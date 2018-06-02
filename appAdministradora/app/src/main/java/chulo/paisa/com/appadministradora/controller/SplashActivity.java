package chulo.paisa.com.appadministradora.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import chulo.paisa.com.appadministradora.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imagenFondo;
    private TextView txtAdmin;
    private TextView txtBocados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");
        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.difuminar);
        imagenFondo = (ImageView) findViewById(R.id.imageFondo);
        txtAdmin = (TextView) findViewById(R.id.txtAdmin);
        txtBocados = (TextView) findViewById(R.id.txtBocados);
        imagenFondo.setAnimation(anim);
        txtAdmin.setTypeface(face);
        txtAdmin.setAnimation(anim);
        txtBocados.setTypeface(face);
        txtBocados.setAnimation(anim);
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