package chulo.paisa.com.appadministradora.controller.productos;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import chulo.paisa.com.appadministradora.R;

public class ProductActivity extends AppCompatActivity {


    private Button btnPrecioSig;
    private EditText textPrecio , textProducto;
    private TextView txtPrecio, txtProducto;
    private String categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        final Bundle parametro = getIntent().getExtras();
        categoria = parametro.getString("categoria");
        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");


        btnPrecioSig = (Button) findViewById(R.id.btnPrecioSig);
        textPrecio = (EditText) findViewById(R.id.textPrecio);
        textProducto = (EditText) findViewById(R.id.textProducto);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        txtProducto = (TextView) findViewById(R.id.txtProducto);

        btnPrecioSig.setTypeface(face);
        textPrecio.setTypeface(face);
        textProducto.setTypeface(face);
        txtPrecio.setTypeface(face);
        txtProducto.setTypeface(face);


        btnPrecioSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(ProductActivity.this,SubirFotoProActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("categoria",categoria);
                bundle.putInt("precio",Integer.parseInt(textPrecio.getText().toString().trim()));
                bundle.putString("producto",textProducto.getText().toString().trim());
                inte.putExtras(bundle);
                startActivity(inte);
            }
        });
    }
}