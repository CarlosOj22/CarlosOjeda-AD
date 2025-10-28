package com.example;

import java.util.Scanner;

public class UsaConectaEstudiantes {
    public static void main(String[] args) {
        System.out.println("BIENVENIDO AL MENU DE OPERACIONES EN LA BASE DE DATOS COLEGIO!");
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        //DECLARO NUEVO SCANNER PARA LEER NOMBRE YA QUE ME SALTA NOMBRE DIRECTAMENTE AL INTRODUCIR NUEVO
        //ESTUDIANTE, SIEMPRE ME DA PROBLEMAS MEZCLAR NEXTINT Y NEXTLINE
        Scanner scString = new Scanner(System.in);
        ConectaEstudiantes conexionEstudiantes = null;
        //Archivo txt para apartado 6
        String archivoEstudiantes = "estudiantesNuevos.txt";
        do {
            //Obviamente si no te conectas antes el resto de opciones no van a funcionar (Excepcion NullPointerException)
            System.out.println("Pulse una opcion:\n -------------------------------------------- ");
            System.out.println("1.Conectarse a la Base de Datos\n" +
            "2.Insertar un nuevo Estudiante\n" + 
            "3.Actualizar edad de un Estudiante\n" + 
            "4.Borrar un estudiante\n" + 
            "5.Buscar Estudiantes entre dos Edades\n" + 
            "6.Insertar desde un Archivo\n" + 
            "7.Salir y Cerrar conexión\n" + 
            "----------------------------------------------------------------------------------");
            opcion=sc.nextInt();

            switch (opcion) {
                case 1:
                    //Creo la conexion
                    conexionEstudiantes = new ConectaEstudiantes();
                    break;
                
                case 2:
                //VER ODEN DE COLUMNNAS PARA PEDIR LOAS DATOS EN ORDEN
                    Estudiantes ingresado = new Estudiantes();
                    //MIRAR TABLA DNI NO SE PUEDE REPETIR Y ME LOS ESTOY INVENTANDO
                    System.out.println("Introduce el NIF del Estudiante (7 DIGITOS):");
                    int nif=sc.nextInt();
                    ingresado.setNif(nif);
                    System.out.println("Introduce el Nombre del Estudiante:");
                    String nombre = scString.nextLine();
                    ingresado.setNombre(nombre);
                    System.out.println("Introduce la Edad del Estudiante:");
                    int edad = sc.nextInt();
                    ingresado.setEdad(edad);
                    conexionEstudiantes.insertar(ingresado);
                    break;
                
                case 3:
                    System.out.println("Introduce el NIF del Estudiante al que quieres actualizar la edad:");
                    int nifActualizar = sc.nextInt();
                    System.out.println("Introduce la nueva edad:");
                    int nuevaEdad = sc.nextInt();
                    conexionEstudiantes.actualizarEdad(nifActualizar, nuevaEdad);
                    break;
                
                case 4:
                    System.out.println("Introduce el NIF del Estudiante que quieres borrar:");
                    int nifBorrar = sc.nextInt();
                    conexionEstudiantes.borrarPreguntado(nifBorrar);
                    break;

                case 5:
                    System.out.println("Introduce la edad minima a buscar:");
                    int edadMin = sc.nextInt();
                    System.out.println("Introduce la edad maxima a buscar:");
                    int edadMax = sc.nextInt();
                    conexionEstudiantes.estudiantesEntreEdades(edadMin, edadMax);
                    break;

                case 6:
                    try {
                        conexionEstudiantes.insertarDesdeArchivo(archivoEstudiantes);
                    } catch (Exception e) {
                        System.out.println("Problemas en la inserccion de estudiantes desde archivo.");
                    }
                    break;

                case 7:
                    conexionEstudiantes.desconectar();
                    break;

                default:
                System.out.println("No has introducido una opción correcta");
                //Deberia un try en el metodo dentro de bufered reader y demas y que trhows excepcion
                // aqui para ver si sale bien o mal?
                    break;
            }
        } while (opcion!=7);
    }
}