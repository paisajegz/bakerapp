package chulo.paisa.com.appadministradora.fragment.principal;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.adapter.CategoriaAdapter;
import chulo.paisa.com.appadministradora.modelo.Categoria;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private RecyclerView recycleCategorias;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_product, container, false);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("cargando ...");
        dialog.show();
        recycleCategorias = (RecyclerView) vista.findViewById(R.id.recycleCategoria);
         final ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();
        recycleCategorias.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("categoria");

        final CategoriaAdapter adapter = new CategoriaAdapter(listaCategoria,getActivity());
        recycleCategorias.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                listaCategoria.remove(listaCategoria);
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Categoria categoria = data.getValue(Categoria.class);
                    listaCategoria.add(categoria);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return vista;
    }

}