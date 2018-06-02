package chulo.paisa.com.appadministradora.fragment.principal;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.controller.categoria.SubirFotoCatActivity;

public class CategoriaFragment extends Fragment {

    private EditText textCategoria;
    private Button subirCategoria;
    private TextView txtCategoria;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_categoria, container, false);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");

        textCategoria = (EditText) vista.findViewById(R.id.textCategoria);
        subirCategoria = (Button) vista.findViewById(R.id.btnSubirCat);
        txtCategoria = (TextView) vista.findViewById(R.id.txtCategoria);

        textCategoria.setTypeface(face);
        subirCategoria.setTypeface(face);
        txtCategoria.setTypeface(face);

        subirCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getContext(), SubirFotoCatActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("categoria",textCategoria.getText().toString().trim());
                inte.putExtras(bundle);
                startActivity(inte);
            }
        });
        return  vista;
    }
}