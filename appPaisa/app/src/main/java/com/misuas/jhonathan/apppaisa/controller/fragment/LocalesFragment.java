package com.misuas.jhonathan.apppaisa.controller.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.adapter.LocalAdapter;
import com.misuas.jhonathan.apppaisa.controller.map.LocalesActivity;
import com.misuas.jhonathan.apppaisa.controller.map.MapsActivity;
import com.misuas.jhonathan.apppaisa.model.Local;

import java.util.ArrayList;


public class LocalesFragment extends Fragment {


    private RecyclerView recyclerLocal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_locales, container, false);
        final ArrayList<Local> listaLocales = new ArrayList<Local>();
        recyclerLocal = (RecyclerView) vista.findViewById(R.id.recyclerLocal);
        recyclerLocal.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locales");
        final LocalAdapter adapter = new LocalAdapter(listaLocales,"vista",getActivity());
        recyclerLocal.setAdapter(adapter);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("cargando ...");
        dialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                listaLocales.removeAll(listaLocales);
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Local local = data.getValue(Local.class);
                    listaLocales.add(local);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(getContext(),"error database" ,Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

   }
