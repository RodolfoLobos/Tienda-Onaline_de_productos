package com.example.tienda_onaline_de_productos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class DB extends SQLiteOpenHelper{
    private static final String dbname = "db_Productos";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE Productos(idProducto interger primary key autoincrement, Codigo text, Descripcion text, Marca text, Presentacion text, Precio text, foto text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQldb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String Administrar_Productos(String accion, String[] datos){
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (accion.equals("nuevo")){
                db.execSQL("INSERT INTO Productos(Codigo,Descripcion,Marca,Presentacion,Precio, foto) VALUES('"+ datos[1]+"','"+ datos[2]+"','"+ datos[3]+"','"+ datos[4]+"','"+datos[5] +"', '"+ datos[6] +"')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE Productos SET Codigo='" + datos[1] + "',Descripcion='" + datos[2] + "',Marca='" + datos[3] + "',Presentacion='" + datos[4] + "',Precio='" + datos[5] + "',foto='" + datos[6] + "' WHERE idProducto='" + datos[0] + "'");
            } else if (accion.equals("eliminar")) {

                db.execSQL("DELETE FROM Productos WHERE idProducto='"+ datos[0] +"'");
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos ORDER BY Codigo", null);
        return cursor;
    }
}

