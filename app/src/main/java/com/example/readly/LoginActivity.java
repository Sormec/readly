package com.example.readly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private CheckBox MantenerSesion;
    private MyOpenHelper dbOpenH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usuario = findViewById(R.id.txtUsuario);
        contraseña = findViewById(R.id.txtContraseña);
        MantenerSesion = findViewById(R.id.chkMantenerSesion);
        dbOpenH = new MyOpenHelper(this);

        cargarCredenciales();
    }
    private void cargarCredenciales(){
        SharedPreferences shpLogin = getSharedPreferences("AccesoCredenciales", Context.MODE_PRIVATE);
        String usuario_tmp = shpLogin.getString("usuarioSHP", "");
        String contraseña_tmp = shpLogin.getString("contraseñaSHP", "");
        usuario.setText(usuario_tmp);
        contraseña.setText(contraseña_tmp);
        if(usuario_tmp != "")
        {
            MantenerSesion.setChecked(true);
        }
        else{
            MantenerSesion.setChecked(false);
        }
    }
    private void guardarCredenciales(String usuario, String contraseña){
        SharedPreferences shpLogin = getSharedPreferences("AccesoCredenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor shpLoginEdit = shpLogin.edit();
        shpLoginEdit.putString("usuarioSHP", usuario);
        shpLoginEdit.putString("contraseñaSHP", contraseña);
        shpLoginEdit.commit();
    }
    public void Registrarse(View v) {
        Intent ventanaResgistrarse= new Intent(v.getContext(), ResgistrarseActivity.class);
        startActivity(ventanaResgistrarse);
    }
    public void IniciarSesion(View v) {
        String usuarioV = usuario.getText().toString().trim();
        String contraseniaV = contraseña.getText().toString().trim();
        int cont = 0;
        if(MantenerSesion.isChecked()){
            if (dbOpenH.checkCredenciales(usuarioV, contraseniaV)) {
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Iniciar nueva actividad
                guardarCredenciales(usuarioV, contraseniaV );
                Intent ventanaInicio= new Intent(v.getContext(), InicioActivity.class);
                startActivity(ventanaInicio);
            } else {
                // Credenciales incorrectas, mostrar mensaje de error
                Toast.makeText(LoginActivity.this, "Intente con el nombre y la cedula", Toast.LENGTH_SHORT).show();
                //Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();

            }
        }

    }

}