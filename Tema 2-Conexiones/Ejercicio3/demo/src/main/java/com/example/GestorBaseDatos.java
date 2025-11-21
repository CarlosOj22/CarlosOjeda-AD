package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class GestorBaseDatos {
    private Connection conexion;

    //QUIZA EN EL CONSTRUCTOR SOLO PASO Y CREO LA CADENA; Y EN EL CREAR CONEXION HAGO GET CONECCTION O QUE
    public GestorBaseDatos(String baseDatos, String usuario, String contraseña) {
        try {
            conexion=DriverManager.getConnection("jdbc:mysql://localhost:33060/"+baseDatos,usuario,contraseña);
            System.out.println("Conexion a la BD realizada correctamente...");
        } catch (Exception e) {
            System.out.println("Problemas en la conexion a la BD...");
        }
    }
    //Le voy a cambiar el nombre de creaCOnexion como dice la practica a devuelveCOne
    //No se por que le pone ese nombre si la conexion pone que se inicia en el consteuctor
    public Connection devuelveConexion(){
        return conexion;
    }

    public void desconectar(){
        try{
            conexion.close();
            System.out.println("Conexion cerrada con exito...");
        }catch (Exception e) {
            System.out.println("Problemas cerrando la conexión.");
        }
    }
}
