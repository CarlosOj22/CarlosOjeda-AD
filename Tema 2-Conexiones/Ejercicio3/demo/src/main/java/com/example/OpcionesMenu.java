package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//NO TENGO QUE HACER LA CLASE STATICA SI METO METODOS STATICOS? CREIA RECORDAR QUE SI PERO ME DABA ERROR
public class OpcionesMenu {

    public static void mostrarMenu() {
        System.out.println("--- Menú de Opciones ---"); 
        System.out.println("1. Mostrar la información de la tabla pasajeros");
        System.out.println("2. Listado de pasajeros por vuelo");
        System.out.println("3. Insertar vuelo nuevo");
        System.out.println("4. Borrar vuelo");
        System.out.println("5. Modificar plazas de fumadores de un vuelo");
        System.out.println("6. Salir del programa");
    }


    public static void mostrarInformacionPasajeros(Connection cone) {
        //TODA LA INFORMACION DE PASAJEROS
        String consulta = "SELECT * FROM PASAJEROS";
        //AQUI NO HACE FALTA PREPARESTATEMENT YA QUE ES UN SELECT DE TODO Y YA
        try {
            Statement consultaDefinitiva = cone.createStatement();
            ResultSet resultadoConsulta = consultaDefinitiva.executeQuery(consulta);
            //Visualizamos
            //PARA ESTE SOUT HE TENIDO QUE MIRAR TABLA
            System.out.println("NUM------COD.VUELO-----TIPO.PLAZA-----FUMADOR");
            //DA IGUAL QUE EN PASAJERO VUELO SEA OBJETO (DE AI SE SACA CODIGO), YA QUE
            //ESTO HACE UNA SENTENCIA SQL SINMAS UN SELECT
            while(resultadoConsulta.next()){
                //Hay que saber datos de la tabla y orden
                System.out.printf("%d\t %s\t %s\t\t %s\t", resultadoConsulta.getInt(1),
                resultadoConsulta.getString(2), resultadoConsulta.getString(3),
                resultadoConsulta.getString(4));
                System.out.println("");
            }
            System.out.println("-----------------------");
        } catch (Exception e) {
            System.out.println("Problemas al hacer la consulta de MySql");
        }
      
    }

    //LE PASO CODIGO DE VUELO PARA VER PASAJEROS ((LE TENGO QUE PASAR CONE SIEMPRE))
    //YA QUE EN ESTA CLASE NO TENGO NINGUN OBJETO TIPO CONEXION
    public static void listadoPasajerosPorVuelo(Connection cone,String codVuelo) {
        String consulta = "SELECT * FROM PASAJEROS WHERE COD_VUELO=?";
        //Lo hacemos con Prepared o Normal? Sepuede ambas no? LO voy a hacer con prepare
        try {
                PreparedStatement consultaPasajeros = cone.prepareStatement(consulta);
                consultaPasajeros.setString(1, codVuelo);
                //Resulset para sacar datos
                ResultSet resultado = consultaPasajeros.executeQuery();
                System.out.println("-----------------------");
                while(resultado.next()){
                    System.out.printf("%d , %s , %s , %s", resultado.getInt(1),
                    resultado.getString(2),resultado.getString(3),
                    resultado.getString(4));
                    System.out.println("");
                }
        } catch (Exception e) {
            System.out.println("Problemas en consulta de pasajeros por vuelo.");
        }
    }

    //PASO LOS DATOS Y LOS PIDO DESDE EL MAIN (CON DATOS DE VUELO CREO UNO NUEVO
    //LLAMANDO A EL CONSTRUCTOR EN EL MAIN Y PASO EL NUEVO VUELO
    public static void insertarVueloNuevo(Connection cone, Vuelos vueloNuevo) {
       String inserccion = "INSERT INTO PASAJEROS VALUES(?,?,?,?,?,?,?,?)";
       try {
            PreparedStatement insertarVuelo = cone.prepareStatement(inserccion);
            insertarVuelo.setString(1, vueloNuevo.getCodVuelo());
            insertarVuelo.setString(2, vueloNuevo.getHoraSalida());
            insertarVuelo.setString(3, vueloNuevo.getDestino());
            insertarVuelo.setString(4, vueloNuevo.getProcedencia());
            insertarVuelo.setInt(5, vueloNuevo.getPlazasFumador());
            insertarVuelo.setInt(6, vueloNuevo.getPlazasNoFumador());
            insertarVuelo.setInt(7, vueloNuevo.getPlazasTurista());
            insertarVuelo.setInt(8, vueloNuevo.getPlazasPrimera());
            //EJECUTO
            int filasInsertadas = insertarVuelo.executeUpdate();
            if(filasInsertadas > 0){
                System.out.println("Vuelo insertado correctamente. Filas insertadas: " + filasInsertadas);
            } else {
                System.out.println("No se ha podido insertar el vuelo.");
            }
       } catch (Exception e) {
            System.out.println("Problemas al insertar el nuevo vuelo.");
       }
    }

    //PIDO CODIGO VUELO A BORRAR
    public static void borrarVuelo(Connection cone, String codVuelo) {
        //Primero chequear si existe el vuelo
        String consultaComprobar = "SELECT * FROM VUELOS WHERE COD_VUELO=?";
        String borrado = "DELETE FROM VUELOS WHERE COD_VUELO=?";
        try {
            //con un solo stateent puedo ver si existe select y borrarlo? creo qu no
            PreparedStatement consultaExista = cone.prepareStatement(consultaComprobar);
            PreparedStatement consultaBorrado = cone.prepareStatement(borrado);
            consultaExista.setString(1,codVuelo);
            ResultSet resultado = consultaExista.executeQuery();
             //Si existe lo borro
            if(resultado.next()){   
                consultaBorrado.setString(1, codVuelo);
                int filasBorradas = consultaBorrado.executeUpdate();
                if(filasBorradas > 0){
                    System.out.println("Vuelo borrado correctamente. Filas borradas: " + filasBorradas);
                } else {
                    System.out.println("No se ha podido borrar el vuelo.");
                }
            } else {
                System.out.println("El vuelo con codigo " + codVuelo + " no existe.");
            }
        } catch (Exception e) {
            System.out.println("Problemas con el borrado del vuelo.");
        }
    }

    //AQUI FIJO A LA CUARTA PARTE DE PLAZAS FUMADORES A NOFUMADORES SOLO?
    //FLOW UN SET UPDATE?   UPDATE PLAZAS TAL SET NOFUMADORES = FUMADORES/4
    public static void modificarPlazasFumadores(Connection cone ,String codVuelo, int plazasNoFumadores) {
        PreparedStatement modificarPlazas;
        PreparedStatement consultaVuelo;
        String modificacion = "UPDATE VUELOS SET PLAZAS_NO_FUMADOR=?";
        String consultaVueloExiste= "SELECT * FROM VUELOS WHERE COD_VUELO=?";
        try {
            consultaVuelo=cone.prepareStatement(consultaVueloExiste);
            consultaVuelo.setString(1, codVuelo);
            ResultSet resultado= consultaVuelo.executeQuery();
            if(!resultado.next()){
                System.out.println("El vuelo con codigo "+ codVuelo +" no existe. No se pueden modificar las plazas.");
                return;
            }
            modificarPlazas=cone.prepareStatement(modificacion);
            modificarPlazas.setInt(1, (plazasNoFumadores/4));
            System.out.println("Cambios en las plazas de fumadores realizados correctamente.");
        } catch (Exception e) {
            System.out.println("Problemas en la modificacion de Plazas de Fumadores");
        }
    }
}   
