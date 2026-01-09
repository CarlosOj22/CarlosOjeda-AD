package com.example;

import config.HibernateUtil;
import repositories.DirectoresRepository;
import repositories.ActoresRepository;
import repositories.PeliculaRepository;
import java.util.Scanner;
import org.hibernate.Session;
import entities.Actor;
import entities.Director;
import entities.Pelicula;
import java.util.List;

//EN HIBERNATE TENGO ESTO COMO LA PRACC DE CONEXIONES: <property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property> 
/*PERO CHAT GPT RECOMIENDA ESTO:<property name="hibernate.dialect">
    org.hibernate.dialect.Oracle12cDialect
</property>*/
//Y uso en pom.xml la version de java 17 no 19 por que asi lo hicimos con oracle con la 17
public class Main {

    //REPASAR ETIQUETAS HIBERTANETE MANYTOMANY JOIN TABLE Y DEMAS!
    static PeliculaRepository pRepo; //LO SACO FUERA PARA QUE LO VEO EL METODO DE SACAR TODOS
     //LOS DIRECTORS DE LAS PELICULAS
    public static void main(String[] args) {
        //Añadimos 3 registros de cada tabla
        //Creamos actores primero
        Actor actor1 = new Actor("Leonardo DiCaprio", "11-11-1974");
        Actor actor2 = new Actor("Samuel L. Jackson", "21-12-1948");
        Actor actor3 = new Actor("Jeff Goldblum", "22-10-1952");
        //Creamos array de actores para asignarles a las peliculas
        List<Actor> actoresPelicula1 = List.of(actor3, actor1);
        List<Actor> actoresPelicula2 = List.of(actor1,actor2);
        List<Actor> actoresPelicula3 = List.of(actor2,actor3);
        //Creamos directores
        Director director1 = new Director("Steven Spielberg", "18-12-1946", null);
        Director director2 = new Director("Christopher Nolan", "30-07-1970", null);
        Director director3 = new Director("Quentin Tarantino", "27-03-1963", null);
        //Creamos peliculas
        Pelicula pelicula1 = new Pelicula("15-07-1993", "Jurassic Park", director1);
        Pelicula pelicula2 = new Pelicula("16-07-2010", "Inception", director2);
        Pelicula pelicula3 = new Pelicula("21-05-1994", "Pulp Fiction", director3);
        Pelicula pelicula4 = new Pelicula("22-11-2024", "Nueva Pelicula", director2);
        Pelicula pelicula5 = new Pelicula("10-10-2023", "Otra Pelicula", director1);    
        Pelicula pelicula6 = new Pelicula("05-05-2022", "Ultima Pelicula", director3);
      
        //ASIGANMOS ACTORES DE LAS PELICULAS
        pelicula1.setActores(actoresPelicula1);
        pelicula2.setActores(actoresPelicula2);
        pelicula3.setActores(actoresPelicula3);
        /*ESTO NO SE HACE MANUAL DA ERRORESSSS 
        //ASIGNAMOS PELICULAS A DIRECTORES
        director1.setPeliculas(peliculasDirector1);
        director2.setPeliculas(peliculasDirector2);
        director3.setPeliculas(peliculasDirector3);
        //ASIGNAMOS PELICULAS A ACTORES
        actor1.setPeliculas(peliculasActor1);
        actor2.setPeliculas(peliculasActor2);       
        actor3.setPeliculas(peliculasActor3);
        */

        //AÑADIMOS CON OPERACIONES CRUD DE HIBERNATE
        //CREAMOS OBJETOS REPOSITORIOS CON UNA SESSION
        Session session = HibernateUtil.get().openSession();
        //Añadir actores
        ActoresRepository aRepo = new ActoresRepository(session);
        DirectoresRepository dRepo = new DirectoresRepository(session);
        pRepo = new PeliculaRepository(session);

        //YA CREADA LAS SESIONES DE TODOS LOS REPOSITORIOS Y TODOS LOS OBJETOS GUARDAMOS TODO
        //PRIMERO GUARDO DIRECTOR PARA QUE NO DE PROBLEMAS DE FK 
        //Luego actores que son indeendientes como directores, y por 
        //ultimo peliculas que dependen de los otros dos
        dRepo.save(director1);
        dRepo.save(director2);
        dRepo.save(director3);
        aRepo.save(actor1);
        aRepo.save(actor2);
        aRepo.save(actor3);
        pRepo.save(pelicula1);
        pRepo.save(pelicula2);
        pRepo.save(pelicula3);
        pRepo.save(pelicula4);
        pRepo.save(pelicula5);
        pRepo.save(pelicula6);
        

        //MENU DEL EJERCICIO
        int opcion=0;
        Scanner sc= new Scanner(System.in);
        Scanner scLetras = new Scanner(System.in);
        List<Pelicula> peliculasImprimir =null;

        do {
            System.out.println("---MENU HIBERNATE OPCIONES");
            System.out.println("1.Mostrar los datos de todas las peliculas.");
            System.out.println("2.Insertar una película nueva.");
            System.out.println("3.Borrar pelicula por ID.");
            System.out.println("4.Listado de peliculas por director.");
            System.out.println("5.Salir del programa.");
            opcion=sc.nextInt();
             switch (opcion) {
            case 1:
                //LLAMO AL TO STRING QUE DE CADA PELICULA QUE DEVUELVE EL LIST DE FINDALL DE PELICULAREPOSITORY
                //Creo que se podria hacer directamente en SOUT llamada al to string de la lista no? y va imprimiendo 
                //todos
                peliculasImprimir = pRepo.findAll();
                for(Pelicula p : peliculasImprimir){
                    System.out.println(p.toString());
                    System.out.println("--------------------");
                }
                break;

            case 2:
                //El id lo pone el autoincremental
                String nombrePelicula;
                String fechaEstreno;
                //Creo un nuevo objeto director de la peli 
                //Pero le paso nulll fecha nac, y lo demas
                //Podria pasarle un id de un director creado tambien a la nueva film
                Director directorPeli;
                String nombreDirector;

                System.out.println("Introduce el nombre de la pelicula nueva:");
                nombrePelicula=scLetras.nextLine();
                System.out.println("Introduce la fecha de estreno (Formato preferible xx/xx/xxxx):");
                fechaEstreno=scLetras.nextLine();
                System.out.println("Introduce el nombre del director de la pelicula:");
                nombreDirector=scLetras.nextLine();
                directorPeli = new Director(nombreDirector, null, null);
                Pelicula nuevaPelicula = new Pelicula(fechaEstreno, nombrePelicula, directorPeli);
                directorPeli.añadirPelicula(nuevaPelicula);
                pRepo.save(nuevaPelicula);
                break;
        

            case 3:
                System.out.println("Introduce un ID de pelicula para borrar:");
                int idBorrar= sc.nextInt();
                //Si no saca pelicula por ID no existe ese ID
                if(pRepo.findOneById(idBorrar).equals(null)){
                    System.out.println("Pelicula con ID no encontrada");
                }else{
                    Pelicula peliculaBorrar=pRepo.findOneById(idBorrar);
                    pRepo.delete(peliculaBorrar);
                    System.out.println("Pelicula Borrada con exito");
                }
                break;
        

            case 4:
                System.out.println("Dame un nombre del director para buscar sus peliculas:");
                String nombreDirectorBuscar = scLetras.nextLine();
                //Podria ir metiendo los directores en un array provisional para luego sacar su id
                //Pero voy a crear un metodo para sacar id que recorra todos los directores
                //De las pelis, cuando encuentre el nombre del director que pare y devuelva ese objeto
                //director sacando su id
                if(sacarIdDirector(nombreDirectorBuscar)==null){
                    System.out.println("No se encontro ese director.");
                }else{ //En este caso es que existe directo
                    int idDirector = sacarIdDirector(nombreDirectorBuscar).getId();
                    List<Pelicula> peliculasAMostrar= dRepo.findOneById(idDirector).getPeliculas();
                    System.out.println("Peliculas de " + nombreDirectorBuscar + " :");
                    //Lo hago con for each aunque se podria con SOUT directo creo
                    for(Pelicula p : peliculasAMostrar){
                        p.toString();
                    }
                }
                break;
        

            case 5:
                System.out.println("Saliendo del programa...");
                sc.close();
                scLetras.close();
                break;
    
            default:
                System.out.println("Fallo en el proceso...");
                break;
        
            }
        } while (opcion!=5);
       
    }

    public static Director sacarIdDirector(String nombreDirector){
        List<Pelicula> todasPeliculas = pRepo.findAll();
        for(Pelicula p:todasPeliculas){
            if(p.getDirector().getNombre()==nombreDirector){
                return p.getDirector();
            }
        }
        return null;
    }
}
