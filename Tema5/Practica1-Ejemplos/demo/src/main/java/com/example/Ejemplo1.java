package com.example;

import org.xmldb.api.base.*;
import org.exist.xmldb.DatabaseImpl; // Controlador eXist-db
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.modules.XPathQueryService;

public class Ejemplo1{
    
    public static void main(String[] args) {
        String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db"; // Ajusta el URI si es necesario
        String user = "admin"; // Usuario
        String pass = ""; // Contraseña
        String collectionPath = "/prueba_collection"; // Ruta de la colección en eXist
        String xquery = "doc('/db/prueba_collection/universidad.xml')//nombre"; // Consulta XQuery

        try {
            // Cargar el controlador. Obligatorio para eXist-db aunque tengamos la
            // dependenia en pom.xml
            Database database = new DatabaseImpl();
            DatabaseManager.registerDatabase(database);
            // Obtener la colección
            Collection col = DatabaseManager.getCollection(uri + collectionPath, user, pass); // parecido a
                                                                                              // DriverManager en JDBC
            // Obtener el servicio de consulta XPath
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            // Ejecutar la consulta
            ResourceSet result = xpathService.query(xquery);
            // Procesar el resultado
            ResourceIterator i = result.getIterator();
            while (i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println(r.getContent());
            }
            // Cerrar la conexión
            col.close();
        } catch (SecurityException | XMLDBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
