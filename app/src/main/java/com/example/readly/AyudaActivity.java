package com.example.readly;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AyudaActivity extends AppCompatActivity {
    private EditText edProblema;
    private Button btnEnviar;
    private RatingBar ratingBar;
    private MyOpenHelper dbOpenH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ayuda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        edProblema = findViewById(R.id.edProblema);
        btnEnviar = findViewById(R.id.btnEnviar);
        ratingBar = findViewById(R.id.ratingBar);

        dbOpenH = new MyOpenHelper(this);

        /*btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AyudaActivity.this, "Enviado exitosamente", Toast.LENGTH_SHORT).show();
                edProblema.setText("");
            }
        });*/
    }

    public void guardarDatosAyuda(View view) {
        MyOpenHelper dbReadly = new MyOpenHelper(this);
        final SQLiteDatabase dbReadlyMode = dbReadly.getWritableDatabase();
        String problema = edProblema.getText().toString().trim();
        double rating_bar = ratingBar.getRating();

        SharedPreferences shpLogin = getSharedPreferences("AccesoCredenciales", Context.MODE_PRIVATE);
        String usuario_tmp = shpLogin.getString("usuarioSHP", "");
        int idUsuario = dbOpenH.obtenerIdporNombre(usuario_tmp);

        if(dbReadlyMode != null){
            ContentValues cv = new ContentValues();
            cv.put("id_usuario", idUsuario);
            cv.put("comentario", problema);
            cv.put("r_bar", rating_bar);
            dbReadlyMode.insert("ayuda", null, cv);
            Toast.makeText(AyudaActivity.this, "Enviado exitosamente", Toast.LENGTH_SHORT).show();
        }
        //dbReadlyMode.close();
        edProblema.setText("");
        ratingBar.setRating(0);
    }
    public void regresarInicio(View v) {
        Intent ventanaInicio = new Intent(v.getContext(), InicioActivity.class);
        startActivity(ventanaInicio);
    }
}
