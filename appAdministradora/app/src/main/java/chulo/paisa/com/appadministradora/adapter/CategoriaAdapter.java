package chulo.paisa.com.appadministradora.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.controller.productos.ProductActivity;
import chulo.paisa.com.appadministradora.modelo.Categoria;

/**
 * Created by USUARIO on 10/05/2018.
 */

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolderCategoria> {

    private ArrayList<Categoria> listaCategoria;
    private FragmentActivity activity;
    public CategoriaAdapter(ArrayList<Categoria> listaCategoria) {

    }

    public CategoriaAdapter(ArrayList<Categoria> listaCategoria, FragmentActivity activity) {
        this.listaCategoria = listaCategoria;
        this.activity = activity;

    }


    @Override
    public ViewHolderCategoria onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria,null,false);
        return new ViewHolderCategoria(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderCategoria holder, int position) {
        holder.asignarDatos(listaCategoria.get(position));
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }






    public class ViewHolderCategoria extends RecyclerView.ViewHolder {


        private ImageView imageCategoria;
        private TextView txtNameCategoria;
        private CardView cardCategoria;
        private  View vista;

        public ViewHolderCategoria(View itemView) {
            super(itemView);
            vista = itemView;
            // aqui van los componentes cargados
            imageCategoria = (ImageView) itemView.findViewById(R.id.imageCategoria);
            txtNameCategoria = (TextView) itemView.findViewById(R.id.txtNameCate);
            cardCategoria = (CardView) itemView.findViewById(R.id.cardCategoria);
        }

        public void asignarDatos(final Categoria categoria) {
            txtNameCategoria.setText(categoria.getNombreCategoria());
            Picasso.with(vista.getContext())
                    .load(categoria.getImageCategoria())
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageCategoria);
            cardCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  inte = new Intent(vista.getContext(), ProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("categoria",categoria.getNombreCategoria());
                    inte.putExtras(bundle);
                    activity.startActivity(inte);
                }
            });
        }
    }
}