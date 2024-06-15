package com.example.readly;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EjerciciosActivity extends AppCompatActivity {

    private SoundPool sp;
    private int sonidoP;
    private int sonidoA;
    private TextView txtD, txtP, txtQ, txtB;
    private TextView txtA1, txtA2, txtE1, txtE2;
    private TextView txtCopo, txtPaco, txtQoqo, txtPoco;
    private TextView txtR_r, txtRR_r, txtRR_rr, txtR_rr;
    private TextView txtBrado, txtBravo, txtBrano, txtCravo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ejercicios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtD = findViewById(R.id.text_D);
        txtP = findViewById(R.id.text_P);
        txtQ = findViewById(R.id.text_Q);
        txtB = findViewById(R.id.text_B);
        txtA1 = findViewById(R.id.text_A1);
        txtA2= findViewById(R.id.text_A2);
        txtE1 = findViewById(R.id.text_E1);
        txtE2 = findViewById(R.id.text_E2);
        txtCopo = findViewById(R.id.text_Copo);
        txtPaco = findViewById(R.id.text_Paco);
        txtQoqo = findViewById(R.id.text_Qoqo);
        txtPoco = findViewById(R.id.text_Poco);
        txtR_r = findViewById(R.id.text_R_r);
        txtRR_r = findViewById(R.id.text_RR_r);
        txtRR_rr = findViewById(R.id.text_RR_rr);
        txtR_rr = findViewById(R.id.text_R_rr);
        txtBrado = findViewById(R.id.text_Brado);
        txtBravo = findViewById(R.id.text_Bravo);
        txtBrano = findViewById(R.id.text_Brano);
        txtCravo = findViewById(R.id.text_Cravo);

        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 1);
        sonidoP = sp.load(this, R.raw.letra_p, 1);
        sonidoA = sp.load(this, R.raw.letra_a, 1);
    }

    public void reproduce_P(View view) {
        sp.play(sonidoP, 1f, 1f, 1, 0, 1f);
    }
    public void reproduce_A(View view) {
        sp.play(sonidoA, 1f, 1f, 1, 0, 1f);
    }

    public void seleccionarOpc1(View view){
        //crear un metodo que coloree solo el que seleciono
        String bg = "#F4DFA2";
        TextView[] textViews = {txtD, txtP, txtQ, txtB};

        for (TextView textV : textViews) {
            //compara cada id del array con el que viene del 'view'
            if (view.getId() == textV.getId()) {
                textV.setBackgroundColor(Color.parseColor(bg));
            } else {
                textV.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public void seleccionarOpc2(View view){
        //crear un metodo que coloree solo el que seleciono
        String bg = "#F4DFA2";
        TextView[] textViews = {txtA1, txtE1, txtE2, txtA2};

        for (TextView textV : textViews) {
            //compara cada id del array con el que viene del 'view'
            if (view.getId() == textV.getId()) {
                textV.setBackgroundColor(Color.parseColor(bg));
            } else {
                textV.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public void seleccionarOpc3(View view){
        //crear un metodo que coloree solo el que seleciono
        String bg = "#F4DFA2";
        TextView[] textViews = {txtCopo, txtPaco, txtQoqo, txtPoco};

        for (TextView textV : textViews) {
            //compara cada id del array con el que viene del 'view'
            if (view.getId() == textV.getId()) {
                textV.setBackgroundColor(Color.parseColor(bg));
            } else {
                textV.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public void seleccionarOpc4(View view){
        //crear un metodo que coloree solo el que seleciono
        String bg = "#F4DFA2";
        TextView[] textViews = {txtR_r, txtRR_r, txtRR_rr, txtR_rr};

        for (TextView textV : textViews) {
            //compara cada id del array con el que viene del 'view'
            if (view.getId() == textV.getId()) {
                textV.setBackgroundColor(Color.parseColor(bg));
            } else {
                textV.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public void seleccionarOpc5(View view){
        //crear un metodo que coloree solo el que seleciono
        String bg = "#F4DFA2";
        TextView[] textViews = {txtBrado, txtBravo, txtBrano, txtCravo};

        for (TextView textV : textViews) {
            //compara cada id del array con el que viene del 'view'
            if (view.getId() == textV.getId()) {
                textV.setBackgroundColor(Color.parseColor(bg));
            } else {
                textV.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public void borrar(View view){
        TextView[] textViews = {
                txtD, txtP, txtQ, txtB,
                txtA1, txtA2, txtE1, txtE2,
                txtCopo, txtPaco, txtQoqo, txtPoco,
                txtR_r, txtRR_r, txtRR_rr, txtR_rr,
                txtBrado, txtBravo, txtBrano, txtCravo
        };

        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void regresarInicio(View v) {
        Intent ventanaInicio = new Intent(v.getContext(), InicioActivity.class);
        startActivity(ventanaInicio);
    }

}