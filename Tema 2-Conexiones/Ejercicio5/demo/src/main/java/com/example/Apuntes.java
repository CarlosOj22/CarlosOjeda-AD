//Carlos Ojeda Corona-Ejercicio 5
package com.example;

public class Apuntes {
    //---Atributos---
    int idProducto;
    String producto;
    double precioUnidad;
    int cantidad;
    int idFactura;

    //--Constructor--
    public Apuntes(int idProducto, String producto, double precioUnidad, int cantidad, int idFactura) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.precioUnidad = precioUnidad;
        this.cantidad = cantidad;
        this.idFactura = idFactura;
    }//Constructor

    //---Getters & Setters---
    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    public double getPrecioUnidad() {
        return precioUnidad;
    }
    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    //---Getters & Setters---
}
