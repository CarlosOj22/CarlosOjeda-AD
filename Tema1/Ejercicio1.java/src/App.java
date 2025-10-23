//Carlos Ojeda Corona
//Ejercicio1-Lectura y escritura Archivos CSV
/*Crea un programa Java que:
a)
En primer lugar,genere el archivo “alumnosNotas.txt” con java con la siguiente información y
después lo muestre por pantalla.
pepe:5:4:3
ana:6:7:8
luisa:9:10:8
diego:5:5:2 
b)
A continuación, el programa lee los datos que hay en el archivo “alumnosNotas.txt”, que contiene la información
anterior y calcula la nota media de cada alumno, generando un archivo llamado “alumnosMedias.txt”
con una línea para cada alumno, con el formato:nombre_alumno:notaMedia
c)
Por último, muestra por pantalla en contenido del fichero “alumnosMedia.txt” */


import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
  
        //Declaracion de variables
        //----------------------
        boolean salir=false;
        String alumnoBase1=new String("pepe:5:4:3\n");
        String alumnoBase2=new String("ana:6:7:8\n");
        String alumnoBase3=new String("luisa:9:10:8\n");
        String alumnoBase4=new String("diego:5:5:2\n");
        Scanner sc=new Scanner(System.in);

        //--------------------------------------------------------------
        //Creamos objeto que encapsula fichero
        File fichero=new File("alumnosNotas.txt");
        RandomAccessFile miRAF=new RandomAccessFile(fichero,"rw");
        File ficheroMedias=new File("alumnosMedias.txt");
        RandomAccessFile mediasRAF=new RandomAccessFile(ficheroMedias, "rw");
        //A partir de este objeto creamos fichero fisico
        miRAF.setLength(0);//BORRAMOS LO QUE HUBIERA
        miRAF.seek(miRAF.length());
        try{
            
            //LO hago asi desimple por que solo son 4 
            miRAF.writeBytes(alumnoBase1);
            miRAF.writeBytes(alumnoBase2);
            miRAF.writeBytes(alumnoBase3);
            miRAF.writeBytes(alumnoBase4);

            do {
                System.out.println("Quieres introducir algun alumno? s/n");
                String opcion=sc.nextLine();
                if(opcion.equalsIgnoreCase("s")){
                    salir=false;
                }else
                {
                    System.out.println("Saliendo de la app...");
                    salir=true;
                }
            } while (!salir);
        }catch (Exception ioe){
            ioe.getMessage();
        }
        
        //LEEMOS LA INFORMACION DEL ARCHIVO Y CALCULAMOS LA MEDIA

        //Ponemos el puntero de randomacces file a cero para leer desde el principio
        miRAF.seek(0);
        mediasRAF.setLength(0);//Borramos lo que hubiera en medias
        //Leemos linea
        String linea;
        while((linea=miRAF.readLine())!=null){//Leemos linea por linea
            String[] lineas=linea.split(":"); 
            //El metodo split en este caso separa a partir de una expresion regular
            //Separamos el nombre de las notas
            //!!!AQUI TENDRIAMOS EN LINEAS ESTO: (pepe,8,5,3)
            String nombre=lineas[0];
            int suma=0;

            for(int i=1;i<lineas.length;i++){
                suma+=Integer.parseInt(lineas[i]);//AQUI TENDRIAMOS ESTO 7+8+9
            }

            double media=(double) suma/(lineas.length-1);
            mediasRAF.seek(mediasRAF.length());
            mediasRAF.writeBytes(nombre+":"+String.format("%.2f", media)+ "\n");
        }

        //MOSTRAMOS LA NOTA MEDIA DE LOS ALUMNOS
        System.out.println("NOTAS MEDIAS:");
        mediasRAF.seek(0);//Volvemos al principio
        String medias;
        while((medias=mediasRAF.readLine()) != null){
            System.out.println(medias);
        }
    
    }
}
