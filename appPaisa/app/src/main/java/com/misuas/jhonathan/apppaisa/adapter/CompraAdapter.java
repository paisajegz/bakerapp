package com.misuas.jhonathan.apppaisa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.model.Compra;

import java.util.ArrayList;

/**
 * Created by USUARIO on 13/05/2018.
 */

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolderCompra> {


    public CompraAdapter(ArrayList<Compra> listaCompra) {
        this.listaCompra = listaCompra;
    }

    private ArrayList<Compra> listaCompra = new ArrayList<Compra>();


    @Override
    public ViewHolderCompra onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra,null,false);
        return new ViewHolderCompra(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderCompra holder, int position) {
        holder.asignarVariables(listaCompra.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCompra.size();
    }



    public class ViewHolderCompra extends RecyclerView.ViewHolder {

        private TextView  txtProducto;
        private TextView txtPrecioCompra;
        private TextView txtPrecioProducto;
        private TextView txtDirrecion;
        private TextView txtTelefono;
        private TextView txtCantidad;
        private View vista;


        public ViewHolderCompra(View itemView) {
            super(itemView);
            vista =itemView;
            txtProducto = (TextView) vista.findViewById(R.id.txtProducto);
            txtPrecioCompra = (TextView) vista.findViewById(R.id.txtPrecioCompra);
            txtPrecioProducto = (TextView) vista.findViewById(R.id.txtPrecioProducto);
            txtDirrecion = (TextView)vista.findViewById(R.id.txtDirrecion);
            txtTelefono = (TextView) vista.findViewById(R.id.txtTelefono);
            txtCantidad = (TextView) vista.findViewById(R.id.txtCantidad);
        }

        public void asignarVariables(Compra compra) {

            txtProducto.setText("nombre producto: " + compra.getProducto());
            txtPrecioProducto.setText("precio producto: "+ compra.getPrecioProducto());
            txtPrecioCompra.setText("precio total: " + compra.getPrecioTotal());
            txtDirrecion.setText("direccion del pedido: " + compra.getDirrecion());
            txtTelefono.setText("telefono del pedido" + compra.getTelefono());
            txtCantidad.setText("cantidad de productos: " + compra.getCantidad());
         }
    }
}