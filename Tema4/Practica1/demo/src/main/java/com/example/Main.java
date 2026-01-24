//Carlos Ojeda Corona//2ºDAM//AD//Tema4_BDOR_PRACTICA1

package com.example;

import java.io.File;
import java.util.Scanner;

//Dejo el chip Hibernate atras, ahora uso db4o
/*No hay tablas, no hay SQl, no hibernate ni mapeos. Ahora guardo objetos directamente
en un fichero .db4o*/
//No necesitamos conectores o drivers, solo una libreria db40.jar
//Creo carpeta lib y añado librerias que nos dio profe (añado todas y que coja la necesaria)
//Copio el db40 8.x core que es el importante
//Para añadir el jar como libreria, tenemos que declararlo en el pom
//Ahora ya funcionan imports!! a currale!

import com.db4o.*;
import com.db4o.query.*;;

public class Main {
    public static void main(String[] args) {
        
        //------------VARIABLES----------
        Scanner sc = new Scanner(System.in);
        int opcion=-1;
        Scanner enterosSc= new Scanner(System.in);
        Scanner cadenasSc = new Scanner(System.in);

        //---------COMPRUEBO EXISTENCIA DE FICHERO-----------
        //Veo si existe fichero, si existe lo elimino
        try {
            File fichero = new File("BDEmpleadoHijo.db4o");
            if(fichero.exists()) fichero.delete();
        } catch (Exception e) {
            System.out.println("Fichero No accesible");
        }

        //------------OBJETO OBJECTCONTAINER----------------
        ObjectContainer db = Db4o.openFile("BDEmpleadoHijo.db4o"); //Si no existe la crea
        //La base de datos física es el fichero "BDEmpleadoHijo.db4o" almacenado en la
        //carpeta raíz del proyecto creado   
        
        
        //------------LOGICA PROGRAMA-----------------
        do {
            System.out.println("-------Menú-------\n"+
            "1.INSERTAR DATOS DE EMPLEADOS Y SUS HIJOS\n"+
            "2.VISUALIZAR EMPLEADOS MAYORES DE UNA EDAD\n"+
            "3.INCREMENTAR EDAD DE UN EMPLEADO POR NOMBRE\n"+
            "4.BORRAR EMPLEADOS CON MAS ANTIGUEDAD\n"+
            "5.VISUALIZAR TODOS LOS EMPLEADOS Y SUS HIJOS\n"+
            "0.SALIR");
            System.out.println("Ingrese una Opcion:");
            opcion=sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("--------------------------------------------------");
                    System.out.println("INSERTANDO DATOS DE EMPLEADOS Y SUS HIJOS...");
                     try {
                        insertarDatos(db);
                    } catch (Exception e) {
                        System.out.println("Problemas almacenando en la base de datos...");
                    }
                    System.out.println("--------------------------------------------------");
                    break;

                case 2:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Inserte edad para mostrar empleados con edad superior:");
                    int edad = enterosSc.nextInt();
                    System.out.println("MOSTRANDO EMPLEADOS CON EDAD SUPERIOR A: " + edad);
                    mostrarEdad(db,edad);
                    System.out.println("--------------------------------------------------");
                    break;

                case 3:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Introduce nombre del empleado para incrementar edad: ");
                    String nombreEmpleado=cadenasSc.nextLine();
                    System.out.println("INCREMENTANDO EN UNO LA EDAD DE EMPLEADO: " + nombreEmpleado);
                    incrementarEdadEmpleado(db,nombreEmpleado);
                    System.out.println("--------------------------------------------------");
                    break;

                case 4:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Introduce una antiguedad, se borrara a los empleados "+
                        "con mas antiguedad que la introducidad:");
                    int antiguedad = enterosSc.nextInt();
                    System.out.println("BORRANDO EMPLEADOS...");
                    borrarMasAntiguedad(db,antiguedad);
                    System.out.println("--------------------------------------------------");
                    break;

                case 5:
                    System.out.println("--------------------------------------------------");
                    System.out.println("MOSTRANDO DATOS DE TODOS LOS EMPLEADOS Y SUS HIJOS...");
                    visualizarTodos(db);
                    System.out.println("--------------------------------------------------");
                    break;

                case 0:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Saliendo del programa....");
                    db.close();
                    cadenasSc.close();
                    sc.close();
                    enterosSc.close();
                    System.out.println("--------------------------------------------------");
                    break;
            
                default:
                    System.out.println("Opcion no admitidad...");
                    break;
            }
        } while (opcion!=0);
    }

    //--------------------METODOSSS--------------------

    //---------Opcion 1---------
    static void insertarDatos(ObjectContainer baseDatos){
        //No necesito try catthcs ???
        baseDatos.store(new Empleado("Angel", 5, 53, new Hijo("Gustavo", 7)));
        baseDatos.store(new Empleado("Nieves", 3, 45, new Hijo("Ivan", 3)));
        baseDatos.store(new Empleado("Jesus", 3, 5, new Hijo("Noelia", 3)));
        baseDatos.store(new Empleado("Dolores", 5, 63, new Hijo("Sergio", 7)));
        baseDatos.store(new Empleado("Vicki", 3, 5, null));
        baseDatos.store(new Empleado("Fatima", 5, 63, new Hijo("Lidia", 27)));
        baseDatos.store(new Empleado("Juan Luis", 3, 5, null));
        baseDatos.store(new Empleado("Elena", 1, 42, new Hijo("David", 19)));
        baseDatos.store(new Empleado("Miguel", 20, 45, new Hijo("Paula", 3)));
        baseDatos.store(new Empleado("Jesus", 19, 44, new Hijo("Ruben", 12)));

    }


    //---------Opcion 2---------
    static void mostrarEdad(ObjectContainer baseDatos, int edad){
        Query consulta;
        ObjectSet<Empleado> resultado;
        Empleado e;

        //Consulta
        consulta=baseDatos.query();
        consulta.constrain(Empleado.class);
        consulta.descend("edad").constrain(edad).greater();//Ordenamos edad de mayor a menor
        //hasta la que le pasamos, y cojemos superiores(mayores de edad)
        resultado=consulta.execute();
        while(resultado.hasNext()){
            e=(Empleado)resultado.next();
            e.visDatosEmpleados();
        }
        //No cierro BD aun (PODEMOS SEGUIR HACIENDO OPERACIONES)
    }

    //---------Opcion 3---------
    static void incrementarEdadEmpleado(ObjectContainer baseDatos, String nombreEmpleado){
        Query consulta;
        ObjectSet<Empleado> resultado; //AUNQUE SOLO DEVUELVA 1, se hace con objetset? YA que
        //Sera lo que devuelve el metodo execute no?
        Empleado e=null;

        //Consulta
        consulta=baseDatos.query();
        consulta.constrain(Empleado.class);
        consulta.descend("nombre").constrain(nombreEmpleado);
        resultado=consulta.execute();
        while(resultado.hasNext()){
            e=(Empleado)resultado.next();
        }
        //LO HAGO FUERA NO? SI VARIOS SE LLAMAN IGUAL? NO CUMPLEN AÑOS EL MISMO DIA XD
        e.cumpleAños();
    }

    //---------Opcion 4---------
    static void borrarMasAntiguedad(ObjectContainer baseDatos, int antiguedad){
        Query consulta;
        ObjectSet<Empleado> resultado;
        Empleado e;

        //Consulta
        consulta=baseDatos.query();
        consulta.constrain(Empleado.class);
        consulta.descend("antiguedad").constrain(antiguedad).greater();
        resultado=consulta.execute();
        while(resultado.hasNext()){
            e=(Empleado)resultado.next();
            baseDatos.delete(e);//Borramos de la base de dato ese empleado
            //((SUPONGO QUE SE BORRAN EN CASCADA SUS HIJOS (A NO SER QUE ESTE SU OTRO PADRE
            //O MADRE EN LA EMPRESA PERO LO MAS NORMAL SERIA EN CASCADA)))
        }
    }

    //---------Opcion 5---------
    static void visualizarTodos(ObjectContainer baseDatos){
        Query consulta;
        ObjectSet<Empleado> resultado;
        Empleado e;

        //Consulta
        consulta=baseDatos.query();
        consulta.constrain(Empleado.class);
        resultado=consulta.execute();
        while(resultado.hasNext()){
            e=(Empleado)resultado.next();
            e.visDatosEmpleados();//Este metodo llama a metodo del visualizar info del hijo ya
        }

    }
}