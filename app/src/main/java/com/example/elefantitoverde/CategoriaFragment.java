package com.example.elefantitoverde;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CategoriaFragment extends Fragment {
    View vista;
    Button btnAgregar;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_categoria, container, false);
        btnAgregar=(Button)vista.findViewById(R.id.btnAgrCategoria);
        
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCategoria();
            }
        });
        return vista;
    }

    private void agregarCategoria() {
        Intent i = new Intent(getContext(), AgregarCategoria.class);
        startActivity(i);
    }
}