package com.example.elefantitoverde;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProductosFragment extends Fragment {
    View vista;
    Button btnAgregar, btnProductos, btnDetalles;

    public ProductosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_productos, container, false);
        btnAgregar=(Button)vista.findViewById(R.id.btnAgrProducto);
        btnProductos=(Button)vista.findViewById(R.id.btnVerProducto);
        btnDetalles=(Button)vista.findViewById(R.id.btnDetalle);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver();
            }
        });
        
        btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle();
            }
        });

        return vista;
    }

    private void detalle() {
        Intent i = new Intent(getContext(), ModificarProducto.class);
        startActivity(i);
    }

    private void ver() {
        Intent i = new Intent(getContext(), ListViewProductos.class);
        startActivity(i);
    }

    private void agregar() {
        Intent i = new Intent(getContext(), AgregarProducto.class);
        startActivity(i);
    }
}