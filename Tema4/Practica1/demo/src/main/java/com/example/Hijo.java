package com.example;


public class Hijo {
    String nombre;
    int edad;

    Hijo(String nombre, int ed) {
        this.nombre=nombre;
        edad=ed;
    }

    void visualHijo() {
        System.out.println("\n\tNOMBRE: " +nombre);
        System.out.println("\n\tEDAD: " +edad);
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }
}
