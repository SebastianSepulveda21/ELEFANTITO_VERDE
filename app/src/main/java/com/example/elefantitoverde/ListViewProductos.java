package com.example.elefantitoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewProductos extends AppCompatActivity {

    ListView listViewProductos;
    ArrayList<String> listaInformacion;
    ArrayList<Producto> listaProductos;
    AdminSqlOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_productos);

        listViewProductos=(ListView)findViewById(R.id.listViewProductos);
        conn=new AdminSqlOpenHelper(this,"elefantito_verde",null,1);

        consultarListaProductos();

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaInformacion);
        listViewProductos.setAdapter(adapter);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info="id: "+listaProductos.get(position).getId()+"\n";
                info+="nombre: "+listaProductos.get(position).getNombre()+"\n";
                info+="stock: "+listaProductos.get(position).getCantidad()+"\n";
                info+="id_categoria"+listaProductos.get(position).getIdCat();

                Toast.makeText(getApplicationContext(),info,Toast.LENGTH_LONG).show();

                Producto pro=listaProductos.get(position);
                Intent intent=new Intent(ListViewProductos.this,DetalleProducto.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("producto",pro);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void consultarListaProductos() {

        SQLiteDatabase db=conn.getReadableDatabase();
        Producto producto=null;
        listaProductos=new ArrayList<Producto>();
        Cursor cursor=db.rawQuery("select * from producto",null);

        while (cursor.moveToNext()){
            producto=new Producto();

            producto.setId(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setCantidad(cursor.getInt(2));
            producto.setPrecio(cursor.getDouble(3));
            producto.setPrecioConIva(cursor.getDouble(4));
            producto.setPrecioDolar(cursor.getDouble(5));
            producto.setIdCat(cursor.getInt(6));

            listaProductos.add(producto);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for(int i=0 ; i<listaProductos.size() ; i++){
            listaInformacion.add(listaProductos.get(i).getId()+" - "+listaProductos.get(i).getNombre()
                    +"\n"+"Stock del Producto: "+listaProductos.get(i).getCantidad());

        }

    }
}