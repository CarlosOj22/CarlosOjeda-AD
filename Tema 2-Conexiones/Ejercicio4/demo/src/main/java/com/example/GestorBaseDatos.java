//Carlos Ojeda Corona-Ejercicio 4
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class GestorBaseDatos {
    private Connection conexion;

    public GestorBaseDatos(String usuario, String contraseña) {
       try {
            String urljdbc = "jdbc:oracle:thin:carlos/1234@localhost:49161:XE";        
            conexion = DriverManager.getConnection(urljdbc, usuario, contraseña);
            System.out.println("Conexion Realizada a Oracle correctamente.");
        } catch (Exception e) {
            System.out.println("Problemas en la conexion a la BD...");
        }
    }
    
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