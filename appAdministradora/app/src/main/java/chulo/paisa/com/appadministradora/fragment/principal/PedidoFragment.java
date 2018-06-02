package chulo.paisa.com.appadministradora.fragment.principal;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.adapter.CompraAdapter;
import chulo.paisa.com.appadministradora.adapter.PedidoAdapter;
import chulo.paisa.com.appadministradora.modelo.Compra;
import chulo.paisa.com.appadministradora.modelo.Pedido;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoFragment extends Fragment {


    private RecyclerView recyclerCompra;

    public PedidoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_pedido, container, false);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("cargando ...");
        dialog.show();
        recyclerCompra = (RecyclerView) vista.findViewById(R.id.recyclerCompra);
        recyclerCompra.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        final ArrayList<Pedido> listaPedido = new ArrayList<Pedido>();
        final PedidoAdapter  adapter = new PedidoAdapter(listaPedido,getActivity());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference("compras");
        recyclerCompra.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPedido.removeAll(listaPedido);
                dialog.dismiss();
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    for (DataSnapshot datas : data.getChildren() ){
                        Pedido pedido = new Pedido();
                        pedido.setClave(data.getKey());
                        pedido.setValor(datas.getKey());
                        listaPedido.add(pedido);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return vista;
    }
}