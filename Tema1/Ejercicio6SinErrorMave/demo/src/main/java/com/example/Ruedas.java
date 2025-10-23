package com.example;

public class Ruedas {
    private int numero;
    private String tipo;
    public Ruedas(int numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    } 
}
