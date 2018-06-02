package com.misuas.jhonathan.apppaisa.controller.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.model.Usuario;

public class EditFragment extends Fragment {


    private EditText textNombre;
    private EditText textApellido;
    private EditText textCelular;
    private EditText textDocumento;
    private TextView txtDatosPer;
    private Button btnActDato;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference refPersona;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_edit, container, false);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");
        textNombre = (EditText) vista.findViewById(R.id.textNombre);
        textApellido = (EditText) vista.findViewById(R.id.textApellido);
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        refPersona = database.getReference("usuarios");
        textCelular = (EditText) vista.findViewById(R.id.textCelular);
        textDocumento = (EditText) vista.findViewById(R.id.textDocumento);
        btnActDato = (Button) vista.findViewById(R.id.btnActDatos);
        txtDatosPer = (TextView) vista.findViewById(R.id.txtDatosPer);

        textCelular.setTypeface(face);
        textDocumento.setTypeface(face);
        btnActDato.setTypeface(face);
        txtDatosPer.setTypeface(face);
        textNombre.setTypeface(face);
        textApellido.setTypeface(face);

        final ProgressDialog dialog= new ProgressDialog(getContext());
        dialog.setMessage("cargando datos");
        dialog.show();

        refPersona.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario user = new Usuario();
                user = dataSnapshot.getValue(Usuario.class);

                dialog.dismiss();
                textNombre.setText(user.getNombre());
                textApellido.setText(user.getApellido());
                textDocumento.setText(user.getDocumento());
                textCelular.setText(user.getCelular());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"error en la extracion de datos",Toast.LENGTH_SHORT).show();
            }
        });
        btnActDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario user = new Usuario();
                user.setNombre(textNombre.getText().toString().trim());
                user.setApellido(textApellido.getText().toString().trim());
                user.setCelular(textCelular.getText().toString().trim());
                user.setDocumento(textDocumento.getText().toString().trim());
                refPersona.child(mAuth.getCurrentUser().getUid()).setValue(user);
                Toast.makeText(getContext(),"datos actualizasoa exitosamente",Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }
}