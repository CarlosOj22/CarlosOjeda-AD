//Carlos Ojeda Corona
//Ehercicio 2-Ejercicios de Ficheros de acceso aleatorio
/*1º
-A ejecutar el programa, lo primero que hace es crear el fichero alumnos.dat de acceso
aleatorio con la siguiente información:
Apellido:"FERNANDEZ","LOPEZ","GOMEZ","SERRANO","ALONSO"
Edad:17, 20, 18, 17, 19
Nota1:7.5, 4.2, 6.5, 8.0, 3.2
Nota2:5.5, 9.2, 8.5, 5.0, 2.0
Nota3:4.6, 3.5, 9.0, 7.1, 1.9
Después se mostrará un menú con las siguientes opciones:
1.Mostrar datos del alumno (por numero de orden)
2.Generar fichero de medias (mediasalumnos.dat)
será de acceso aleatorio y tendrá el nombre y la media de notas de todos los alumnos
3.Mostrar las notas medias de todos los alumnos (recorrido de fichero mediasalumnos.dat)
4.Borrar el fichero mediasalumnos.dat (si no está creado, mostrará un mensaje)
5.Salir del programa */


import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        //Declaracion de variables 
        //------------------------------------------------
        String[] Apellidos={"FERNANDEZ","LOPEZ","GOMEZ","SERRANO","ALONSO"};
        int[] edad={17,20,18,17,19};
        double[] nota1={7.5,4.2,6.5,8.0,3.2};
        double[] nota2={5.5,9.2,8.5,5.0,2.0};
        double[] nota3={4.6,3.5,9.0,7.1,1.9};
        int opcion=0;
        Scanner sc=new Scanner(System.in);
        //Declaracion de ficheros y flujos de datos
        //---------------------------------------------------
        File f= new File("alumnos.dat");
        RandomAccessFile miRaf=null;
        File ficheroMedias=new File("C:\\DAM2\\AD\\Ejercicios\\Tema1\\Ejercicio2\\Archivos\\alumnosMedias.dat");
        RandomAccessFile mediasRAF=new RandomAccessFile(ficheroMedias, "rw");


        try {
            miRaf=new RandomAccessFile(f,"rw");
            for (int i = 0; i < Apellidos.length; i++) {//Con string format damos formatos de bytes
                String registro = String.format("%-10s/%2d/%4.1f/%4.1f/%4.1f", 
                    Apellidos[i], edad[i], nota1[i], nota2[i], nota3[i]);
                
                // Asegurar longitud fija de 48 caracteres
                if (registro.length() < 48) {
                    registro = String.format("%-48s", registro);
                }
                
                miRaf.writeBytes(registro);
            }
        } catch (Exception e) {
            System.out.println("Problemas al crear al archivo");
        }
        
        miRaf.seek(0);
        
        do {
            System.out.println("1.Mostrar datos del alumno\n" + 
                "2.Generar fichero de medias\n" + 
                "3.Mostrar nota media de todos los alumnos\n" + 
                "4.Borrar fichero medias\n" + 
                "5.Salir del programa\n");
                opcion=sc.nextInt();
            switch (opcion) {
                
            case 1:
                System.out.println("Has elegido mostrar datos del alumno.");
                System.out.println("Introduce numero del alumno: ");
                int multiplo=sc.nextInt();
                multiplo=multiplo-1;
                if(multiplo>=0||multiplo<6){
                miRaf.seek(48*(multiplo));
                byte[] bytes = new byte[48];
                miRaf.read(bytes);    
                String linea = new String(bytes).trim();
                System.out.println("Datos del alumno: " + linea);
                }else{
                    System.out.println("No se encuentra ese alumno");
                }
                break;
        
            case 2:
                System.out.println("Has elegido generar fichero de medias.");
                //LEEMOS LA INFORMACION DEL ARCHIVO Y CALCULAMOS LA MEDIA

                    //Ponemos el puntero de randomacces file a cero para leer desde el principio
                    miRaf.seek(0);
                    mediasRAF.setLength(0);//Borramos lo que hubiera en medias
                       //Leemos los registros
                        for (int i = 0; i < Apellidos.length; i++) {
                            miRaf.seek(48 * i);
                            byte[] bytes = new byte[48];
                            miRaf.read(bytes);
                            String linea = new String(bytes).trim();
                            
                            String[] partes = linea.split("/");
                            String nombre = partes[0].trim();
                            //DABA ERROR DE FORMATO USA ESPAÑOL CON COMA; USMAOS METODOR REPLACE
                            double nota1_val = Double.parseDouble(partes[2].trim().replace(",", "."));
                            double nota2_val = Double.parseDouble(partes[3].trim().replace(",", "."));
                            double nota3_val = Double.parseDouble(partes[4].trim().replace(",", "."));
                            
                            double media = (nota1_val + nota2_val + nota3_val) / 3.0;
                            
                            mediasRAF.seek(mediasRAF.length());
                            //Con string format le damos formatos a una cadena
                            /*Podemos elegir el numero de ancho que ocupara 10 caracteres
                            EL signo - rellenara con espacios en blanco a la derecha si es mas corta
                            con esto nos garantizamos un tamaño fijo*/
                            String registroMedia = String.format("%-10s: %.2f", nombre, media);
                            //Aseguramos la longitud fija para el archivo de medias
                            if (registroMedia.length() < 20) {
                                registroMedia = String.format("%-20s", registroMedia);
                            }
                            mediasRAF.writeBytes(registroMedia);
                        }
                        
                        
                break;

            case 3:
                System.out.println("Has elegido mostrar nota media de todos los alumnos.");
                //Mostramos notas medias
                //--------------------------------------------
                System.out.println("Notas medias:");
                mediasRAF.seek(0);
                for (int i = 0; i < Apellidos.length; i++) {
                byte[] bytes = new byte[20];
                mediasRAF.read(bytes);
                String lineaMedia = new String(bytes).trim();
                System.out.println(lineaMedia);
                        }
                break;

            case 4:
                miRaf.close();
                System.out.println("Has elegido borrar el fichero de medias.");
                if(!ficheroMedias.exists()){
                    System.out.println("Fichero medias no existe.");
                }else{
                    if(ficheroMedias.delete()){
                        System.out.println("Fichero medias borrado correctamente");
                    }else{
                        System.err.println("Error al borrar fichero medias");
                    }

                }
                break;
            
            case 5:
                System.out.println("Saliendo del programa...");
                break;
        }
        } while (opcion!=5);
        
    }
}//MAIN
