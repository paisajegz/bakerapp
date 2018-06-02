package chulo.paisa.com.appadministradora.controller.pedido;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.adapter.CompraAdapter;
import chulo.paisa.com.appadministradora.adapter.PedidoAdapter;
import chulo.paisa.com.appadministradora.modelo.Compra;
import chulo.paisa.com.appadministradora.modelo.Pedido;

public class PedidoActivity extends AppCompatActivity {


    private Bundle parametro;
    private String clave;
    private String valor;
    private TextView txtProducto;
    private TextView txtPrecioCompra;
    private TextView txtPrecioProducto;
    private TextView txtDirrecion;
    private TextView txtTelefono;
    private TextView txtCantidad;
    private Button btnEliminarPedido;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");
        txtProducto = (TextView) findViewById(R.id.txtProducto);
        txtPrecioCompra = (TextView) findViewById(R.id.txtPrecioCompra);
        txtPrecioProducto = (TextView) findViewById(R.id.txtPrecioProducto);
        txtDirrecion = (TextView) findViewById(R.id.txtDirrecion);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtCantidad = (TextView) findViewById(R.id.txtCantidad);
        btnEliminarPedido = (Button) findViewById(R.id.btnEliminarPedido);

        txtProducto.setTypeface(face);
        txtPrecioCompra.setTypeface(face);
        txtPrecioProducto.setTypeface(face);
        txtDirrecion.setTypeface(face);
        txtTelefono.setTypeface(face);
        txtCantidad.setTypeface(face);
        btnEliminarPedido.setTypeface(face);

        btnEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clave.equals("")) {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("compras");
                    myRef.child(clave).child(valor).removeValue();
                    Toast.makeText(PedidoActivity.this, "compra eliminada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        final ProgressDialog dialog = new ProgressDialog(PedidoActivity.this);
        dialog.setMessage("cargando datos");
        dialog.show();
        parametro = getIntent().getExtras();
        clave = parametro.getString("clave");
        valor = parametro.getString("valor");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("compras");
        myRef.child(clave).child(valor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                Compra compra = dataSnapshot.getValue(Compra.class);
                if(compra != null) {
                    txtProducto.setText(compra.getProducto());
                    txtPrecioProducto.setText(compra.getPrecioProducto());
                    txtPrecioCompra.setText(compra.getPrecioTotal());
                    txtDirrecion.setText(compra.getDirrecion());
                    txtTelefono.setText(compra.getTelefono());
                    txtCantidad.setText(compra.getCantidad());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(PedidoActivity.this,"error datbase",Toast.LENGTH_LONG).show();
            }
        });

    }
}