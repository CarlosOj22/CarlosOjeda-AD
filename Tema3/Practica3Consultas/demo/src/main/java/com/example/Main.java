package com.example;

import config.HibernateUtil;
import entities.*;
import java.util.List;
import org.hibernate.*;

//DADO QUE LA PRACTICA 1 ALUMNO Y DEMAS ES EN MYSQL, AQUI TAMBIEN USARE MYSQL YA QUE ESTA 
//AHI SU BASE DE DATOS
//COPIO Y PEGO TODO EL EJERCICIO 1 PARA CAMBIAR LO QUE PIDE EL PROFESOR 
public class Main {
    public static void main(String[] args) {

        try {
            System.out.println("Iniciando sesion a MariaDB");
            Session session = HibernateUtil.get().openSession();
            System.out.println("---------CONSULTAS----------");
            //ejecutarConsulta1HQL(session);
            //ejecutarConsulta2HQL(session);
            //ejecutarConsulta3HQL(session);
            //ejecutarConsulta4HQL(session);
            //ejecutarConsulta5HQL(session);
            ejecutarConsulta6HQL(session);
            session.close();
        }catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
        }finally {
        //HibernateUtil.shutdown(); //Esta linea da error
        System.out.println("Aplicación finalizada");
        }
    }

    //-------------------------------------------------------------------------------------//
    //CONSULTA 1:
    /*Listado de los alumnos de l centro “CIFP CUENCA”, que tienen más de 2 módulos matriculados 
    -La consulta selecciona objetos “Alumno” completos 
    -Se puede usar el método size de la propiedad “modulos” en la consulta SELECT*/

    private static void ejecutarConsulta1HQL(Session session){
        //Consulta listado de los alumnos de el centro CIFP CUENCA 
        //que tienen más de 2 módulos matriculados
        //CUIDAD CON EL NOMBRE DE LAS CLASES Y DEMAS TOENE QUE SER EL MISMO
        //DABA ERROR POR QUE NOMBRE DE FROM DEBE COINCIDIR NO CON CLASE; SI NO CON EL QUE PONE
        //EN ENTITY NAMEE"!!
        String cadenasql = "SELECT a FROM alumnos a WHERE a.centro.nombre='CIFP CUENCA' AND size(a.modulos)>2";
        //Query deprecated actualmente
        Query consulta = session.createQuery(cadenasql);
        List<Alumno> alumnosConsulta = consulta.list();
        for(Alumno alumno: alumnosConsulta){
            System.out.println(alumno.toString());
            System.out.println("-------------------");
        }

    }


    //-------------------------------------------------------------------------------------//
    //CONSULTA 2
    /*Datos de los centros y el número total de alumnos matriculados en cada uno 
    -La consulta selecciona datos sueltos y no objetos completos 
    -Se utilizará JOIN para contar los alumnos matriculados en cada centro (GROUP BY) */

    private static void ejecutarConsulta2HQL(Session session){
        //JOIN S HACE DESDE ALUMNOS YA QUE CLASE ALUMNOS TIENE EL OBJETO CENTRO PERO CENRO NO
        //TIENE NADA DE ALUMNOS
        String cadenaSql="SELECT c.nombre, COUNT(a) FROM alumnos a JOIN a.centro c GROUP BY c.nombre";
        Query consulta= session.createQuery(cadenaSql);
        //NO DEVUELVE OBJETOS ALUMNOS; SINO OBJETOS OBJECT CON DATOS DE 2 COLUMNAS
        List<Object[]> resultado = consulta.list();
        System.out.println("Centro\t Num.Alumnos");
        for(Object[] datos:resultado){
            System.out.println(datos[0] + " : " + datos[1]);
            System.out.println("-----------------");
        }
    }


    //-------------------------------------------------------------------------------------//
    //CONSULTA 3
    /*Muestra los datos de los alumnos que están en centros con más de 2 alumnos
    -La consulta selecciona objetos “Alumno” completos
    -Se utilizará JOIN para contar los alumnos matriculados en cada centro (GROUP BY)
    y quedarnos con los que tienen más de 2 alumnos */

    private static void ejecutarConsulta3HQL(Session session){
        //COUNT VA EN HAVING NO EN WHERE
        /* 
        SI PONGO SELECT a para seleccionar todo un objeto todo alumno, no puedo pone count a
        String cadenaSql = "SELECT a,COUNT(a) FROM "+
        "alumnos a WHERE COUNT(a)>2 JOIN a.centro c GROUP BY c.nombre";
        */
       //SE HACE CON UNA SUBCONSULTA SACAMOS PRIMERO CENTRO QUE TIENEN MAS DE DOS Y LUEGO LOS
       //ALUMNOS DE ESOS CENTROS
       String cadenaSql = "SELECT a FROM alumnos a JOIN a.centro c WHERE c IN "
       +"( SELECT c2 FROM alumnos a2 JOIN a2.centro c2 GROUP BY c2 HAVING COUNT(a2)>2)";
        Query consulta = session.createQuery(cadenaSql);
        List<Alumno> resultado = consulta.list();
        for(Alumno alumno:resultado){
            System.out.println(alumno);
            System.out.println("-----------------");
        }
    }


    //-------------------------------------------------------------------------------------//
    //CONSULTA 4
    /*Muestra el nombre (sólo este campo) de los alumnos cuya edad está por encima de la
    media de SU centro.
    -La consulta selecciona un solo campo de los alumnos
    -Recordar subconsultas CORRELACIONADAS */

    private static void ejecutarConsulta4HQL(Session session){
        //Primero saquemos meidia de edad de los alumnos de su centro
        String subconsulta="(SELECT AVG(a2.edad) FROM alumnos a2 WHERE a2.centro =a.centro)";
        String consultaSql="SELECT a.nombre FROM alumnos a WHERE a.edad>"+subconsulta;
        //DEVUELVE LISTA DE NOMBRES DE GENTE SUPERIOR A MEDIA, dado que solo una columna
        //Se hace con list<object>
        Query resultado=session.createQuery(consultaSql);
        List<Object> nombres = resultado.list();
        for(Object n : nombres){
            System.out.println(n);
        }

    }



    //-------------------------------------------------------------------------------------//
    //CONSULTA 5
    /*Muestra los datos del alumno o alumnos más veteranos y un listado de sus módulos
    -La consulta selecciona objetos “Alumno” completos
    -Al recorrer los alumnos, sacamos sus módulos del atributo List del mismo */

    private static void ejecutarConsulta5HQL(Session session){
        //Mostramos objeto entero alumnno con mayor edad y los modulos que cursan 
        //que seria su propiedad list modulos
        //AUNQUE MODULOS SEA PROPIEDAD DE ALUMNOS SE HACE UN JOIN 
        /* 
        String consultaSql = "SELECT a,a.modulos FROM alumnos a WHERE a.edad="
        +"(SELECT MAX(a2.edad) FROM alumnos a2)";
        EN JOIN SE PONE ALIAS COMO EN FROM
        */
        //IMPRIMIA DIRECCION DE MEMORIA DE MODULOS, TENGO QUE ACCEDER A NOMBRE MODULOS
        String consultaSql = "SELECT a,m.nombre FROM alumnos a JOIN a.modulos m WHERE a.edad="
        +"(SELECT MAX(a2.edad) FROM alumnos a2)";
        //YA QUE NO ES SOLO UN OBJETO LO QUE DEVUELVE ENTERO SI NO OBJETO ALUMNO Y MODULOS
        //USO LIST<OBJECT[]>
        Query resultado = session.createQuery(consultaSql);
        List<Object[]> alumnoModulos = resultado.list();
        for(Object[] am : alumnoModulos){
            System.out.println("Alumno: " + am[0] + " --- Modulos: " + am[1]);
        }
    }
    



    //-------------------------------------------------------------------------------------//
    //CONSULTA 6
    /*Muestra los datos de los módulos que comienzan por “P” y un listado de sus 
    alumnos mayores de edad
    -La consulta selecciona objetos “Modulo” completos
    -Al recorrer los módulos, sacamos sus alumnos y sólo mostramos los que tienen una edad
    mayor de 18 */
    private static void ejecutarConsulta6HQL(Session session){
        //Tengo que mirar los modulos que empiezan por P y cada uno recorrer sus alumnos
        // y mostrar solo los >18 
        //HACEMOS CONSULTA QUE DEVUELVA TODOS LOS OBJETOS MODULO COMPLETO
        String consultaSql = "SELECT m FROM modulos m WHERE nombre LIKE 'P%'";
        //GUARDAMOS EN UNA LISTA CON OBJETOS MODULOS
        Query consultaModulos= session.createQuery(consultaSql);
        List<Modulo> modulos = consultaModulos.list();
        //CON TODOS LOS OBETOS MODULOS RECORREMOS SU ARRAY ALUMNOS, Y SACAMOS LOS QUE SON >18
        for(Modulo m:modulos){
            System.out.println(m.getNombre());
            //Recorremos con java los alumnos
            for(Alumno a: m.getAlumnos()){
                if(a.getEdad()>18){
                    //IMPRIMO TODO EL TO STRING ALUMNO NO SOLO EL NOMBRE
                    System.out.println(a);
                }
            }
        }
        
    }
}
