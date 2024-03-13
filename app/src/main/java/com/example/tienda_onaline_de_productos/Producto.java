package com.example.tienda_onaline_de_productos;

public class Producto {

    String idProducto;
    String Codigo;
    String Descripcion;
    String Marca;
    String Presentacion;
    String Precio;
    String foto;
public Producto(String idProducto, String Codigo, String Descripcion, String Marca, String Presentacion, String Precio, String foto) {
    this.idProducto = idProducto;
    this.Codigo = Codigo;
    this.Descripcion = Descripcion;
    this.Marca = Marca;
    this.Presentacion = Presentacion;
    this.Precio = Precio;
    this.foto = foto;
}
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
}
public String getIdProducto() {
        return idProducto;
}

public void setIdProducto(String idProducto){
    this.idProducto = idProducto;
}

public String getCodigo(){
    return Codigo;
}

public void setCodigo(String Codigo){
    this.Codigo = Codigo;
}
public String getDescripcion(){
    return Descripcion;
}
public void setDescripcion(String Descripcion){
    this.Descripcion = Descripcion;
}
public String getMarca(){
    return Marca;
}

public void setMarca(String Marca){
    this.Marca = Marca;
}

public String getPresentacion(){
    return Presentacion;
}

public void setPresentacion(String Presentacion){
    this.Presentacion = Presentacion;
}

public String getPrecio(){
    return Precio;
}
public void setPrecio(String Precio){
    this.Precio = Precio;
}

}

