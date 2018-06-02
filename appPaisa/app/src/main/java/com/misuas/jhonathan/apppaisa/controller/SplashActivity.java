package com.misuas.jhonathan.apppaisa.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.misuas.jhonathan.apppaisa.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imagenFondo;
    private TextView txtBienvenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.difuminar);
        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");
        imagenFondo = (ImageView) findViewById(R.id.imageFondo);
        txtBienvenido = (TextView) findViewById(R.id.txtBienvenido);
        imagenFondo.setAnimation(anim);
        txtBienvenido.setAnimation(anim);
        txtBienvenido.setTypeface(face);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent movimiento = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(movimiento);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        },3000);
    }
}