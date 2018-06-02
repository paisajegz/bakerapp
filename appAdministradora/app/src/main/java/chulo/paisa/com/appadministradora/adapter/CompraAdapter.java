package chulo.paisa.com.appadministradora.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.modelo.Compra;

/**
 * Created by USUARIO on 13/05/2018.
 */

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolderCompra> {

    private ArrayList<Compra> listaCompra;
    private String clave="";
    private String valor="";
    private AppCompatActivity activity;

    public CompraAdapter(ArrayList<Compra> listaCompra, String clave, String valor, AppCompatActivity activity) {
        this.listaCompra = listaCompra;
        this.clave = clave;
        this.valor = valor;
        this.activity = activity;
    }

    @Override
    public ViewHolderCompra onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra,null,false);
        return new ViewHolderCompra(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolderCompra holder, int position) {
         if(!clave.equals("")) {
             holder.asignarVariables(listaCompra.get(position));
         }
    }

    @Override
    public int getItemCount() {
        return listaCompra.size();
    }




    public class ViewHolderCompra extends RecyclerView.ViewHolder {

        private TextView txtProducto;
        private TextView txtPrecioCompra;
        private TextView txtPrecioProducto;
        private TextView txtDirrecion;
        private TextView txtTelefono;
        private TextView txtCantidad;
        private View vista;
        private Button btnEliminarPedido;

        public ViewHolderCompra(View itemView) {
            super(itemView);
            vista =itemView;
            txtProducto = (TextView) vista.findViewById(R.id.txtProducto);
            txtPrecioCompra = (TextView) vista.findViewById(R.id.txtPrecioCompra);
            txtPrecioProducto = (TextView) vista.findViewById(R.id.txtPrecioProducto);
            txtDirrecion = (TextView)vista.findViewById(R.id.txtDirrecion);
            txtTelefono = (TextView) vista.findViewById(R.id.txtTelefono);
            txtCantidad = (TextView) vista.findViewById(R.id.txtCantidad);
            btnEliminarPedido = (Button) vista.findViewById(R.id.btnEliminarPedido);
        }

        public void asignarVariables(Compra compra) {
            if(!clave.equals("")) {
                txtProducto.setText(compra.getProducto());
                txtPrecioProducto.setText(compra.getPrecioProducto());
                txtPrecioCompra.setText(compra.getPrecioTotal());
                txtDirrecion.setText(compra.getDirrecion());
                txtTelefono.setText(compra.getTelefono());
                txtCantidad.setText(compra.getCantidad());
            }
            btnEliminarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!clave.equals("")) {


                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("compras");
                        myRef.child(clave).child(valor).removeValue();
                        Toast.makeText(activity, "compra eliminada", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }else{

                    }
                }
            });
        }
    }
}