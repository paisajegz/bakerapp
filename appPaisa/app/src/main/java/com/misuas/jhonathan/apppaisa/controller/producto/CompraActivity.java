package com.misuas.jhonathan.apppaisa.controller.producto;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.MainActivity;
import com.misuas.jhonathan.apppaisa.model.Compra;
import com.misuas.jhonathan.apppaisa.model.Producto;
import com.misuas.jhonathan.apppaisa.service.TokenService;

import org.json.JSONObject;

import java.util.Date;

public class CompraActivity extends AppCompatActivity {

    private TextView txtProducto;
    private TextView txtPrecioProducto;
    private TextView txtPrecioCompra;
    private EditText textDirrecion;
    private Button btnMas;
    private Button btnMenos;
    private Button btnCompra;
    private TextView txtCantidad;
    private int cantidad = 1;
    private Producto producto;
    private Bundle parametro;
    private int precioTotal=0;
    private EditText textTelefono;
    private String token= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        parametro = getIntent().getExtras();
        producto = new Producto();
        producto.setNombre(parametro.getString("producto"));
        producto.setPrecio(parametro.getInt("precio"));


        txtProducto = (TextView) findViewById(R.id.txtProducto);
        txtPrecioCompra = (TextView) findViewById(R.id.txtPrecioCompra);
        txtPrecioProducto = (TextView) findViewById(R.id.txtPrecioProducto);
        textDirrecion = (EditText) findViewById(R.id.textDirrecion);
        btnMas = (Button) findViewById(R.id.btnMas);
        btnMenos = (Button) findViewById(R.id.btnMenos);
        btnCompra = (Button) findViewById(R.id.btnComprar);
        txtCantidad = (TextView) findViewById(R.id.txtCantidad);
        textTelefono = (EditText) findViewById(R.id.textTelefono);

        precioTotal=producto.getPrecio();
        txtProducto.setText("producto: " + producto.getNombre());
        txtPrecioProducto.setText("precio del producto: " + String.valueOf(producto.getPrecio()));
        txtPrecioCompra.setText("precio de compra: " + String.valueOf(precioTotal));
        txtCantidad.setText("cantidad: " + String.valueOf(cantidad));
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidad++;
                 precioTotal = cantidad * producto.getPrecio();
                txtPrecioCompra.setText("precio de compra: " + String.valueOf(precioTotal));
                txtCantidad.setText("cantidad: " + String.valueOf(cantidad));
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(cantidad >= 0){
                   cantidad--;
                   precioTotal = cantidad * producto.getPrecio();
                   txtPrecioCompra.setText("precio de compra: " + String.valueOf(precioTotal));
                   txtCantidad.setText("cantidad: " + String.valueOf(cantidad));
               }
            }
        });

        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(CompraActivity.this);
                dialog.setMessage("haciendo pedido ...");
                dialog.show();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final Compra compra = new Compra();
                compra.setCantidad(String.valueOf(cantidad));
                compra.setDirrecion(textDirrecion.getText().toString().trim());
                compra.setPrecioProducto(String.valueOf(producto.getPrecio()));
                compra.setPrecioTotal(String.valueOf(precioTotal));
                compra.setTelefono(textTelefono.getText().toString().trim());
                compra.setProducto(producto.getNombre());
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("compras");
                final Date date = new Date();
                FirebaseDatabase datad= FirebaseDatabase.getInstance();
                DatabaseReference refToken = datad.getReference("token");
                 refToken.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        token=value;
                        myRef.child(user.getUid()).child(String.valueOf(date.getDate()) + String.valueOf(date.getMonth()) + String.valueOf(date.getYear()) + String.valueOf(date.getHours()) + String.valueOf(date.getMinutes()) + String.valueOf(date.getSeconds()) + compra.getProducto()).setValue(compra);
                        Toast.makeText(CompraActivity.this, "compra hecha exitosamente", Toast.LENGTH_SHORT).show();
                        TokenService service = new TokenService(CompraActivity.this,dialog);
                        service.execute(token);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CompraActivity.this,"error",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}