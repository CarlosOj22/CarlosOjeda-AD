package com.example;

public class Empleado {
    String nombre;
    int antiguedad;
    int edad;

    Hijo inforUnHijo;

    Empleado(String n, int ant, int e, Hijo h) {
        nombre=n;
        antiguedad=ant;
        edad=e;
        inforUnHijo=h;
    }

    void visDatosEmpleados() {
        System.out.println("\n"+nombre+ " tiene " +edad+ " años, lleva " +antiguedad+ " años en la empresa");
        if (inforUnHijo != null) {
            System.out.println("Los datos de su hijo son : ");
            inforUnHijo.visualHijo();
        }
    }

    void cumpleAños () {
        edad++;
    }
}