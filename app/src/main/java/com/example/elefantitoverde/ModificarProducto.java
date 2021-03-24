package com.example.elefantitoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ModificarProducto extends AppCompatActivity {

    Spinner spnProducto;
    EditText txtNombre, txtStock, txtPrecio, txtPrecioIVA, txtPrecioDolar, txtId;
    Button btnEliminar;
    AdminSqlOpenHelper conn;
    ArrayList<String> listaProducto;
    ArrayList<Producto> productosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        conn= new AdminSqlOpenHelper(getApplicationContext(),"elefantito_verde",null,1);

        txtId=(EditText)findViewById(R.id.txtId2);
        txtNombre=(EditText)findViewById(R.id.txtNombre2);
        txtStock=(EditText)findViewById(R.id.txtStock2);
        txtPrecio=(EditText)findViewById(R.id.txtPrecio2);
        txtPrecioIVA=(EditText)findViewById(R.id.txtPrecioConIva2);
        txtPrecioDolar=(EditText)findViewById(R.id.txtPrecioDolar2);
        spnProducto=(Spinner)findViewById(R.id.spnProductoMod);
        btnEliminar=(Button)findViewById(R.id.btnEliminarProducto);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto();
            }
        });

        consultarListaProductos();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaProducto);
        spnProducto.setAdapter(adaptador);

        spnProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    txtId.setText(productosList.get(position-1).getId().toString());
                    txtNombre.setText(productosList.get(position-1).getNombre());
                    txtStock.setText(productosList.get(position-1).getCantidad().toString());
                    txtPrecio.setText(String.valueOf(productosList.get(position-1).getPrecio()));
                    txtPrecioIVA.setText(String.valueOf(productosList.get(position-1).getPrecioConIva()));
                    txtPrecioDolar.setText(String.valueOf(productosList.get(position-1).getPrecioDolar()));

                }else{
                    txtId.setText("");
                    txtNombre.setText("");
                    txtStock.setText("");
                    txtPrecio.setText("");
                    txtPrecioIVA.setText("");
                    txtPrecioDolar.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eliminarProducto() {
        SQLiteDatabase ldb=conn.getWritableDatabase();
        String id=txtId.getText().toString();
        ldb.delete("producto", "id='"+id+"'",null);
        ldb.close();
        Toast.makeText(this, "Se elimino el producto: "+ txtId.getText().toString()+"\n"+"de la base de datos", Toast.LENGTH_LONG).show();
        spnProducto.setSelection(0);
        txtId.setText("");
        txtNombre.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
        txtPrecioIVA.setText("");
        txtPrecioDolar.setText("");
    }

    private void consultarListaProductos() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Producto pro=null;
        productosList=new ArrayList<Producto>();

        Cursor cursor=db.rawQuery("select id, nombre, stock, precio, precio_con_iva, precio_dolar from producto",null);
        while(cursor.moveToNext()){
            pro=new Producto();
            pro.setId(cursor.getInt(0));
            pro.setNombre(cursor.getString(1));
            pro.setCantidad(cursor.getInt(2));
            pro.setPrecio(cursor.getDouble(3));
            pro.setPrecioConIva(cursor.getDouble(4));
            pro.setPrecioDolar(cursor.getDouble(5));

            productosList.add(pro);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaProducto=new ArrayList<String>();
        listaProducto.add("Seleccione");

        for(int i=0; i<productosList.size(); i++){
            listaProducto.add(productosList.get(i).getId()+" - "+productosList.get(i).getNombre());
        }
    }
}