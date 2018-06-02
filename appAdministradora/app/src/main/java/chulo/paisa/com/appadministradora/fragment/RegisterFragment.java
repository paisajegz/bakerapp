package chulo.paisa.com.appadministradora.fragment;


import android.content.Intent;
import android.graphics.Typeface;
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

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.controller.Register2Activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private Button btnRegistro;
    private EditText textEmail;
    private EditText textClave;
    private TextView txtRegistro,txtClave,txtEmail;
    private FirebaseAuth mAuth;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();

        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");


        btnRegistro = (Button) vista.findViewById(R.id.btnRegister);
        textEmail = (EditText) vista.findViewById(R.id.textEmail);
        textClave = (EditText) vista.findViewById(R.id.textClave);
        txtRegistro = (TextView) vista.findViewById(R.id.txtRegistro);
        txtClave = (TextView) vista.findViewById(R.id.txtClave);
        txtEmail = (TextView) vista.findViewById(R.id.txtEmail);

        btnRegistro.setTypeface(face);
        textEmail.setTypeface(face);
        textClave.setTypeface(face);
        txtRegistro.setTypeface(face);
        txtClave.setTypeface(face);
        txtEmail.setTypeface(face);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(textEmail.getText().toString().trim(),textClave.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent inte = new Intent(getContext(), Register2Activity.class);
                            startActivity(inte);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getContext(),"registro erroneo",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return  vista;
    }
}