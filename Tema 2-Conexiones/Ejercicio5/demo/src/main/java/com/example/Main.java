//Carlos Ojeda Corona-Ejercicio 5

package com.example;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    GestorBaseDatos gbd;
    //Declaros aqui el statement y resulsset para poder usarlos en todos los metodos
    Statement statement;
    ResultSet resultSet;
    String usuario="carlos"; 
    String contraseña="1234";
    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        Scanner cadenas = new Scanner(System.in);
        do {
        System.out.println("Introduce un numero de factura:");
        int idFacturaIntroducida = sc.nextInt();
        if(main.comprobarExistenciaFactura(idFacturaIntroducida)){
            main.mostrarInformacionApuntes(idFacturaIntroducida);
            System.out.println("-----------------------------------");
            System.out.println("Introduce los nuevos datos de un nuevo apunte para introducir");
            //Hago que introduzcamos el id nosotros aunque deberia ser autoincremental
            System.out.println("Introduce id producto");
            int idProdcuto = sc.nextInt();
            System.out.println("Introduce nombre producto");
            String nombreProd = cadenas.nextLine();
            System.out.println("Introduce precio unidad");
            double precioUni=sc.nextDouble();
            System.out.println("Introduce cantidad");
            int cantidad=sc.nextInt();
            System.out.println("Introduce Id de su Factura");
            int numFac=sc.nextInt();
            main.almacenarApunte(idProdcuto,nombreProd,precioUni,cantidad,numFac);
            main.mostrarInformacionApuntes(idFacturaIntroducida);
            main.aplicarDescuentoUnidad(idFacturaIntroducida);
            main.mostrarInformacionApuntes(idFacturaIntroducida);
            main.borrarApuntes(idFacturaIntroducida);
            main.mostrarInformacionApuntes(idFacturaIntroducida);
            main.cerradoDeRecursos();

        }else{
            System.out.println("Factura no existente");
            return;
        }
        } while (true);
    }//main

    public boolean comprobarExistenciaFactura(int idFactura){
        String consulta = "SELECT * FROM FACTURAS WHERE NUMERO="+idFactura;
        gbd = new GestorBaseDatos(usuario, contraseña);
        Connection cone = gbd.devuelveConexion(); 
        try {
            Statement consultaSelect = cone.createStatement();
            ResultSet result = consultaSelect.executeQuery(consulta);
            if(result.next()) return true;
            else return false;
        } catch (Exception e) {
            System.out.println("Problemas al buscar la factura");
            return false;
        }
    }//comprobarExistenciaFactura

    public void mostrarInformacionApuntes(int idFactura){
        //Podriamos enviar factura y de ai sacar su id pero bueno
        String consulta = "SELECT * FROM APUNTES WHERE FACTURA_NUMERO="+idFactura;
        //Aqui usamos el stamement con resulset adaptable
        try {
            Connection cone = gbd.devuelveConexion();
            //Resulset que se puede recorrer hacia delante y atras y es updatable mientras
            statement=cone.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
            //Ejecutamos la consulta
            resultSet=statement.executeQuery(consulta);
            System.out.println("----------DATOS---------");
            printRs(resultSet);
        } catch (SQLException e) {
            System.out.println("Problemas al mostrar la informacion...");
        }
    }//mostrarInformacionApuntes

    public void almacenarApunte(int idProd,String nombreProd, double precioUni, int cantid,int idFac){
        try {
            //Aprovechamos el resulset
            System.out.println("Insertando nuevo apunte...");
            resultSet.moveToInsertRow();
            resultSet.updateInt("ID",idProd);
            resultSet.updateString("PRODUCTO", nombreProd);
            resultSet.updateDouble("PRECIO_UNIDAD",precioUni    );
            resultSet.updateInt("CANTIDAD",cantid);
            resultSet.updateInt("FACTURA_NUMERO",idFac);
            //INSERTAMOS
            resultSet.insertRow();
            System.out.println("------TABLA APUNTES NUEVA------");
            printRs(resultSet);
        } catch (SQLException e) {
           System.out.println("Problemas en la insercion de apuntes");
        }
    }//almacenarApunte


    public void aplicarDescuentoUnidad(int idFactura){
        try {
            //Nos movemos al apunte que queremos modificar
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if(resultSet.getInt("NUMERO_FACTURA")==idFactura){
                    double precioActual = resultSet.getDouble("PRECIO_UNIDAD");
                    int cantidad = resultSet.getInt("CANTIDAD");
                    double nuevoPrecio=precioActual;
                    if(cantidad<3) nuevoPrecio=precioActual - (precioActual * 0.10);
                    if(cantidad>3&&cantidad<6) nuevoPrecio=precioActual - (precioActual * 0.20);
                    if(cantidad>6) nuevoPrecio=precioActual - (precioActual * 0.30);
                    resultSet.updateDouble("PRECIO_UNIDAD", nuevoPrecio);
                    resultSet.updateRow();
                    System.out.println("Descuento aplicado correctamente.");
                    return;
                }
            }
            System.out.println("Apunte no encontrado.");
        } catch (SQLException e) {
            System.out.println("Problemas aplicando el descuento.");
        }
    }//aplicarDescuentoUnidad

    public void borrarApuntes(int idFactura){
        Scanner decisionBorrado=new Scanner(System.in);
        try {
            resultSet.beforeFirst();
            //RECORREMOS RESULSET PARA VER LOS QUE SON DE LA FACTURA QUE LE PASEMOS
            while (resultSet.next()) {
                if(resultSet.getInt("NUMERO_FACTURA")==idFactura){
                   String nombreProducto=resultSet.getString("PRODUCTO");
                   System.out.println("Quieres borrar el producto:" + nombreProducto + " (S/N)");
                   String decision=decisionBorrado.nextLine();
                   if (decision.equalsIgnoreCase("s")) {
                    resultSet.deleteRow();
                   } else {
                    System.out.println("Borrado Rechazado, no has pulsado S");
                   }
                }
                }
        } catch (SQLException e) {
            System.out.println("Problemas al borrar los apuntes de la factura");
        }

    }//borrarApuntes

    public void cerradoDeRecursos(){
        try {
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Problemas cerrando los recursos...");
        }
        gbd.desconectar();
    }//cerradoDeRecursos

    //Este metodo lo copie y pegue
    public static void printRs(ResultSet rs) throws SQLException {
        // NOS ASEGURAMOS DE ESTAR EN LA PRIMERA POSICION
        rs.beforeFirst();
        while (rs.next()) {
            //Retrieve by column name
            int num = rs.getInt("ID");
            String produ = rs.getString("PRODUCTO");
            double pU = rs.getDouble("PRECIO_UNIDAD");
            int can = rs.getInt("CANTIDAD");
            int fN = rs.getInt("FACTURA_NUMERO");
            //Display values
            System.out.print("ID: " + num);
            System.out.print(", PRODUCTO: " + produ);
            System.out.print(", PRECIO_UNIDAD: " + pU);
            System.out.print(", CANTIDAD: " + can);
            System.out.println(" FACTURA_NUMERO: " + fN);
            }
        System.out.println();
        }// FIN printRs()
    }