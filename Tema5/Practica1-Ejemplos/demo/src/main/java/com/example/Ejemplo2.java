package com.example;

import org.exist.xmldb.DatabaseImpl;
import org.xmldb.api.DatabaseManager; // Controlador eXist-db
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

public class Ejemplo2 {
    public class AccesoExistXapi_ejemplo2 {
        public static void main(String[] args) {
            String uri = "xmldb:exist://localhost:8080/exist/xmlrpc"; // Ajusta el URI si es necesario
            String user = "admin"; // Usuario
            String pass = ""; // Contraseña
            String collectionPath = "/db/prueba_collection"; // Ruta de la colección en eXist
            String xquery = "doc('/db/prueba_collection/universidad.xml')//carrera[creditos>170]/nombre"; // Consulta
                                                                                                          // XQuery

            try {
                // Cargar el controlador. Obligatorio para eXist-db aunque tengamos la
                // dependenia en pom.xml
                Database database = new DatabaseImpl();
                DatabaseManager.registerDatabase(database);
                // Obtener la colección
                Collection col = DatabaseManager.getCollection(uri + collectionPath, user, pass); // parecido a
                                                                                                  // DriverManager en
                                                                                                  // JDBC.
                // Obtener el servicio de consulta XPath
                XQueryService xpathService = (XQueryService) col.getService("XQueryService", "1.0");
                // Ejecutar la consulta compilada
                CompiledExpression compiled = xpathService.compile(xquery);
                ResourceSet result = xpathService.execute(compiled);
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
}