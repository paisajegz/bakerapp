package chulo.paisa.com.appadministradora.fragment.principal;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.modelo.Local;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment {

    private Button btnSaveLocal;
    private EditText textNombreLocalidad;
    private EditText textLactitud;
    private EditText textLongitud;
    private TextView  txtNombreLocalidad,txtLactitud,txtLongitud;

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_mapa, container, false);

        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");
        btnSaveLocal =(Button) vista.findViewById(R.id.btnSaveLocal);
        textNombreLocalidad = (EditText) vista.findViewById(R.id.textNombreLocal);
        textLactitud = (EditText) vista.findViewById(R.id.textLactitud);
        textLongitud = (EditText) vista.findViewById(R.id.textLongitud);
        txtNombreLocalidad = (TextView) vista.findViewById(R.id.txtNombreLocal);
        txtLactitud = (TextView) vista.findViewById(R.id.ttxLactitud);
        txtLongitud = (TextView) vista.findViewById(R.id.txtLongitud);

        btnSaveLocal.setTypeface(face);
        textNombreLocalidad.setTypeface(face);
        textLongitud.setTypeface(face);
        textLactitud.setTypeface(face);
        txtNombreLocalidad.setTypeface(face);
        txtLactitud.setTypeface(face);
        txtLongitud.setTypeface(face);


        btnSaveLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("locales");
                Local local = new Local();
                local.setNombre(textNombreLocalidad.getText().toString().trim());
                local.setLactitud(textLactitud.getText().toString().trim());
                local.setLongitud(textLongitud.getText().toString().trim());
                myRef.child(local.getNombre()).setValue(local);
                Toast.makeText(getContext(),"local subido sastifactoriamnete",Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

}