package com.misuas.jhonathan.apppaisa.controller.producto;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.adapter.ProductoAdapter;
import com.misuas.jhonathan.apppaisa.model.Producto;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {


    private Bundle parametros;
    private String categoria;
    private RecyclerView recyclerProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        parametros = getIntent().getExtras();
        categoria = parametros.getString("categoria");
        recyclerProducto = (RecyclerView) findViewById(R.id.recyclerProducto);
        final ArrayList<Producto> listaProducto = new ArrayList<Producto>();
        recyclerProducto.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog dialog = new ProgressDialog(ProductActivity.this);
        dialog.setMessage("cargando ...");
        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("producto");
        final ProductoAdapter adapter = new ProductoAdapter(listaProducto,this);
        recyclerProducto.setAdapter(adapter);
        myRef.child(categoria).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaProducto.removeAll(listaProducto);
                dialog.dismiss();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Producto producto = data.getValue(Producto.class);
                    listaProducto.add(producto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(ProductActivity.this,"error database",Toast.LENGTH_SHORT).show();
            }
        });

    }
}