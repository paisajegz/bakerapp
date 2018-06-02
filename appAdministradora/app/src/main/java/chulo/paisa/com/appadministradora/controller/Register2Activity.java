package chulo.paisa.com.appadministradora.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.modelo.Usuario;

public class Register2Activity extends AppCompatActivity {

    private EditText textNombre;
    private EditText textApellido;
    private EditText textCelular;
    private EditText textDocumento;
    private Button btnRegiDatos;
    private FirebaseAuth mAuth;
    private TextView txtDatosPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mAuth = FirebaseAuth.getInstance();

        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");
        textNombre = (EditText) findViewById(R.id.textNombre);
        textApellido = (EditText) findViewById(R.id.textApellido);
        textCelular = (EditText) findViewById(R.id.textCelular);
        textDocumento = (EditText) findViewById(R.id.textDocumento);
        btnRegiDatos = (Button) findViewById(R.id.btnRegiDatos);
        txtDatosPer = (TextView) findViewById(R.id.txtDatosPer);

        textNombre.setTypeface(face);
        textApellido.setTypeface(face);
        textCelular.setTypeface(face);
        textDocumento.setTypeface(face);
        btnRegiDatos.setTypeface(face);
        txtDatosPer.setTypeface(face);

        btnRegiDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference refUsuario = database.getReference("admin");
                Usuario user =new Usuario();
                user.setNombre(textNombre.getText().toString().trim());
                user.setApellido(textApellido.getText().toString().trim());
                user.setCelular(textCelular.getText().toString().trim());
                user.setDocumento(textDocumento.getText().toString().trim());
                refUsuario.child(mAuth.getCurrentUser().getUid()).setValue(user);
                mAuth.signOut();
                Toast.makeText(Register2Activity.this,"Datos registrado exitosamente",Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(Register2Activity.this,MainActivity.class);
                startActivity(inte);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        });
    }
}