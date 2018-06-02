package com.misuas.jhonathan.apppaisa.controller;

import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.misuas.jhonathan.apppaisa.R;

public class OlvidoClaveActivity extends AppCompatActivity {

    private Button btnRecordar;
    private EditText textCorreo;
    private FirebaseAuth mAuth;
    private TextView txtOlvido, txtCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_clave);

        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");
        btnRecordar = (Button) findViewById(R.id.btnRecordar);
        textCorreo = (EditText) findViewById(R.id.textCorreo);
        txtOlvido = (TextView) findViewById(R.id.txtOlvido);
        txtCorreo  = (TextView) findViewById(R.id.txtCorreo);


        btnRecordar.setTypeface(face);
        textCorreo.setTypeface(face);
        txtOlvido.setTypeface(face);
        txtCorreo.setTypeface(face);

        mAuth= FirebaseAuth.getInstance();
        btnRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(textCorreo.getText().toString().trim());
                Snackbar.make(v,"revisa tu correo ahi restableceremos la contraseña",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}