package com.example.tienda_onaline_de_productos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<Producto> datosProductoArrayList;
    Producto misProducto;
    LayoutInflater layoutInflater;

    public adaptadorImagenes(Context context, ArrayList<Producto> datosProductoArrayList) {
        this.context = context;
        this.datosProductoArrayList = datosProductoArrayList;
    }
    @Override
    public int getCount() {
        return datosProductoArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosProductoArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return Long.parseLong(datosProductoArrayList.get(i).getIdProducto());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.activity_listview_imagenes, viewGroup, false);
        try{
            misProducto = datosProductoArrayList.get(i);

            TextView tempVal = itemView.findViewById(R.id.lblCodigo);
            tempVal.setText(misProducto.getCodigo());

            tempVal = itemView.findViewById(R.id.lblDescripcion);
            tempVal.setText(misProducto.getDescripcion());

            tempVal = itemView.findViewById(R.id.lblMarca);
            tempVal.setText(misProducto.getMarca());

            tempVal = itemView.findViewById(R.id.lblPresentación);
            tempVal.setText(misProducto.getPresentacion());

            tempVal = itemView.findViewById(R.id.lblPrecio);
            tempVal.setText(misProducto.getPrecio());

            ImageView imgView = itemView.findViewById(R.id.imgFoto);
            Bitmap bitmap = BitmapFactory.decodeFile(misProducto.getFoto());
            imgView.setImageBitmap(bitmap);
        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar datos: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}

