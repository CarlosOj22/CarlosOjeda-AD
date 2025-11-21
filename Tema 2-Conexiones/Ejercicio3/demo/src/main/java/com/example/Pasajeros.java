package com.example;

public class Pasajeros {

    private int numPasajeros;
    //Aqui guardo codigo de vuelo no, es un objeto tipo vuelo digo yo
    private Vuelos vueloAsociado;
    private String tipoPlaza;
    private String fumador;

    //Constructor
    public Pasajeros(int numPasajeros, Vuelos vueloAsociado, String tipoPlaza, String fumador) {
        this.numPasajeros = numPasajeros;
        this.vueloAsociado = vueloAsociado;
        this.tipoPlaza = tipoPlaza;
        this.fumador = fumador;
    }//Constructor



    //GETTERS AND SETTERS:::::::
    public int getNumPasajeros() {
        return numPasajeros;
    }
    public void setNumPasajeros(int numPasajeros) {
        this.numPasajeros = numPasajeros;
    }
    public Vuelos getVueloAsociado() {
        return vueloAsociado;
    }
    public void setVueloAsociado(Vuelos vueloAsociado) {
        this.vueloAsociado = vueloAsociado;
    }
    public String getTipoPlaza() {
        return tipoPlaza;
    }
    public void setTipoPlaza(String tipoPlaza) {
        this.tipoPlaza = tipoPlaza;
    }
    public String getFumador() {
        return fumador;
    }
    public void setFumador(String fumador) {
        this.fumador = fumador;
    }
}

