package com.example;

import org.xmldb.api.base.*;
import org.exist.xmldb.DatabaseImpl;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.modules.XQueryService;

public class Ejemplo3 {
    public static void main(String[] args) {
        String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
        String user = "admin";
        String pass = "";
        String collectionPath = "/db/prueba_collection";

        try {
            Database database = new DatabaseImpl();
            DatabaseManager.registerDatabase(database);
            Collection col = DatabaseManager.getCollection(uri + collectionPath, user, pass);
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");

            String xquery =
                "update insert\n" +
                "  <alumno id=\"e05\">\n" +
                "    <apellido1>López</apellido1>\n" +
                "    <apellido2>Martínez</apellido2>\n" +
                "    <nombre>Carlos</nombre>\n" +
                "    <sexo>Hombre</sexo>\n" +
                "    <estudios>\n" +
                "      <carrera codigo=\"c01\"/>\n" +
                "      <asignaturas>\n" +
                "        <asignatura codigo=\"a01\"/>\n" +
                "        <asignatura codigo=\"a02\"/>\n" +
                "      </asignaturas>\n" +
                "    </estudios>\n" +
                "  </alumno>\n" +
                "into doc(\"universidad.xml\")/universidad/alumnos";

            CompiledExpression compiled = service.compile(xquery);
            service.execute(compiled);

            System.out.println("Alumno insertado correctamente");
            col.close();
        } catch (SecurityException | XMLDBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
