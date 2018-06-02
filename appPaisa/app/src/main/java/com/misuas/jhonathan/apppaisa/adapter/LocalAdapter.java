package com.misuas.jhonathan.apppaisa.adapter;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.map.LocalesActivity;
import com.misuas.jhonathan.apppaisa.model.Local;

import java.util.ArrayList;

/**
 * Created by USUARIO on 12/05/2018.
 */

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolderLocal> {

    private ArrayList<Local> listaLocales;
    private String opc;
    private FragmentActivity activity;
    private AppCompatActivity LocalActivity;
    private GoogleMap mMap;
    public LocalAdapter(ArrayList<Local> listaLocales, String opc, FragmentActivity activity) {
        this.listaLocales = listaLocales;
        this.opc = opc;
        this.activity = activity;
    }

    public LocalAdapter(ArrayList<Local> listaLocales, String opc, AppCompatActivity LocalActivity, GoogleMap mMap) {
        this.listaLocales = listaLocales;
        this.opc = opc;
        this.LocalActivity = LocalActivity;
        this.mMap = mMap;

    }

    @Override
    public ViewHolderLocal onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local,null,false);
        return new ViewHolderLocal(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderLocal holder, int position) {
        holder.asignaVariables(listaLocales.get(position));
    }

    @Override
    public int getItemCount() {
        return listaLocales.size();
    }




    public class ViewHolderLocal extends RecyclerView.ViewHolder {

        private View vista;
        private TextView txtLocal;
        private CardView cardlocal;

        public ViewHolderLocal(View itemView) {
            super(itemView);
            vista = itemView;
            txtLocal = (TextView) vista.findViewById(R.id.txtlocal);
            cardlocal = (CardView) vista.findViewById(R.id.cardLocal);
        }

        public void asignaVariables(final Local local) {
            txtLocal.setText(local.getNombre());
            cardlocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (opc.equals("vista")){
                        Intent inte = new Intent(activity, LocalesActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("local",local.getNombre());
                        bundle.putString("lactitud",local.getLactitud());
                        bundle.putString("longitud",local.getLongitud());
                        inte.putExtras(bundle);
                        activity.startActivity(inte);
                    }else if(opc.equals("map")){
                        UiSettings uiSettings = mMap.getUiSettings();
                        LatLng coordenada = new LatLng(Double.parseDouble(local.getLactitud()), Double.parseDouble(local.getLongitud()));
                        mMap.addMarker(new MarkerOptions().position(coordenada).title(local.getNombre()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15));
                    }
                }
            });
        }
    }
}