package com.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorGenerico {
    public static void main(String[] args) {
            if(args.length!=0){

            switch (args[0]) {
                
                case "1":
                Connection conOracle;
                    try {
                        String urljdbc = "jdbc:oracle:thin:carlos/1234@localhost:49161:XE";      
                        //la base de datos esta en local y el servicio Oracle es XE     
                        //se utiliza thin driver              
                        conOracle = DriverManager.getConnection(urljdbc, "carlos", "1234");
                        System.out.println("Conexion Realizada a Oracle correctamente.");
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la conexion Oracle");
                    }
                    break;

                case "2":

                        Connection conMySql;       
                        try {     
                            // Establecemos la conexion con la BD     
                            conMySql= DriverManager.getConnection("jdbc:mysql://localhost:33060/empresa", "root", "root");
                            System.out.println("Conexion Realizada a MySQL correctamente.");
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la conexion MySql");
                    }
                    break;

                case "3":
                    String url="jdbc:sqlite:empresa.db";
                    Connection conSqlite;
                    try {
                        File f= new File("C:\\DAM2\\AD\\Tema 2-Conexiones\\Ejercicio1-ConexionesJDBC\\demo\\src\\main\\java\\com\\example\\empresa.db");
                        //si no comprobamos si existe, la crearía nueva en caso de no existir
                        if(f.exists()){
                            try {
                                conSqlite=DriverManager.getConnection(url);
                                System.out.println("Conexion Realizada a SQLite correctamente.");
                            } catch (SQLException e) {
                               System.out.println(e.getMessage());
                            }
                        }else{
                            //Voy a DB Browser for SQLite para crear la base de datos
                            //en la misma carpeta que proyecto
                            System.out.println("La base de datos no existe");
                        }
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la conexion SQLite");
                    }


                    case "4":
                        //URL de conexión para PostgreSQL
                        String urlPostGre = "jdbc:postgresql://localhost:5432/empresa"; 
                        String userPostGre = "admin"; //Usuario que he creado en Docker
                        String passwordPostGre = "admin123"; //Contraseña que he creado en Docker

                        Connection conPostgres;
                        try {
                            //PostGre va por Docker
                            conPostgres = DriverManager.getConnection(urlPostGre, userPostGre, passwordPostGre);
                            System.out.println("Conexión realizada a PostgreSQL correctamente.");
                            
                        } catch (SQLException e) {
                            System.out.println("No se ha podido realizar la conexion PostgreSQL");
                        }

                        break;
                default:
                    System.out.println("Problemas en la conexion");
                    break;
            }
         }
    }
}
