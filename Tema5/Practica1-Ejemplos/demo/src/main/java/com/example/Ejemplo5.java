package com.example;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;

public class Ejemplo5 {
    public static void main(String[] args) {
        XQConnection conexion = null;
        try {
            // Establecemos los parámetros para acceder a Exist
            XQDataSource recurso = new ExistXQDataSource();
            recurso.setProperty("serverName", "localhost");
            recurso.setProperty("port", "8080");
            recurso.setProperty("user", "admin");
            recurso.setProperty("password", "");
            conexion = recurso.getConnection();
            if (conexion != null) {
                String consulta = "doc('/db/prueba_collection/universidad.xml')//nombre";
                System.out.print("conexion creada ");
                XQExpression cons = conexion.createExpression();
                XQResultSequence resultado = cons.executeQuery(consulta);
                System.out.println("\n\n*****LISTA DE datos******\n\n");
                // System.out.println(resultado.getSequenceAsString(null)); **Muestra todo
                // seguido**
                while (resultado.next()) {
                    // Mostramos individual con salto de línea
                    System.out.println(resultado.getItemAsString(null) + "\n");
                }
            }
        } catch (XQException e) {
            e.printStackTrace();
        }
    }
}
