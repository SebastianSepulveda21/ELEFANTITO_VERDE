package com.example.elefantitoverde;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSqlOpenHelper extends SQLiteOpenHelper {

    public AdminSqlOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table categoria(codigo integer primary key, descripcion text)");
        db.execSQL("create table producto(id integer primary key autoincrement, " +
                "nombre text, stock integer, precio double, precio_con_iva double, precio_dolar double, categoria integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists categoria");
        db.execSQL("create table categoria(codigo integer primary key, descripcion text)");
        db.execSQL("drop table if exists producto");
        db.execSQL("create table producto(id integer primary key autoincrement, " +
                "nombre text, stock integer, precio double, precio_con_iva double, precio_dolar double, categoria integer)");
    }
}
