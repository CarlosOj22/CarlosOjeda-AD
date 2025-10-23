package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectaEstudiantes {

    Connection conexion;

    public void conectar(){
        try {
            conexion=DriverManager.getConnection("jdbc:mysql://localhost:33060/COLEGIOS", "root", "root");
            System.out.println("Conexion a la BD realizada correctamente...");
        } catch (Exception e) {
            System.out.println("Problemas en la conexion a la BD");
        }
    }//conectar

    public void desconectar(){
        try {
            conexion.close();
            System.out.println("Conexion a la BD cerrada correctamente...");
        } catch (Exception e) {
            System.out.println("Problemas cerrando la conexión.");
        }
    }//desconectar

    public void insertar(Estudiantes estudiante){

    }//insertar

    public void actualizarEdad(String nif,int nuevaEdad){

    }//actualizarEdad

    public void borrarPreguntado(String nif){

    }//borrarPreguntado

    public void estudiantesEntreEdades(int edadMin, int edadMax){

    }//estudiantesEntreEdades

    public void insertarDesdeArchivo(String nombreFichero){

    }//insertarDesdeArchivo
}
