package com.misuas.jhonathan.apppaisa.controller.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.adapter.LocalAdapter;
import com.misuas.jhonathan.apppaisa.model.Local;

import java.util.ArrayList;

public class LocalesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Bundle parametros;
    private Local local;
    private RecyclerView recyclerLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locales);
        parametros = getIntent().getExtras();
        local = new Local();
        local.setNombre(parametros.getString("local"));
        local.setLactitud(parametros.getString("lactitud"));
        local.setLongitud(parametros.getString("longitud"));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recyclerLocal = (RecyclerView) findViewById(R.id.recyclerLocal);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        LatLng coordenada = new LatLng(Double.parseDouble(local.getLactitud()), Double.parseDouble(local.getLongitud()));
        mMap.addMarker(new MarkerOptions().position(coordenada).title(local.getNombre()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15));
        final ArrayList<Local> listaLocales = new ArrayList<Local>();
        recyclerLocal.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locales");
        final LocalAdapter adapter = new LocalAdapter(listaLocales,"map",this,mMap);
        recyclerLocal.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLocales.removeAll(listaLocales);
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Local local = data.getValue(Local.class);
                    listaLocales.add(local);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}