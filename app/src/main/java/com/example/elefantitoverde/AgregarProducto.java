package com.example.elefantitoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AgregarProducto extends AppCompatActivity {

    EditText txtNombre, txtStock, txtPrecio, txtPrecioIVA, txtPrecioDolar;
    Button btnCalcular, btnAgregar;
    Spinner spnCategoria;
    ArrayList<String> listaCategoria;
    ArrayList<Categoria> categoriaList;
    AdminSqlOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        conn = new AdminSqlOpenHelper(this, "elefantito_verde", null, 1);

        txtNombre=(EditText)findViewById(R.id.txtNomProducto);
        txtStock=(EditText)findViewById(R.id.txtStock);
        txtPrecio=(EditText)findViewById(R.id.txtPrecioSinIva);
        txtPrecioIVA=(EditText)findViewById(R.id.txtPrecioConIva);
        txtPrecioDolar=(EditText)findViewById(R.id.txtPrecioDolar);
        btnCalcular=(Button)findViewById(R.id.btnCalcular);
        btnAgregar=(Button)findViewById(R.id.btnAgregarProducto);

        spnCategoria=(Spinner)findViewById(R.id.comboCategoria);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        consultarListaCategoria();

        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaCategoria);
        spnCategoria.setAdapter(adaptador);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });
    }

    private void agregarProducto() {
        SQLiteDatabase ldb=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        String nombre=txtNombre.getText().toString();
        String stock=txtStock.getText().toString();
        String precio=txtPrecio.getText().toString();

        if (nombre.trim().length() > 1){
            values.put("nombre",nombre);
            if (stock.trim().length() > 0){
                values.put("stock",stock);
                if (precio.trim().length() > 0){
                    values.put("precio", precio);
                    values.put("precio_con_iva",txtPrecioIVA.getText().toString());
                    values.put("precio_dolar",txtPrecioDolar.getText().toString());

                    int idCombo=(int)spnCategoria.getSelectedItemId();
                    if (idCombo!=0){
                        Log.i("Tamaño",categoriaList.size()+"");
                        Log.i("id combo",idCombo+"");
                        Log.i("id combo - 1",(idCombo-1)+"");
                        int idCategoria=categoriaList.get(idCombo-1).getId();
                        Log.i("id categegoria",idCategoria+"");

                        values.put("categoria", idCategoria);

                        ldb.insert("producto",null,values);
                        ldb.close();
                        Toast.makeText(this, "Se guardaron los datos del producto en la base de datos"+"\n"+"nombre="+nombre.toString()+"\n"+"stock="+stock, Toast.LENGTH_LONG).show();

                        limpiarCampos();

                    }else{
                        Toast.makeText(this, "Debe seleccionar la categoria del producto"+"\n"+"(Si no hay categorias, debe agregarlas primero)", Toast.LENGTH_LONG).show();
                    }

                }else{
                    txtPrecio.setError("Escriba el precio del producto");
                }
            }else{
                txtStock.setError("Escriba la cantidad de stock del producto");
            }
        }else{
            txtNombre.setError("Escriba el nombre del producto");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtNombre.setHint("Nombre del Producto");

        txtStock.setText("");
        txtStock.setHint("Stock");

        txtPrecio.setText("");
        txtPrecio.setHint("Precio sin IVA");

        txtPrecioIVA.setText("");
        txtPrecioIVA.setHint("Precio con IVA");

        txtPrecioDolar.setText("");
        txtPrecioDolar.setHint("Precio en Dolares");

        spnCategoria.setSelection(0);
    }

    private void consultarListaCategoria() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Categoria cat=null;
        categoriaList=new ArrayList<Categoria>();

        Cursor cursor=db.rawQuery("select * from categoria",null);
        while(cursor.moveToNext()){
            cat=new Categoria();
            cat.setId(cursor.getInt(0));
            cat.setDescripcion(cursor.getString(1));

            categoriaList.add(cat);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaCategoria=new ArrayList<String>();
        listaCategoria.add("Seleccione");

        for(int i=0; i<categoriaList.size(); i++){
            listaCategoria.add(categoriaList.get(i).getId()+" - "+categoriaList.get(i).getDescripcion());
        }
    }

    public void calcular(){
        int precio=Integer.parseInt(txtPrecio.getText().toString());
        String precioVal = txtPrecio.getText().toString();
        double iva=1.19;
        double dolar=768.70;
        DecimalFormat format = new DecimalFormat("#.00");

        double precioIva=precio*iva;
        double precioDolar=precio/dolar;

        txtPrecioIVA.setText(String.valueOf(format.format(precioIva)));
        txtPrecioDolar.setText(String.valueOf(format.format(precioDolar)));
    }
}