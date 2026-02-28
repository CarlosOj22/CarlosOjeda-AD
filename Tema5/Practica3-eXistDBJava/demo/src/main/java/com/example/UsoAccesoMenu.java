package com.example;

import java.util.Scanner;

public class UsoAccesoMenu {
    public static void main(String[] args) {
        AccesoMenu acceso = new AccesoMenu();
        Scanner cadenas = new Scanner(System.in);
        Scanner opc = new Scanner(System.in);
        int opcion;
        
        //Conecatamos a exist-db al iniciar el programa
        acceso.conectar2();
        
        do {
            mostrarMenu();
            System.out.print("Elige una opcion: ");
            opcion = opc.nextInt();
            
            switch(opcion) {
                case 1:
                    acceso.mostrarMenus();
                    break;
                    
                case 2:
                    acceso.insertMenu();
                    break;
                    
                case 3:
                    System.out.print("Nombre del menu a buscar: ");
                    String nombreBuscar = cadenas.nextLine();
                    acceso.buscarMenu(nombreBuscar);
                    break;
                    
                case 4:
                    System.out.print("Nombre del menu a borrar: ");
                    String nombreBorrar = cadenas.nextLine();
                    acceso.borrarMenu(nombreBorrar);
                    break;
                    
                case 5:
                    System.out.print("Porcentaje de incremento: ");
                    int incremento = cadenas.nextInt();
                    acceso.actualizaPrecio(incremento);
                    break;
                    
                case 6:
                    System.out.print("Precio maximo: ");
                    int precioMax = cadenas.nextInt();
                    acceso.buscarMasBaratoQue(precioMax);
                    break;
                    
                case 7:
                    System.out.println("Saliendo...");
                    break;
                    
                default:
                    System.out.println("Opción no valida");
            }
            
        } while(opcion != 7);
        
        //Al acabar el programa desconectamos de exist-db
        acceso.desconectar();
        cadenas.close();
    }
    
    public static void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL---");
        System.out.println("1.Mostrar todos los menus");
        System.out.println("2.Insertar nuevo menu");
        System.out.println("3.Buscar menu por nombre");
        System.out.println("4.Borrar menu por nombre");
        System.out.println("5.Actualizar precios");
        System.out.println("6.Buscar menus más baratos que...");
        System.out.println("7.Salir");
    }
}

