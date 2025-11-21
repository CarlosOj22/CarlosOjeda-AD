//Carlos Ojeda Corona-Ejercicio 5
package com.example;

public class Facturas {
    //---Atributos---
    int numeroFactura;
    String nombreCliente;
    Apuntes[] apuntes;

    //---Constructor---
    public Facturas(int numeroFactura, String nombreCliente) {
        this.numeroFactura = numeroFactura;
        this.nombreCliente = nombreCliente;
    }//Constructor
    //---Constructor---
    public Facturas(int numeroFactura, String nombreCliente, Apuntes[] apuntes) {
        this.numeroFactura = numeroFactura;
        this.nombreCliente = nombreCliente;
        this.apuntes = apuntes;
    }//Constructor

    //---Getters & Setters---
    public int getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Apuntes[] getApuntes() {
        return apuntes;
    }

    public void setApuntes(Apuntes[] apuntes) {
        this.apuntes = apuntes;
    }

    //---Getters & Setters---
}
