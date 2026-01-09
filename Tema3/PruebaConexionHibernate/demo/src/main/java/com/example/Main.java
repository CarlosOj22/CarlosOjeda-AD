package com.example;

import config.HibernateUtil;
import repositories.AlumnoRepository;
import repositories.CentroRepository;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import org.hibernate.Transaction;
import org.hibernate.Session;
import entities.Alumno;
import entities.Centro;
import entities.Modulo;

public class Main {
    public static void main(String[] args) {
    
    int opcion=0;
    Scanner sc=new Scanner(System.in);
    Alumno alumnoPrimero=null;
    Centro centro=null;
    Modulo prog;
    Modulo datos;
    Modulo movil;
    ArrayList<Modulo> modulosLista=null;
    CentroRepository centroRepository=null;

    System.out.println("Iniciando sesion a MariaDB");
    Session session = HibernateUtil.get().openSession();

         Socket socket; //AÑADIDO PARA QUE ME CUENTE COMO CAMBIO PARA GIT
    // AQUI incluiremos la lógica del programa
    do {
        System.out.println("---MENU HIBERNATE OPCIONES");
        System.out.println("1.Añadir alumno.");
        System.out.println("2.Añadir Modulos.");
        System.out.println("3.Añadir Centro.");
        System.out.println("4.Añadir modulo a alumno ya matriculado");
        System.out.println("5.Mostrar todos los centros.");
        System.out.println("6.Mostrar centro por ID.");
        System.out.println("7.Borrar Centro.");
        System.out.println("8.Cerrar sesion y salir.");
        opcion=sc.nextInt();
         
        switch (opcion) {
            case 1:
                alumnoPrimero= new Alumno(1,"Carlos","04632611T",centro,modulosLista,24);
                AlumnoRepository alumnoRepository=new AlumnoRepository(session);
                alumnoRepository.save(alumnoPrimero);
                break;
            case 2:
                modulosLista = new ArrayList<Modulo>();
                prog=new Modulo("PROGRAMACION","PR");
                datos=new Modulo("ACCESO A DATOS","AD");
                movil=new Modulo("Prog. dispositivos moviles","PMD");
                modulosLista.add(prog);
                modulosLista.add(datos);
                modulosLista.add(movil);
                break;

            case 3:
                centroRepository = new CentroRepository(session);
                centro = new Centro("IES CENTRO Nº5");
                centroRepository.save(centro);
                break;

            case 4:
                /*UNA VEZ QUE TENGO ALUMMNO MATRICULRLO CON UNO MAS, AÑADIR UNO DESPUES DE CRREARLO
                AL ARRAYLIST DE MODULOS (DENERIA GUARDARSE EN LA BBDD DIRECTO YA QUE ESTA MAPEADO 
                Y PERSISTIDO)*/
                Modulo procesos=new Modulo("Procesos e Hilos","PSP");
                modulosLista.add(procesos);
                alumnoPrimero.setModulos(modulosLista);
                Transaction trx=session.beginTransaction();
                session.flush();
                trx.commit();
                System.out.println("Modulos del alumno actualizados");
                break;

            case 5:
                //Para que muestre datos los siguientes metodos, hay que añadir toString en la clase Centro
                System.out.println(centroRepository.findAll()); 
                break;

            case 6:
                System.out.println(centroRepository.findOneById(1));
                break;

            case 7:
                centroRepository.delete(centro);
                break;

            case 8:
                
                session.close();
                System.out.println("Cerrando sesion a MariaDB");
                break;

            default:
                System.out.println("No has introducido una opcion valida");
                break;
        }


    } while (opcion!=8);
   
    /*
    SE LE PODRIA AÑADIR LOGICA DE INTRODUCE ID PARA BUSCAR; CONTROLAR QUE EXISTA O DIVERSAS EXCEPCIONESSS ETCCC...
    */
    }
}
