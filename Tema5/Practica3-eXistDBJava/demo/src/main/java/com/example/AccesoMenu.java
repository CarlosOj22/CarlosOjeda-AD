package com.example;

import java.util.Scanner;

import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;


public class AccesoMenu {
    private Collection coleccion = null;
    private String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private String user = "admin";
    private String pass = "";
    private String coleccionRuta = "/db/ejercicios";
    XMLResource recurso = null;
    
    public AccesoMenu() {
        //Constructor que se conecta
        //conectar();
    }
    //METODO PARA CONECTARSE A LA BASE DE DATOS Y OBTENER EL XML
    public void conectar() {
        try {
            coleccion=DatabaseManager.getCollection(uri + coleccionRuta, user, pass);
            recurso = (XMLResource)coleccion.getResource("menus.xml");
            System.out.println(recurso.getContent());
            
            if (coleccion == null) {
                System.out.println("No se pudo conectar a la coleccion");
            } else {
                System.out.println("Conectado a eXist-db correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DABA ERRORES LA CONEXION SENCILLA HE USADO 
    //ESTE METODO PARA CONECTARME A LA BASE DE DATOS Y OBTENER EL XML
    //QUE HE ENCONTRADO EN INTERNET Y FUNCIONA CORRECTAMENTE
    public void conectar2() {
    try {
        System.out.println("Conectando a eXist-db...");
        
        // Registrar el driver (FORMA CORRECTA para eXist-db 6.x)
        String driver = "org.exist.xmldb.DatabaseImpl";
        Class<?> cl = Class.forName(driver);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        
        // Intentar conectar
        String url = uri + coleccionRuta; // "xmldb:exist://localhost:8080/exist/xmlrpc/db/ejercicios"
        System.out.println("URL: " + url);
        
        coleccion = DatabaseManager.getCollection(url, user, pass);
        
        if (coleccion == null) {
            System.out.println("ERROR: No se pudo conectar. Comprueba:");
        } else {
            System.out.println("Conectado correctamente a: " + coleccion.getName());
            
            // Mostrar recursos
            String[] recursos = coleccion.listResources();
            System.out.println("Recursos encontrados: " + recursos.length);
            for (String r : recursos) {
                System.out.println("  - " + r);
            }
        }
        
    } catch (ClassNotFoundException e) {
        System.out.println("No se encontró la clase DatabaseImpl");
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("Error:" + e.getMessage());
        e.printStackTrace();
    }
}
    
    //DESCONEXIÓN DE LA BASE DE DATOS
    public void desconectar() {
        try {
            if (coleccion != null) {
                coleccion.close();
                System.out.println("Desconectado de eXist-db");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //METODO PARA MOSTRAR MENUS
    public void mostrarMenus() {
        try {
            if (coleccion == null) {
                System.out.println("No hay conexión con la base de datos.");
                return;
            }
            
            String xquery = "collection('" + coleccionRuta + "')//menu";
            
            XPathQueryService service = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query(xquery);
            
            ResourceIterator i = result.getIterator();
            System.out.println("\n--- MENÚS ---");
            while(i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println(r.getContent().toString());
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }
    
    //MEOTOD PARA INSERTAR MENU
    public void insertMenu() {
        try {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("--- INSERTAR NUEVO MENÚ ---");
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Primer plato: ");
            String primerPlato = sc.nextLine();
            System.out.print("Segundo plato: ");
            String segundoPlato = sc.nextLine();
            System.out.print("Postre: ");
            String postre = sc.nextLine();
            System.out.print("Precio: ");
            String precio = sc.nextLine();
            
            String xquery = "update insert " +
                "<menu>" +
                "<nombre>" + nombre + "</nombre>" +
                "<primerPlato>" + primerPlato + "</primerPlato>" +
                "<segundoPlato>" + segundoPlato + "</segundoPlato>" +
                "<postre>" + postre + "</postre>" +
                "<precio>" + precio + "</precio>" +
                "</menu> " +
                "into collection('" + coleccionRuta + "')/restaurante";
            
                XUpdateQueryService service = (XUpdateQueryService) coleccion.getService("XUpdateQueryService", "1.0");
                service.update(xquery);
            
                System.out.println("Menú insertado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //METODO BUSCAR MENU POR NOMBRE
    public void buscarMenu(String nombre) {
        try {
            String xquery = "collection('" + coleccionRuta + "')//menu[nombre='" + nombre + "']";
            
            XPathQueryService service = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query(xquery);
            
            ResourceIterator i = result.getIterator();
            System.out.println("\n--- INFORMACION DEL MENU: " + nombre + " ---");
            if(i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println(r.getContent().toString());
            } else {
                System.out.println("No se encontró el menu: " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //METODO BORRAR MENU POR NOMBRE
    public void borrarMenu(String nombre) {
        try {
            String xquery = "update delete collection('" + coleccionRuta + "')//menu[nombre='" + nombre + "']";
            
            XUpdateQueryService service = (XUpdateQueryService) coleccion.getService("XUpdateQueryService", "1.0");
            service.update(xquery);
            
            System.out.println("Menu '" + nombre + "' borrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //METODO ACTUALIZAR PRECIO DE LOS MENUS
    public void actualizaPrecio(int incremento) {
        try {
            double porcentaje = incremento / 100.0;
            
            String xquery = "for $p in collection('" + coleccionRuta + "')//precio " +
                           "return update value $p " +
                           "with round-half-to-even(xs:decimal($p) * (1 + " + porcentaje + "), 2)";
            
            XUpdateQueryService service = (XUpdateQueryService) coleccion.getService("XUpdateQueryService", "1.0");
            service.update(xquery);
            
            System.out.println("Precios incrementandos con: " + incremento + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //METODO PARA BUSCAR MENUS MAS BARATOS QUE UN PRECIO DADO
    public void buscarMasBaratoQue(int precio) {
        try {
            String xquery = "for $m in collection('" + coleccionRuta + "')//menu[xs:decimal(precio) < " + precio + "] " +
                           "order by xs:decimal($m/precio) ascending " +
                           "return <menu>{" +
                           " $m/nombre," +
                           " $m/precio" +
                           "}</menu>";
            
            XPathQueryService service = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query(xquery);
            
            ResourceIterator i = result.getIterator();
            System.out.println("\n---- MENUS MÁS BARATOS QUE " + precio + "€ ---");
            while(i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println(r.getContent().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}