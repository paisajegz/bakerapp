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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.adapter.CategoriaAdapter;
import com.misuas.jhonathan.apppaisa.model.Categoria;

import java.util.ArrayList;


public class CompraFragment extends Fragment {

    private RecyclerView recycleCategorias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View vista= inflater.inflate(R.layout.fragment_compra, container, false);
        recycleCategorias = (RecyclerView) vista.findViewById(R.id.recycleCategoria);
        final ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();
        recycleCategorias.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("cargando ...");
        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("categoria");

        final CategoriaAdapter adapter = new CategoriaAdapter(listaCategoria,getActivity());
        recycleCategorias.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                listaCategoria.removeAll(listaCategoria);
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Categoria categoria = data.getValue(Categoria.class);
                    listaCategoria.add(categoria);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(getContext(),"error database", Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }


}
