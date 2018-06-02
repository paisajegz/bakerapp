package com.misuas.jhonathan.apppaisa.controller.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.misuas.jhonathan.apppaisa.R;
import com.misuas.jhonathan.apppaisa.controller.Register2Activity;


public class RegistroFragment extends Fragment {

    private EditText textCorreo;
    private EditText textClave;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private TextView txtClave, txtCorreo;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_registro, container, false);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("existe", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("existe", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        textCorreo = (EditText)  vista.findViewById(R.id.textCorreo);
        textClave = (EditText) vista.findViewById(R.id.textPass);
        txtClave = (TextView)  vista.findViewById(R.id.txtClave);
        txtCorreo = (TextView) vista.findViewById(R.id.txtCorreo);
        btnRegister = (Button) vista.findViewById(R.id.btnRegister);


        textClave.setTypeface(face);
        textCorreo.setTypeface(face);
        txtClave.setTypeface(face);
        txtCorreo.setTypeface(face);
        btnRegister.setTypeface(face);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("registrando ...");
                dialog.show();
                mAuth.createUserWithEmailAndPassword(textCorreo.getText().toString().trim(),textClave.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getContext(), "registro exitoso" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Register2Activity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);


                        }else{
                            dialog.dismiss();
                            Toast.makeText(getContext(), "registro erroneo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return vista;
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
    }
}