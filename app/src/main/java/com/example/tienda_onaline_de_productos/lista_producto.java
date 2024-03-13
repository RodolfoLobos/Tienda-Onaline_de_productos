package com.example.tienda_onaline_de_productos;


import java.util.ArrayList;
import java.util.Locale;import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class lista_producto extends AppCompatActivity {

    Bundle parametros = new Bundle();
    FloatingActionButton btn;
    ListView lts;
    Cursor cProducto;
    DB dbProducto;
    Producto misProducto;
    final ArrayList<Producto> alProducto=new ArrayList<Producto>();
    final ArrayList<Producto> alProductoCopy=new ArrayList<Producto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto);

        btn = findViewById(R.id.btnAbrirNuevosProducto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
            }
        });
        obtenerProducto();
        buscarProducto();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
        cProducto.moveToPosition(info.position);
        menu.setHeaderTitle(cProducto.getString(1));//1 es el codigo del producto
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;
                case R.id.mnxModificar:
                    String Productos[] = {
                            cProducto.getString(0), //idAmigo
                            cProducto.getString(1), //nombre
                            cProducto.getString(2), //direccion
                            cProducto.getString(3), //tel
                            cProducto.getString(4), //email
                            cProducto.getString(5), //dui
                            cProducto.getString(6)  //foto
                    };
                    parametros.putString("accion","modificar");
                    parametros.putStringArray("Producto", Productos);
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarProducto();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error en menu: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProducto(){
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_producto.this);
            confirmacion.setTitle("Esta seguro de Eliminar a: ");
            confirmacion.setMessage(cProducto.getString(1));
            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String respuesta = dbProducto.Administrar_Productos("eliminar", new String[]{cProducto.getString(0)});
                    if (respuesta.equals("ok")) {
                        mostrarMsg("Amigo eliminado con exito.");
                        obtenerProducto();
                    } else {
                        mostrarMsg("Error al eliminar el producto: " + respuesta);
                    }
                }
            });
            confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }
    private void abrirActividad(Bundle parametros){
        Intent abriVentana = new Intent(getApplicationContext(), MainActivity.class);
        abriVentana.putExtras(parametros);
        startActivity(abriVentana);
    }
    private void obtenerProducto(){
        try{
            alProducto.clear();
            alProductoCopy.clear();

            dbProducto = new DB(lista_producto.this, "", null, 1);
            cProducto = dbProducto.consultar_Productos();

            if ( cProducto.moveToFirst() ){
                lts = findViewById(R.id.ltsProducto);
                do{
                    misProducto = new Producto(
                            cProducto.getString(0),//idAmigo
                            cProducto.getString(1),//Codigo
                            cProducto.getString(2),//Descripcion
                            cProducto.getString(3),//Marca
                            cProducto.getString(4),//Presentacion
                            cProducto.getString(5),//Precio
                            cProducto.getString(6) //foto
                    );
                    alProducto.add(misProducto);
                }while(cProducto.moveToNext());
                alProductoCopy.addAll(alProducto);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProducto);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else{
                mostrarMsg("No hay producto que mostrar");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener el producto: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void buscarProducto(){
        TextView tempVal;
        tempVal = findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    alProducto.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alProducto.addAll(alProductoCopy);
                    }else{
                        for( Producto Producto : alProductoCopy ){
                            String Codigo = Producto.getCodigo();
                            String Descripcion =Producto.getDescripcion();
                            String Marca = Producto.getMarca();
                            String Presentacion = Producto.getPresentacion();
                            if( Codigo.toLowerCase().trim().contains(valor) ||
                                    Descripcion.toLowerCase().trim().contains(valor) ||
                                    Marca.trim().contains(valor) ||
                                    Presentacion.trim().toLowerCase().contains(valor) ){
                                alProducto.add(Producto);
                            }
                        }
                        adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProducto);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+e.getMessage() );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

