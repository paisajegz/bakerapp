package com.misuas.jhonathan.apppaisa.controller.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.OlvidoClaveActivity;
import com.misuas.jhonathan.apppaisa.controller.UsuarioActivity;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText textCorreo;
    private EditText textClave;
    private Button btnLogin;
    private Button btnOlvido;
    private FirebaseAuth mAuth;
    private TextView txtClave,txtCorreo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_login, container, false);

        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");
        textCorreo = (EditText) vista.findViewById(R.id.textCorreo);
        textClave = (EditText) vista.findViewById(R.id.textPass);
        btnLogin = (Button) vista.findViewById(R.id.btnLogin);
        btnOlvido = (Button) vista.findViewById(R.id.btnOlvido);
        txtClave = (TextView) vista.findViewById(R.id.txtClave);
        txtCorreo = (TextView) vista.findViewById(R.id.txtCorreo);


        textCorreo.setTypeface(face);
        textClave.setTypeface(face);
        btnLogin.setTypeface(face);
        btnOlvido.setTypeface(face);
        txtClave.setTypeface(face);
        txtCorreo.setTypeface(face);


        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);
        btnOlvido.setOnClickListener(this);
        return vista;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("iniciando sesion ...");
                dialog.show();
                mAuth.signInWithEmailAndPassword(textCorreo.getText().toString().trim(),textClave.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getContext(),"usuario logeado correctamente",Toast.LENGTH_SHORT).show();
                            Intent inte = new Intent(getContext(), UsuarioActivity.class);
                            getActivity().startActivity(inte);
                            getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            getActivity().finish();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(getContext(),"usuario no logeado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btnOlvido:
                Intent inte = new Intent(getContext(), OlvidoClaveActivity.class);
                startActivity(inte);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
        }
    }
}