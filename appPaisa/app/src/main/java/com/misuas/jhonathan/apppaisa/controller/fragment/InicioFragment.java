package com.misuas.jhonathan.apppaisa.controller.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misuas.jhonathan.apppaisa.R;

public class InicioFragment extends Fragment {

    private TextView txtBienvenido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_inicio, container, false);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"news706.ttf");
        txtBienvenido = (TextView) vista.findViewById(R.id.txtBienvenido);
        txtBienvenido.setTypeface(face);
        return vista;
    }
}
