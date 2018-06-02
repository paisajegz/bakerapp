package com.misuas.jhonathan.apppaisa.controller.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.adapter.CompraAdapter;
import com.misuas.jhonathan.apppaisa.model.Compra;

import java.util.ArrayList;


public class MostrarFragment extends Fragment {


    private RecyclerView recyclerCompra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mostrar, container, false);
        recyclerCompra = (RecyclerView) vista.findViewById(R.id.recyclerCompra);
        recyclerCompra.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        final ArrayList<Compra> listaCompra = new ArrayList<Compra>();
        final CompraAdapter adapter = new CompraAdapter(listaCompra);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("cargando");
        dialog.show();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("compras");
        recyclerCompra.setAdapter(adapter);
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                listaCompra.removeAll(listaCompra);
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Compra compra = data.getValue(Compra.class);
                    listaCompra.add(compra);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(getContext(),"error database",Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

}
