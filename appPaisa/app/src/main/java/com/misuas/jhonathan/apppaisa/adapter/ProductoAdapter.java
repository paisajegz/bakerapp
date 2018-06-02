package com.misuas.jhonathan.apppaisa.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.producto.CompraActivity;
import com.misuas.jhonathan.apppaisa.controller.producto.ProductActivity;
import com.misuas.jhonathan.apppaisa.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.misuas.jhonathan.apppaisa.R.*;

/**
 * Created by USUARIO on 12/05/2018.
 */

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolderProducto> {


    private ArrayList<Producto> listaProductos;
    private AppCompatActivity productActivity;

    public ProductoAdapter(ArrayList<Producto> listaProductos, AppCompatActivity productActivity) {
        this.listaProductos = listaProductos;
        this.productActivity = productActivity;
    }

    @Override
    public ViewHolderProducto onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,null,false);
        return new ViewHolderProducto(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderProducto holder, int position) {
        holder.asignarDatos(listaProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }






    public class ViewHolderProducto extends RecyclerView.ViewHolder {

        private  View vista;
        private ImageView imageProducto;
        private TextView txtNameProducto;
        private TextView txtPrecioProducto;
        private Button btnComprarProducto;


        public ViewHolderProducto(View itemView) {
            super(itemView);
            vista = itemView;
            imageProducto = (ImageView) vista.findViewById(R.id.imageProdcuto);
            txtNameProducto = (TextView) vista.findViewById(R.id.txtNameProducto);
            txtPrecioProducto = (TextView) vista.findViewById(R.id.txtPrecioProducto);
            btnComprarProducto = (Button) vista.findViewById(R.id.btnCompraPoducto);

        }

        public void asignarDatos(final Producto producto) {

            txtNameProducto.setText(producto.getNombre());
            txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
            Picasso.get()
                    .load(producto.getFoto())
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageProducto);
            btnComprarProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inte = new Intent(productActivity, CompraActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("producto",producto.getNombre());
                    bundle.putInt("precio" , producto.getPrecio());
                    inte.putExtras(bundle);
                    productActivity.startActivity(inte);
                }
            });
        }
    }
}
