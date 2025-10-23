//Carlos Ojeda Corona
//Ejercicio3-EJERCICIO FICHEROS ObjectInputStream y ObjectOutputStream
/*1º
-Realiza un programa Java para gestionar una serie de Articulos. Lo primero que hará el programa al
ejecutarse, será cargar los artículos almacenados en un fichero“artículos.dat”
(los artículos están almacenados en forma de objetos serializados mediante ObjectOutputStrem), 
en un arrayList de artículos.
La clase Articulo implementará la interface Serializable y tiene la siguiente estructura:
codigo (int)
nombre (String)
precio (double)
stock (int)
Posteriormente, se mostrará un menú con las siguientes opciones:
1.Mostrar todos los artículos (se leerán los datos del arrayList)
2.Añadir un artículo (se pedirán todos los datos del artículo por teclado y se almacenará en el
arrayList)
Nota: comprobaremos que los datos sean correctos y que el código y el nombre del artículo 
no existe ya
3.Mostrar datos de un artículo por nombre (se leerá por teclado y se buscará en el arrayList)
4.Mostrar artículos con stock mínimo (se leerá una cantidad por teclado y se buscarán artículos
con un stock menor a esta cantidad)
5.Modificar el precio de un artículo (pedirá el nombre por teclado y si existe, nos pedirá el nuevo
precio y lo modificará en el arrayList)
6.Salir (al salir, se copia todo el arrayList de artículos en el fichero “articulos.dat”,
utilizando la clase ObjectOutputStream) */


