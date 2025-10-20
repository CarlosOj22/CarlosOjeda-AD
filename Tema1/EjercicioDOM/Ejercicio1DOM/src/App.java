//Carlos Ojeda Corona
//Ejercicio 1 DOM-ACCESO A DATOS

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//IMPORT PARA XML DOM JAVA
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class App {
        static String archivo = "EMPLEADOS.dat";
        static String archivoXml = "EMPLEADOS.xml";
    public static void main(String[] args) throws Exception {

        //Outputs para crear el archivo xml 
        FileOutputStream fos = new FileOutputStream(archivoXml);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //Escribimos los objetos en el archivo:
        introducirEmpleados();
        //Creamos XML a partir del dat
        creacionXML(archivo);
    }

    public static void introducirEmpleados(){
    
         Empleado emp1 = new Empleado(1,"Juan","Calle La Mora",
         1250.32f,123.2f);
         Empleado emp2 = new Empleado(2,"Pepe","Subida del cerro",
         1400.50f,87.3f);
         Empleado emp3 = new Empleado(3,"Luis","Calle Descalza Nº3",
         1100.15f,23.00f);
         Empleado emp4 = new Empleado(4,"Ana","Calle Cerrajeros Escalera 4",
         2140.23f,432.90f);
         Empleado emp5 = new Empleado(5,"Maria","Avenida Nueva 4",
         1873.40f,234.43f);

         //Introduczo la creacion del archivo y su escritura en un trycatch
         try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))){
            //Podriamos almacenar en un array de personas las personas y escribirlas con for
            oos.writeObject(emp1);
            oos.writeObject(emp2);
            oos.writeObject(emp3);
            oos.writeObject(emp4);
            oos.writeObject(emp5);

            oos.close();
         }catch (Exception e) {
           System.out.println("Problemas en la creacion del archivo...");
         }
    }

    public static void creacionXML(String archivoBat){
       
      try {//METO TODO EN UN TRY CATCH PARA QUE NO ERROR Y SEA VISIBLE LAS VARIABLES ATODO EL METODO

      //Inputs para leer el archivo empleados dat que hemos creado 
        FileInputStream fis = new FileInputStream(archivo);
        ObjectInputStream ois = new ObjectInputStream(fis);

        //CREO DOM VACIO, CON DOCUMENT BUILDE FACTORU, DOM IMPLEMENTATION o COMO EJEMPLO
        //CHEQUEAR REFERENCIAS DESDE STATICOS A NO ESTATICOS Y QUE ES MEJOR DEMAS FORMAS DE HACERLo

      
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.newDocument();
          //SE CREA ASI? UNO POR UNO? 
          Element elementoRaiz = doc.createElement("EMPLEADOS");
          doc.appendChild(elementoRaiz);


          try {
            while(true){
              Empleado empleado = (Empleado)ois.readObject();

              //Creamos elemento empleado para el xml
              Element elementoEmpleado = doc.createElement("Empleado");
              elementoRaiz.appendChild(elementoEmpleado);

              //Codigo
              Element elementoCodigo = doc.createElement("CODIGO");
              elementoCodigo.appendChild(doc.createTextNode(String.valueOf(empleado.getCodigo())));
              elementoEmpleado.appendChild(elementoCodigo);

              //COMISION
              Element elementoComision = doc.createElement("COMISION");
              elementoComision.appendChild(doc.createTextNode(String.valueOf(empleado.getComision())));
              elementoEmpleado.appendChild(elementoComision);


              //DIRECCION
              Element elementoDireccion = doc.createElement("DIRECCION");
              elementoDireccion.appendChild(doc.createTextNode(empleado.getDireccion()));
              elementoEmpleado.appendChild(elementoDireccion);

              //NOMBRE
              Element elementoNombre = doc.createElement("NOMBRE");
              elementoNombre.appendChild(doc.createTextNode(empleado.getNombre()));
              elementoEmpleado.appendChild(elementoNombre);
              //SALARIO
              Element elementoSalario = doc.createElement("SALARIO");
              elementoSalario.appendChild(doc.createTextNode(String.valueOf(empleado.getSalario())));
              elementoEmpleado.appendChild(elementoSalario);

              ois.close();
              fis.close();

            //GUARDAR EL DOCUMENTO COMO XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoXml));
            
            transformer.transform(source, result);
            }
          } catch (Exception e) {
            
          }
          } catch (Exception e) {
        }
    }  
}
