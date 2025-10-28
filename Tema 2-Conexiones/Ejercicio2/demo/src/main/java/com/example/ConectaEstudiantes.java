package com.example;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConectaEstudiantes {

    Connection conexion;
    
    public ConectaEstudiantes(){//Pongo la conexion en el constructor
        try {
            conexion=DriverManager.getConnection("jdbc:mysql://localhost:33060/COLEGIO", "root", "root");
            System.out.println("Conexion a la BD realizada correctamente...");
        } catch (Exception e) {
            System.out.println("Problemas en la conexion a la BD");
        }
    }//Constructor

    public void desconectar(){
        try {
            conexion.close();
            System.out.println("Conexion a la BD cerrada correctamente...");
        } catch (Exception e) {
            System.out.println("Problemas cerrando la conexión.");
        }
    }//desconectar

    public void insertar(Estudiantes estudiante){
        try {   
            //VER ORDEN DE COLUMNASS!!
                String estudianteInsert="INSERT INTO ESTUDIANTES (NIF,NOMBRE,EDAD) " + 
                "VALUES (?,?,?)";
                //DEBERIA DECLAR EL PREPARED STATEMENT FUERA O TENGO QUE USAR UNO NUEVO CADA VEZ??
                PreparedStatement sentenciaInsert = conexion.prepareStatement(estudianteInsert);
                //HAY QUE PONERLO EN ORDEN COMO EN LAS TABLAS NO??
                sentenciaInsert.setInt(1, estudiante.getNif());
                sentenciaInsert.setString(2, estudiante.getNombre());
                sentenciaInsert.setInt(3, estudiante.getEdad());
                //ME GUARDO LAS FILAS AÑADIDAS, INSERT, UPDATE, DELETE SE HACEN CON EXECUTE UPDATE
                int filas = sentenciaInsert.executeUpdate();
                System.out.println("Numero filas insertadas " + filas);
                sentenciaInsert.close();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la Inserción de Estudiantes");
        }
 
    }//insertar

    public void actualizarEdad(int nif,int nuevaEdad){
        try {
            String sentenciaUpdateEdad = "UPDATE ESTUDIANTES SET EDAD=? WHERE NIF=?";
            PreparedStatement sentenciaUpdate = conexion.prepareStatement(sentenciaUpdateEdad);
            sentenciaUpdate.setInt(1, nuevaEdad);
            sentenciaUpdate.setInt(2, nif);
            int filas = sentenciaUpdate.executeUpdate();
            System.out.println("Numero filas actualizadas " + filas);
            System.out.println("Actualización realizada correctamente");
            sentenciaUpdate.close();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la actualización de la edad del Estudiante");
        }
    }//actualizarEdad

    public void borrarPreguntado(int nif){
        try {
            String sentenciaDeletePreguntado = "DELETE FROM ESTUDIANTES WHERE NIF=?";
            PreparedStatement sentenciaDelete = conexion.prepareStatement(sentenciaDeletePreguntado);
            sentenciaDelete.setInt(1, nif);
            int filas=sentenciaDelete.executeUpdate();
            System.out.println("Numero de filas borradas: " + filas);
            sentenciaDelete.close();
            //PREGUNTAR POR ROLLBACKS Y COMMIT LO HACE JAVA O QUE?

        } catch (Exception e) {
            //Chequear que tipo de excepcion es para no poner siempre la generica
            System.out.println("No se pudo realizar el borrado del Estudiante");
        }

    }//borrarPreguntado

    public void estudiantesEntreEdades(int edadMin, int edadMax){
        try {
            //repasar algunos select y insert todo y mas de mysql
            String buscarEstudiantesEdades = "SELECT * FROM ESTUDIANTES WHERE EDAD BETWEEN ? AND ?";
            PreparedStatement buscarEstudiantes = conexion.prepareStatement(buscarEstudiantesEdades);
            buscarEstudiantes.setInt(1, edadMin);
            buscarEstudiantes.setInt(2, edadMax);
            //AHora guardamos en result set ya que es un select , y se hace con metodo eecute query
            ResultSet resultadoSelect = buscarEstudiantes.executeQuery();
            //Recorremos para ver el select
            System.out.println("NIF------NOMBRE---EDAD");
            while(resultadoSelect.next()){
                //Unica forma de hacerlo con printf? TENGO QUE VER ORDEN DE TABLAS NO?
                System.out.printf("%d,%s,%s" , resultadoSelect.getInt(1),resultadoSelect.getString(2),
                resultadoSelect.getString(3));
                System.out.println("");
            }
            //Cerramos result set y statement
            resultadoSelect.close();
            buscarEstudiantes.close();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la busqueda de los Estudiantes");
        }
    }//estudiantesEntreEdades

    //REPASAR UN POCO DESDE ARCHIVOS Y DEMAS; DECIDO ACCERLO CON TRY CATCH CON RESOURCES 
    //Y BUFFERED READER LEYENDO LINEAS Y SEPARANDO; MAS SENCILLO PARA MI
    public void insertarDesdeArchivo(String nombreFichero){
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    Estudiantes nuevoEstudiante = new Estudiantes();
                    nuevoEstudiante.setNif(Integer.parseInt(datos[0]));
                    nuevoEstudiante.setNombre(datos[1]);
                    nuevoEstudiante.setEdad(Integer.parseInt(datos[2]));
                    //Llamo al metodo insertar para añadir el estudiante a la base de datos desde el archivo 
                    //uno por uno
                    insertar(nuevoEstudiante);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }//insertarDesdeArchivo

    }

