//Carlos Ojeda Corona
//Ejercicio1

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
