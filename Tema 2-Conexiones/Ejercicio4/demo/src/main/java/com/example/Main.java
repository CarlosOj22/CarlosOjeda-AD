//Carlos Ojeda Corona-Ejercicio 4
package com.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public class Main {
    GestorBaseDatos gbd; 
    public static void main(String[] args) {
        
        //Creamos objeto de tipo main para poder llamar a metodos no estaticos desde el main
        Main main = new Main();
        //HACER QUE CONSTRUCTOR PARA QUE PONER ID DE PRODUCTO SEPA SUS DATOS YA? UNA ARRAYLIST
        //DE PRODUCTOS O ALGO ASI?
        
        //--Factura 1--
        Facturas factura1 = new Facturas(1001, "Juan Perez");
        Apuntes producto1Juan = new Apuntes(1, "Laptop", 750.25, 2, factura1.getNumeroFactura());
        Apuntes producto2Juan = new Apuntes(2, "Mouse", 25.10, 1, factura1.getNumeroFactura()); 
        Apuntes producto3Juan = new Apuntes(3, "Teclado", 45.30, 1, factura1.getNumeroFactura());
        Apuntes producto4Juan = new Apuntes(4, "Monitor", 200.50, 2, factura1.getNumeroFactura());
        Apuntes[] apuntesJuan = {producto1Juan, producto2Juan, producto3Juan, producto4Juan};
        factura1.setApuntes(apuntesJuan);

        //Mostrar datos factura 1
        System.out.println("Factura N°: " + factura1.getNumeroFactura());
        System.out.println("Cliente: " + factura1.getNombreCliente());
        System.out.println("Productos:");
        System.out.println("- " + producto1Juan.getProducto() + " | Precio Unidad: $" + producto1Juan.getPrecioUnidad() + " | Cantidad: " + producto1Juan.getCantidad());   
        System.out.println("- " + producto2Juan.getProducto() + " | Precio Unidad: $" + producto2Juan.getPrecioUnidad() + " | Cantidad: " + producto2Juan.getCantidad());   
        System.out.println("- " + producto3Juan.getProducto() + " | Precio Unidad: $" + producto3Juan.getPrecioUnidad() + " | Cantidad: " + producto3Juan.getCantidad());   
        System.out.println("- " + producto4Juan.getProducto() + " | Precio Unidad: $" + producto4Juan.getPrecioUnidad() + " | Cantidad: " + producto4Juan.getCantidad());   


        //Si se repite un producto su id sera el mismo no? en plan lapton id 1 y lo pueden comprar
        //Varias personas
        //--Factura 2--
        Facturas factura2 = new Facturas(1002, "Maria Gomez");
        Apuntes producto1Maria = new Apuntes(5, "Tablet", 300.00, 1, factura2.getNumeroFactura());
        Apuntes producto2Maria = new Apuntes(6, "Auriculares", 80.10, 1, factura2.getNumeroFactura());
        Apuntes producto3Maria = new Apuntes(7, "Smartphone", 600.65, 1, factura2.getNumeroFactura());
        Apuntes producto4Maria = new Apuntes(8, "Cargador", 20.00, 2, factura2.getNumeroFactura());
        Apuntes[] apuntesMaria = {producto1Maria, producto2Maria, producto3Maria, producto4Maria};
        factura2.setApuntes(apuntesMaria);

        //--Mostrar datos factura 2
        System.out.println("\nFactura N°: " + factura2.getNumeroFactura());
        System.out.println("Cliente: " + factura2.getNombreCliente());
        System.out.println("Productos:");
        System.out.println("- " + producto1Maria.getProducto() + " | Precio Unidad: $" + producto1Maria.getPrecioUnidad() + " | Cantidad: " + producto1Maria.getCantidad());   
        System.out.println("- " + producto2Maria.getProducto() + " | Precio Unidad: $" + producto2Maria.getPrecioUnidad() + " | Cantidad: " + producto2Maria.getCantidad());       
        System.out.println("- " + producto3Maria.getProducto() + " | Precio Unidad: $" + producto3Maria.getPrecioUnidad() + " | Cantidad: " + producto3Maria.getCantidad());   
        System.out.println("- " + producto4Maria.getProducto() + " | Precio Unidad: $" + producto4Maria.getPrecioUnidad() + " | Cantidad: " + producto4Maria.getCantidad());   

        //Almacenamos las dos facturas y sus datos
        main.almacenarDatos(factura1);
        main.almacenarDatos(factura2);
        //Borramos la factura 2
        main.llamarProcedimientoBorrarFactura(factura2);
        //Calculamos el precio Total de factura1
        main.llamarFuncionTotalFactura(factura1);
        
    }

    //No hace falta crear en facturas array de apuntes, ya que en la base de datos se relacionaran por que
    //Cada apunte tiene el id de la factura a la que pertenece

    //Para que lo haga perfecto las tablas tienes que estar vaciadas al ejecutar este metodo
    //(borrado sus datos con commit, si por ejemplo seejecuta dos veces sin borrar datos dara error de PK duplicada)
    public void almacenarDatos(Facturas factura) {
        //En cada metodo conecto y cierro conexion al final
        gbd = new GestorBaseDatos("carlos", "1234");
        //Metodo para almacenar factura en la base de datos
        Connection  conexion = gbd.devuelveConexion();  
        String insertarFactura = "INSERT INTO FACTURAS VALUES (?,?)";
        String insertarApuntes = "INSERT INTO APUNTES VALUES (?,?,?,?,?)";
        try {
            PreparedStatement insertFacSta = conexion.prepareStatement(insertarFactura);
            PreparedStatement insertApuntsSta = conexion.prepareStatement(insertarApuntes);
            insertFacSta.setInt(1, factura.getNumeroFactura());
            insertFacSta.setString(2, factura.getNombreCliente());
            int filasFac = insertFacSta.executeUpdate();
            System.out.println("Filas afectadas en tabla Facturas: " + filasFac);
            insertFacSta.close();
            for(int i=0;i<factura.getApuntes().length;i++){ //Recorremos el array de apuntes de la facutra
                //Para ir guandando tods sus apuntes en la tabla apuntes
                insertApuntsSta.setInt(1, factura.apuntes[i].getIdProducto());   
                insertApuntsSta.setString(2, factura.apuntes[i].getProducto());   
                insertApuntsSta.setDouble(3, factura.apuntes[i].getPrecioUnidad());   
                insertApuntsSta.setInt(4, factura.apuntes[i].getCantidad());   
                insertApuntsSta.setInt(5, factura.apuntes[i].getIdFactura());
                int filasApunt = insertApuntsSta.executeUpdate();   
                System.out.println("Filas afectadas en tabla Apuntes: "+ filasApunt);
            }

            insertApuntsSta.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al almacenar la factura N° "+factura.getNumeroFactura());
        }
        //Desconectamos
        gbd.desconectar();
    }//almacenarDatos

    public void llamarProcedimientoBorrarFactura(Facturas factura){
        gbd = new GestorBaseDatos("carlos", "1234");
        Connection cone = gbd.devuelveConexion();
        try {
            //Llamamos al procedimiento almacenado
            CallableStatement cst = cone.prepareCall("{call borra_factura("+factura.getNumeroFactura()+")}");
            //Ejecutamos el procedimiento almacenado
            cst.execute();
            System.out.println("Factura N° "+factura.getNumeroFactura()+" borrada correctamente.");
            cst.close();
        } catch (SQLException e) {
            System.out.println("Error al borrar la factura N° "+factura.getNumeroFactura());
        }
        gbd.desconectar();
    }//llamarProcedimientoBorrarFactura

    public void llamarFuncionTotalFactura(Facturas factura){
        gbd = new GestorBaseDatos("carlos", "1234");
        Connection cone = gbd.devuelveConexion();
        try {
            //LLamamos al procedimiento almacenado
            CallableStatement cst = cone.prepareCall("{? = call calcula_total(?)}");
            //La funcion devuelve un double
            cst.registerOutParameter(1,Types.DOUBLE);
            //Suponiendo que la funcion recibe un entero (id de factura)
            cst.setInt(2,factura.getNumeroFactura());
            cst.execute();
            //Obtenemos el valor del return
            double total=cst.getDouble(1);
            System.out.println("El total de la factura"+factura.getNumeroFactura()+" es: " + total);
        } catch (SQLException e) {
            System.out.println("Error al calcular el total de la factura N° "+factura.getNumeroFactura());
        }
        gbd.desconectar();
    }//llamarFuncionTotalFactura
}