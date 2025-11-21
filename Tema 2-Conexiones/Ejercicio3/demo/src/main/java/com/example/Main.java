package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Si tengo que crear aqui objeto gestorbaseatos y cone les tengo que pasar eso a opciones menu
        //Para no crearlo alli dos veces
        Scanner sc = new Scanner(System.in);
        //Scanner para strings que mezclar siempre me da problemas incluso haciendo flush
        Scanner cadenas = new Scanner(System.in);
        GestorBaseDatos gestorBases = new GestorBaseDatos("AEROPUERTO", 
        "root", "root") ;
        int opcion=0;
        //Uso metodo que devuelve conexion de gestor base para pasarlas a los metodos
        //de opciones menu
        do {
            OpcionesMenu.mostrarMenu();
            opcion=sc.nextInt();
            switch (opcion) {
                case 1:
                //LE PASAMOS LA CONEXION
                    OpcionesMenu.mostrarInformacionPasajeros(gestorBases.devuelveConexion());
                    break;

                case 2:
                    String codigo;
                    System.out.println("Introduce el codigo de vuelo que deseas Buscar su listado de Pasajeros");
                    codigo = cadenas.nextLine();
                    OpcionesMenu.listadoPasajerosPorVuelo(gestorBases.devuelveConexion(), codigo);
                    break;

                case 3:
                //Datos del vuelo:
                String codigoVuelo;
                String horaSalida;
                String destino;
                String procedencia;
                int plazasFumador;
                int plazasNoFumador;
                int plazasTurista;
                int plazasPrimera;
                //Primero creo objeto de tipo vuelo y luego lo paso
                //Chequear control de Strings Int y demas aora meto algo de codigo para eso
                System.out.println("----Has elegido introducir un vuelo nuevo----");
                System.out.println("Introduce codigo de vuelo:");
                codigoVuelo = cadenas.nextLine();
                System.out.println("Introduce la hora de salida:");
                horaSalida = cadenas.nextLine();
                System.out.println("Introduce el destino:");
                destino = cadenas.nextLine();
                System.out.println("Introduce procedencia del vuelo:");
                procedencia = cadenas.nextLine();
                System.out.println("Introduce numero de Plazas Fumadores:");
                plazasFumador = sc.nextInt();
                System.out.println("Introduce numero de Plazas No Fumadores:");
                plazasNoFumador = sc.nextInt();
                System.out.println("Introduce numero de plazas Turistas:");
                plazasTurista = sc.nextInt();
                System.out.println("Introduce numero de plazas de Primera:");
                plazasPrimera = sc.nextInt();

                Vuelos vueloInsertar = new Vuelos(codigoVuelo, horaSalida, destino, procedencia, 
                plazasFumador, plazasNoFumador, plazasTurista, plazasPrimera);
                    System.out.println("");
                    OpcionesMenu.insertarVueloNuevo(gestorBases.devuelveConexion(),vueloInsertar);
                    break;

                case 4:
                //Chequear si existe el vuelo antes de borrar
                    String codigoVueloBorrar;
                    System.out.println("Inserte codigo de vuelo a borrar");
                    codigoVueloBorrar = cadenas.nextLine();
                    OpcionesMenu.borrarVuelo(gestorBases.devuelveConexion(), codigoVueloBorrar);
                    break;

                case 5:
                    int plazasNoFumadoresModificar;
                    System.out.println("Inserte codigo de vuelo que quiere modificar plazas");
                    String codVueloModPlazas=cadenas.nextLine();
                    System.out.println("Inserte numero de plazas totales");
                    plazasNoFumadoresModificar = sc.nextInt();
                    System.out.println("(Las plazas de no fumadores seran 1/4 partes de las plazas de no fumadores)");
                    OpcionesMenu.modificarPlazasFumadores(gestorBases.devuelveConexion(), codVueloModPlazas, plazasNoFumadoresModificar);
                    break;

                case 6:
                    System.out.println("----Saliendo del programa----");
                    gestorBases.desconectar();
                    break;
            
                default:
                System.out.println("Opcion introducida no valida...");
                    break;
            }
        } while (opcion!=6);
        sc.close();
        cadenas.close();
    }
}