/*Clase Main, que gestiona la introduccion de datos en el fichero, su lectura, su modificacion en el array
 * del programa y su posterior reintroduccion al fichero.
 */
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        int opcion;
        Scanner sc = new Scanner(System.in);
        String archivo = "articulos.dat";
        Scanner scBuscar = new Scanner(System.in);
        ArrayList<Articulos> tienda = new ArrayList<>();

        //------------CREAMOS OBJETOS DE LECTURA ESCRITURA----------------------------
        //Outputs para escribir los objetos en el archivo
        FileOutputStream fos = new FileOutputStream(archivo);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //Inputs para leer el archivo
        FileInputStream fis = new FileInputStream(archivo);

        //--------------------------INSERTAMOS ARTICULOS-------------------------------
        //Hacemos que se inserten algunos articulos
        //Creamos un par de objetos articulo
        Articulos articulo1 = new Articulos(1, "Tomate", 3.53, 20);
        Articulos articulo2 = new Articulos(2, "Acelgas", 2.12, 40);
        Articulos articulo3 = new Articulos(3, "Brocoli", 1.21, 25);
        Articulos articulo4 = new Articulos(3, "Chocolate", 1.57, 50);

        //--------------------------INTRODUCIMOS LOS OBJETOS EN EL ARCHIVO--------------
        //Ahora pasamos estos articulos a un articulos.dat en forma de objetos
        try {
        oos.writeObject(articulo1);
        oos.writeObject(articulo2);
        oos.writeObject(articulo3);
        oos.writeObject(articulo4);
        } catch (Exception e) {
            System.out.println("Problemas al crear los articulos...");
        }
       
        //-----------------LEEMOS LOS ARTICULOS CON EL INPUT, Y LO AÑADIMOS AL ARRAYLIST------------
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            tienda.clear();
            while (true) {
                try {
                    Articulos articulo = (Articulos) ois.readObject();
                    tienda.add(articulo);
                } catch (EOFException e) {
                    //Fin del archivo - salir del bucle
                    break;
                }
            }
            System.out.println("Artículos cargados correctamente: " + tienda.size());
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar artículos: " + e.getMessage());
        }
        
        //------------------------------------------MENU-------------------------------------------
        do {
            System.out.println("Introduce una opcion: \n" + 
            "1.Mostrar todos los articulos\n" + 
            "2.Añadir un articulo\n" + 
            "3.Mostrar datos de un articulo por nombre\n" + 
            "4.Mostrar articulos con stock minimo\n" + 
            "5.Modificar precio de un articulo\n" + 
            "6.Salir");
            opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.println("Has elegido mostrar todos los articulos.");
                    System.out.println("--------------------------------------------");
                    if(tienda.isEmpty()){
                        System.out.println("No hay articulos en la tienda.");
                        return;
                    }else{
                        for(Articulos articulo:tienda){
                            System.out.println(articulo); //LLAMA AL METODO TO STRING DEL ARCHIVO
                            //POR LO QUE NOS SALDRA EL SOBREESCRIBIDO
                        }

                    }
                    break;
            
                case 2://Controlar excepciones de datos y validaciones
                    System.out.println("Has elegido añadir un articulo.");
                    System.out.println("--------------------------------------------");
                    //Codigo y nombre no se pueden repetir
                    int codigo;
                    String nombre;
                    double precio;
                    int stock;
                    boolean codigoRepetido=false;
                    boolean nombreRepetido=false;
                    do {
                        System.out.println("Introduce codigo: ");
                        codigo = sc.nextInt();
                        for(int i=0;i<tienda.size();i++){
                            if(codigo==tienda.get(i).getCodigo()){
                                System.out.println("Codigo repetido introduce otro...");
                                codigoRepetido=true;
                                break;
                            }
                        }
                            if(!codigoRepetido){
                                System.out.println("Codigo valido");
                                codigoRepetido=false;
                            }                      
                        } while (codigoRepetido);//Mientras que el codigo se repita pedir otro
                    
                    do {
                        System.out.println("Introduce nombre: ");
                        nombre = sc.nextLine();
                        for(int i=0;i<tienda.size();i++){
                            if(nombre==tienda.get(i).getNombre()){
                                System.out.println("Nombre repetido introduce otro...");
                                nombreRepetido=true;
                                break;
                            }
                        }
                            if(!nombreRepetido){
                                System.out.println("Nombre valido");
                                nombreRepetido=false;
                            }                      
                        } while (nombreRepetido);
                    
                    System.out.println("Introduce un precio: ");
                    precio = sc.nextDouble();
                    System.out.println("Introduce stock: ");
                    stock = sc.nextInt();
                    //Añadimos el articulo con los datos al Arraylist, para luego grabarlo en articulos.dat
                    tienda.add(new Articulos(codigo, nombre, precio, stock));
                    System.out.println("Se ha añadido el articulo");
                    break;
                
                case 3:
                    System.out.println("Has elegido mostrar un articulo.");
                    System.out.println("--------------------------------------------");
                    System.out.println("Introduce nombre del articulo a buscar: "); 
                    //Aqui no me va con sc supongo que da problemas buffer de sc, voy a hacer otro scanner
                    String nombreBuscado =  scBuscar.nextLine();
                    boolean encontrado = false;
                    for(int i=0;i<tienda.size();i++){
                        if(nombreBuscado.equalsIgnoreCase(tienda.get(i).getNombre())){
                            System.out.println("Articulo encontrado: ");
                            System.out.println("------------------------");
                            System.out.println(tienda.get(i).toString());
                            encontrado=true;
                            break;
                        }
                    }
                    if(!encontrado){
                        System.out.println("El articulo no se encontro por ese nombre");
                    }
                    break;

                case 4:
                    System.out.println("Has elegido buscar por stock");
                    System.out.println("--------------------------------------");
                    System.out.println("Introduce un stock y mostraremos que producto tienen menor stock: ");
                    int stockBuscado = sc.nextInt();
                    for(int i=0;i<tienda.size();i++){
                        if(stockBuscado>tienda.get(i).getStock()){
                            System.out.println("Este articulo tiene menor stock: \n ----------------");
                            System.out.println(tienda.get(i).toString());
                        }
                    }
                    break;

                case 5:
                    System.out.println("Has elegido modificar precio: ");
                    System.out.println("-----------------------------------");
                    System.out.println("Introduce nombre del articulo a buscar: "); 
                    String nombreaModificar =  scBuscar.nextLine();
                    boolean encontradoNombre = false;
                    for(int i=0;i<tienda.size();i++){
                        if(nombreaModificar.equalsIgnoreCase(tienda.get(i).getNombre())){
                            System.out.println("Articulo encontrado: ");
                            System.out.println("------------------------");
                            System.out.println(tienda.get(i).toString());
                            System.out.println("Introduce un nuevo precio (Decimales con comas): ");
                            tienda.get(i).setPrecio(sc.nextDouble());
                            encontradoNombre=true;
                            break;
                        }
                    }
                    if(!encontradoNombre){
                        System.out.println("El articulo no se encontro por ese nombre");
                    }
                    break;

                case 6:
                    System.out.println("Saliendo de la aplicacion...");
                    sc.close();
                    scBuscar.close();
                    try {
                        oos.writeObject(tienda);
                        System.out.println("Articulos guardados correctamente");
                    } catch (Exception e) {
                        System.out.println("Problemas al guardar los articulos");
                    }
                    oos.close();
                    fis.close();
                    break;
            }
        } while (opcion!=6);
    }

    public void cargarArticulos(){

    }

}//App
