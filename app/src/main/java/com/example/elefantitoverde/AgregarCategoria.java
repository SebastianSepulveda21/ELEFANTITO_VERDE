package com.example.elefantitoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarCategoria extends AppCompatActivity {

    EditText txtId, txtDesc;
    Button btnAgregar, btnBuscar;
    AdminSqlOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        conn = new AdminSqlOpenHelper(this, "elefantito_verde", null, 1);

        txtId=(EditText)findViewById(R.id.txtCodigo);
        txtDesc=(EditText)findViewById(R.id.txtDescripcion);
        btnAgregar=(Button)findViewById(R.id.btnAgregarCategoria);
        btnBuscar=(Button)findViewById(R.id.btnBuscarCategoria);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
    }

    private void buscar() {
        SQLiteDatabase ldb = conn.getReadableDatabase();
        String id=txtId.getText().toString();
        Cursor fila=ldb.rawQuery("select descripcion from categoria where codigo="+id,null);
        if (fila.moveToFirst()){
            txtDesc.setText(fila.getString(0));
        }else{
            Toast.makeText(this, "No se encontro categoria", Toast.LENGTH_SHORT).show();
        }
        ldb.close();
    }

    private void agregar() {
        SQLiteDatabase ldb = conn.getWritableDatabase();

        String id = txtId.getText().toString();
        String descripcion = txtDesc.getText().toString();
        if (id.trim().length() > 0){
            ContentValues cv=new ContentValues();
            cv.put("codigo", id);
            if (descripcion.trim().length() > 1){
                cv.put("descripcion",descripcion);
                ldb.insert("categoria",null,cv);
                ldb.close();
                Toast.makeText(this, "Se guardaron los datos de la categoria"+"\n"+"Id="+id+"\n"+"Descripcion="+descripcion, Toast.LENGTH_LONG).show();
                limpiar();
            }else {
                txtDesc.setError("Escriba el Nombre de la categoria");
            }
        }else{
            txtId.setError("Escriba el código de la categoria");
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtDesc.setText("");
    }
}