package com.example.elefantitoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleProducto extends AppCompatActivity {

    AdminSqlOpenHelper conn;
    TextView txtId, txtNombre, txtStock, txtPrecio, txtIva, txtDolar, txtIdCat, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        conn=new AdminSqlOpenHelper(getApplicationContext(),"elefantito_verde",null,1);

        txtId=(TextView)findViewById(R.id.campoId);
        txtNombre=(TextView)findViewById(R.id.campoNombre);
        txtStock=(TextView)findViewById(R.id.campoStock);
        txtPrecio=(TextView)findViewById(R.id.campoPrecio);
        txtIva=(TextView)findViewById(R.id.campoPrecioConIva);
        txtDolar=(TextView)findViewById(R.id.campoPrecioDolar);
        txtIdCat=(TextView)findViewById(R.id.campoIdCategoria);
        txtDescripcion=(TextView)findViewById(R.id.campoDescripcion);

        Bundle objetoEnviado=getIntent().getExtras();
        Producto pro=null;

        if(objetoEnviado!=null){
            pro= (Producto) objetoEnviado.getSerializable("producto");

            txtId.setText(pro.getId().toString());
            txtNombre.setText(pro.getNombre().toString());
            txtStock.setText(pro.getCantidad().toString());
            int stock=Integer.parseInt(txtStock.getText().toString());
            if(stock>0 && stock<=5){
                txtStock.setTextColor(Color.RED);
            }if(stock>=6 && stock<20){
                txtStock.setTextColor(Color.YELLOW);
            }if (stock>=20){
                txtStock.setTextColor(Color.GREEN);
            }
            txtPrecio.setText(String.valueOf(pro.getPrecio()));
            txtIva.setText(String.valueOf(pro.getPrecioConIva()));
            txtDolar.setText(String.valueOf(pro.getPrecioDolar()));
            consultarCategoria(pro.getIdCat());
        }
    }

    private void consultarCategoria(Integer idCategoria){
        SQLiteDatabase db=conn.getReadableDatabase();
        try{
            Cursor cursor=db.rawQuery("select descripcion from categoria where codigo="+idCategoria,null);
            if (cursor.moveToFirst()){
                txtIdCat.setText(idCategoria.toString());
                txtDescripcion.setText(cursor.getString(0));
                cursor.close();
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"No se encontro la categoría",Toast.LENGTH_LONG).show();
            txtIdCat.setText("");
            txtDescripcion.setText("");
        }
    }
}