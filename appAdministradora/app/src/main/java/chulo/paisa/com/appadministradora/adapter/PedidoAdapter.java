package chulo.paisa.com.appadministradora.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.controller.pedido.PedidoActivity;
import chulo.paisa.com.appadministradora.modelo.Pedido;

/**
 * Created by USUARIO on 13/05/2018.
 */

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolderPedido> {


    private ArrayList<Pedido> listaPedidos ;
    private FragmentActivity activity;

    public PedidoAdapter(ArrayList<Pedido> listaPedidos, FragmentActivity activity) {
        this.listaPedidos = listaPedidos;
        this.activity=activity;
    }

    @Override
    public ViewHolderPedido onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,null,false);
        return new ViewHolderPedido(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderPedido holder, int position) {
        holder.asignarVariables(listaPedidos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }




    public class ViewHolderPedido extends RecyclerView.ViewHolder {

        private TextView txtPedido;
        private View vista;
        private CardView cardPedido;

        public ViewHolderPedido(View itemView) {
            super(itemView);
            vista = itemView;
            txtPedido = (TextView) vista.findViewById(R.id.txtPedido);
            cardPedido = (CardView) vista.findViewById(R.id.cardPedido);
        }

        public void asignarVariables(final Pedido pedido) {
            txtPedido.setText(pedido.getValor());
            cardPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inte = new Intent(activity, PedidoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("clave",pedido.getClave());
                    bundle.putString("valor",pedido.getValor());
                    inte.putExtras(bundle);
                    activity.startActivity(inte);
                }
            });
        }
    }
}