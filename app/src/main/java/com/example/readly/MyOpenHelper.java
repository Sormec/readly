package com.example.readly;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String usuario = "CREATE TABLE usuario(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "cedula TEXT," +
            "nombres TEXT," +
            "apellidos TEXT," +
            "correo TEXT," +
            "fecha_nacimiento TEXT," +
            "nacionalidad TEXT," +
            "genero TEXT" +
            ");";
    public static final String dbName="Readly.sqlite";
    public static final int dbversion=1;
    public MyOpenHelper(Context context)
    {
        super(context, dbName, null,dbversion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usuario);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //verificación de credenciales para iniciar sesión
    public boolean checkCredenciales(String Nombre, String Cedula) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM usuario WHERE nombres = ? AND cedula = ?";
        Cursor cursor = db.rawQuery(query, new String[]{Nombre, Cedula});

        if (cursor != null) {
            boolean isUserFound = cursor.getCount() > 0;
            cursor.close();
            return isUserFound;
        }

        return false;
    }
}
