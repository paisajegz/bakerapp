package chulo.paisa.com.appadministradora.fragment;


import android.app.ProgressDialog;
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

import chulo.paisa.com.appadministradora.controller.PrincipalActivity;
import chulo.paisa.com.appadministradora.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private Button btnLogin;
    private EditText textEmail;
    private EditText textClave;
    private FirebaseAuth mAuth;
    private TextView txtLogin,txtEmail,txtClave;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_login, container, false);

        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");

        btnLogin =(Button) vista.findViewById(R.id.btnLogin);
        txtLogin = (TextView) vista.findViewById(R.id.txtRegistro);
        txtClave = (TextView) vista.findViewById(R.id.txtClave);
        txtEmail = (TextView) vista.findViewById(R.id.txtEmail);
        textEmail = (EditText) vista.findViewById(R.id.textEmail);
        textClave = (EditText) vista.findViewById(R.id.textClave);

        btnLogin.setTypeface(face);
        txtLogin.setTypeface(face);
        txtClave.setTypeface(face);
        txtEmail.setTypeface(face);
        textEmail.setTypeface(face);
        textClave.setTypeface(face);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("validando .. ");
                dialog.show();
                mAuth.signInWithEmailAndPassword(textEmail.getText().toString().trim(),textClave.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Intent inte = new Intent(getContext(), PrincipalActivity.class);
                            startActivity(inte);

                        }else{
                            dialog.dismiss();
                            Toast.makeText(getContext(),"usuario no logeado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return vista;
    }

}
