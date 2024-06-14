package com.example.readly;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConsultardatosActivity extends AppCompatActivity {
    private EditText edCedula, edConsulNombres, edConsulApellidos, edConsulCorreo, edConsulFechaNacimiento;
    private Spinner spnConsulNacionalidad;
    private RadioGroup radioGroupGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultardatos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edCedula = findViewById(R.id.edCedula);
        edConsulNombres = findViewById(R.id.edConsulNombres);
        edConsulApellidos = findViewById(R.id.edConsulApellidos);
        edConsulCorreo = findViewById(R.id.edConsulCorreo);
        edConsulFechaNacimiento = findViewById(R.id.edConsulFechaNacimiento);
        spnConsulNacionalidad = findViewById(R.id.spnConsulNacionalidad);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);

        cargarNacionalidades();

        spnConsulNacionalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    private void cargarNacionalidades() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nacionalidad, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnConsulNacionalidad.setAdapter(adapter);
    }
    @SuppressLint("Range")
    public void consultarDatos(View v) {
        MyOpenHelper dbReadly = new MyOpenHelper(this);
        final SQLiteDatabase dbReadlyMode = dbReadly.getReadableDatabase();

        String cedulaUsuario = edCedula.getText().toString();

        Cursor c = dbReadlyMode.rawQuery("SELECT nombres, apellidos, correo, fecha_nacimiento, nacionalidad, genero FROM usuario" +
                " WHERE cedula = '" + cedulaUsuario + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                String nombresUsuario = c.getString(c.getColumnIndex("nombres"));
                String apellidosUsuario = c.getString(c.getColumnIndex("apellidos"));
                String correoUsuario = c.getString(c.getColumnIndex("correo"));
                String fechaNacimientoUsuario = c.getString(c.getColumnIndex("fecha_nacimiento"));
                String nacionalidadUsuario = c.getString(c.getColumnIndex("nacionalidad"));
                String generoUsuario = c.getString(c.getColumnIndex("genero"));

                edConsulNombres.setText(nombresUsuario);
                edConsulApellidos.setText(apellidosUsuario);
                edConsulCorreo.setText(correoUsuario);
                edConsulFechaNacimiento.setText(fechaNacimientoUsuario);

                if (nacionalidadUsuario != null) {
                    int nacionalidadIndex = obtenerIndiceNacionalidad(nacionalidadUsuario);
                    spnConsulNacionalidad.setSelection(nacionalidadIndex);
                }

                if (generoUsuario != null) {
                    if (generoUsuario.equals("Masculino")) {
                        radioGroupGenero.check(R.id.rdtConsulMasculino);
                    } else if (generoUsuario.equals("Femenino")) {
                        radioGroupGenero.check(R.id.rdtConsulFemenino);
                    } else {
                        radioGroupGenero.clearCheck();
                    }
                }
            }
            c.close();
        }
        dbReadlyMode.close();
    }
    private int obtenerIndiceNacionalidad(String nacionalidad) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spnConsulNacionalidad.getAdapter();
        return adapter.getPosition(nacionalidad);
    }
    public void regresarInicio(View v) {
        Intent ventanaInicio = new Intent(v.getContext(), InicioActivity.class);
        startActivity(ventanaInicio);
    }
    public void eliminarRegistro(View v) {
        // Crear un diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que desea eliminar este registro?");
        builder.setTitle("Confirmación de eliminación");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MyOpenHelper dbReadly = new MyOpenHelper(ConsultardatosActivity.this);
                final SQLiteDatabase dbReadlyMode = dbReadly.getWritableDatabase();

                String cedulaUsuario = edCedula.getText().toString();

                dbReadlyMode.delete("usuario", "cedula = ?", new String[]{cedulaUsuario});

                // Verificar si la tabla está vacía y resetear el autoincremento
                Cursor cursor = dbReadlyMode.rawQuery("SELECT COUNT(*) FROM usuario", null);
                if (cursor != null && cursor.moveToFirst()) {
                    int count = cursor.getInt(0);
                    if (count == 0) {
                        dbReadlyMode.execSQL("DELETE FROM sqlite_sequence WHERE name = 'usuario'");
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }

                edConsulNombres.setText("");
                edConsulApellidos.setText("");
                edConsulCorreo.setText("");
                edConsulFechaNacimiento.setText("");

                spnConsulNacionalidad.setSelection(0);
                radioGroupGenero.clearCheck();

                dbReadlyMode.close();

                Toast.makeText(ConsultardatosActivity.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void actualizarRegistro(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que desea actualizar este registro?");
        builder.setTitle("Confirmación de actualización");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MyOpenHelper dbReadly = new MyOpenHelper(ConsultardatosActivity.this);
                final SQLiteDatabase dbReadlyMode = dbReadly.getWritableDatabase();

                String cedulaUsuario = edCedula.getText().toString();
                String nombresUsuario = edConsulNombres.getText().toString();
                String apellidosUsuario = edConsulApellidos.getText().toString();
                String correoUsuario = edConsulCorreo.getText().toString();
                String fechaNacimientoUsuario = edConsulFechaNacimiento.getText().toString();
                String nacionalidadUsuario = spnConsulNacionalidad.getSelectedItem().toString();
                int selectedGeneroId = radioGroupGenero.getCheckedRadioButtonId();
                String generoUsuario;
                if (selectedGeneroId == R.id.rdtConsulMasculino) {
                    generoUsuario = "Masculino";
                } else if (selectedGeneroId == R.id.rdtConsulFemenino) {
                    generoUsuario = "Femenino";
                } else {
                    generoUsuario = "";
                }
                dbReadlyMode.execSQL("UPDATE usuario SET nombres = ?, apellidos = ?, correo = ?, fecha_nacimiento = ?, nacionalidad = ?, genero = ? WHERE cedula = ?",
                        new Object[]{nombresUsuario, apellidosUsuario, correoUsuario, fechaNacimientoUsuario, nacionalidadUsuario, generoUsuario, cedulaUsuario});

                dbReadlyMode.close();

                Toast.makeText(ConsultardatosActivity.this, "Registro actualizado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
