package com.example.readly;

import android.content.Intent;
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

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AyudaActivity.this, "Enviado exitosamente", Toast.LENGTH_SHORT).show();
                edProblema.setText("");
            }
        });
    }

    public void regresarInicio(View v) {
        Intent ventanaInicio = new Intent(v.getContext(), InicioActivity.class);
        startActivity(ventanaInicio);
    }
}
