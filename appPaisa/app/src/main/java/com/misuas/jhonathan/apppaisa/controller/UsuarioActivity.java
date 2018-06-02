package com.misuas.jhonathan.apppaisa.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.fragment.CompraFragment;
import com.misuas.jhonathan.apppaisa.controller.fragment.EditFragment;
import com.misuas.jhonathan.apppaisa.controller.fragment.InicioFragment;
import com.misuas.jhonathan.apppaisa.controller.fragment.LocalesFragment;
import com.misuas.jhonathan.apppaisa.controller.fragment.MostrarFragment;

public class UsuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.usuario, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager= getSupportFragmentManager();

        if (id == R.id.nav_inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new InicioFragment()).commit();
        }else if ( id == R.id.nav_compras){
            fragmentManager.beginTransaction().replace(R.id.contenedor,new CompraFragment()).commit();
        }else if(id == R.id.nav_editar){
            fragmentManager.beginTransaction().replace(R.id.contenedor,new EditFragment()).commit();
        }else if(id == R.id.nav_mostrarCompras){
            fragmentManager.beginTransaction().replace(R.id.contenedor,new MostrarFragment()).commit();
        }else if (id == R.id.nav_verLocal){
            fragmentManager.beginTransaction().replace(R.id.contenedor,new LocalesFragment()).commit();
        }else if (id == R.id.nav_session){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("cerrar session!");
            builder.setMessage("deseas cerrar session??");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAuth.signOut();
                    Intent inte= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(inte);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}