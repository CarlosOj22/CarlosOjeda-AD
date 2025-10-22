package com.example;

import java.sql.Statement;
import java.util.Scanner;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                        //Conexion es = metodo estatico d drievrmanager getconection       
                        conOracle = DriverManager.getConnection(urljdbc, "carlos", "1234");
                        System.out.println("Conexion Realizada a Oracle correctamente.");
                        //Creamos objeto sentencia que devuelve metodo create Statement de la conexion 
                        Statement sentencia = conOracle.createStatement();
                        //Consula string (FIJARTE BIEN EN NOMBRE COLUMNAS)
                        String oracleConsulta = "SELECT * FROM Depart"; 
                        //Objeto que leera los datos de las tablas,result set, con metodo ejecutar de la sentencia
                        ResultSet resul = sentencia.executeQuery(oracleConsulta);
                        //Recorremos objeto resultset
                        //AMPLIACION SACAAR TODOS LOS EMPLEADOS DE ESE DEPARTAMENTO
                        Statement sentenciaSacarEmpleados = conOracle.createStatement();
                        System.out.println("NUM DEP----DEP NOMBRE----LOCALIDAD");
                        while(resul.next()){
                            System.out.println(resul.getInt(1) + "\t" + resul.getString(2) +"\t" +resul.getString(3));
                            String sacarEmpleados= "SELECT APELLIDO FROM EMPLE WHERE DEPT_NO=" + resul.getInt(1);
                            System.out.println("EMPLEADOS DE ESE DEPARTAMENTO: ");
                            ResultSet empleados= sentenciaSacarEmpleados.executeQuery(sacarEmpleados);
                            while(empleados.next()){
                                System.out.println(empleados.getString(1));
                            }
                        }
                        resul.close();
                        sentencia.close();
                        conOracle.close();
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la conexion Oracle");
                    }
                    break;

                case "2":

                        Connection conMySql;       
                        Scanner sc = new Scanner(System.in);
                        try {     
                            // Establecemos la conexion con la BD     
                            conMySql= DriverManager.getConnection("jdbc:mysql://localhost:33060/empresa", "root", "root");
                            System.out.println("Conexion Realizada a MySQL correctamente.");
                            String consultaMySql = "DELETE FROM EMPLE WHERE DEPT_NO=?";
                            PreparedStatement sentenciaMySql = conMySql.prepareStatement(consultaMySql);
                            System.out.println("Introduce departamento para borrar sus empleados: ");
                            int departamento=sc.nextInt();
                            //AI QUE PASARLE PARAMETRO
                            //VER LO DE MAS POSICIONES MAS LLAMADAS A SETINT PREGUNTAR
                            //Y PREGUNTAR SI SE GUARDAN CAMBIOS EN LA BASE DED ATOS QUE AL SALIR ME DECIA DE GUARDAR O NO D¿EN DEVELOPER
                            sentenciaMySql.setInt(1, departamento);
                            //AHORA NO ES EXECUTE QUERY EN UPDATE
                            sentenciaMySql.executeUpdate();
                            System.out.println("Borrado con exito");

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
