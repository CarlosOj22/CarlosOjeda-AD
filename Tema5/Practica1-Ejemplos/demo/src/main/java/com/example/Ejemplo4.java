package com.example;

import java.io.File;
import org.exist.xmldb.DatabaseImpl;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

public class Ejemplo4 {
    public static void main(String args[]) throws Exception {
        String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
        String user = "admin";
        String pass = "";
        String collectionPath = "/db/prueba_collection";

        Database database = new DatabaseImpl();
        DatabaseManager.registerDatabase(database);

        Collection col = DatabaseManager.getCollection(uri + collectionPath, user, pass);
        XMLResource res = null;

        try {
            res = (XMLResource) col.createResource("creado.xml", "XMLResource");
            File f = new File("C:\\DAM2\\AD\\Ejercicios\\Tema5\\Practica1-Ejemplos\\demo\\origen.xml");
            if (!f.canRead()) {
                System.out.println("No se puede leer el archivo origen.xml");
                return;
            }
            res.setContent(f);
            System.out.print("Guardando documento " + res.getId() + " en la coleccion conectada");
            col.storeResource(res);
            System.out.println("Creado");
        } finally {
            if (res != null) {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
}

