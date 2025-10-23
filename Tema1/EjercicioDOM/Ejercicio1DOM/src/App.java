//Carlos Ojeda Corona
//Ejercicio 4-ACCESO A FICHERO XML (DOM)
/*Realiza un programa Java que:
a)En su método main cree un fichero “EMPLEADOS.DAT”, que contenga al menos
5 objetos de la clase Empleado.
La clase Empleado implementa al Interface Serializable y tiene las siguientes propiedades:
CODIGO (int)
NOMBRE (string)
DIRECCION (string)
SALARIO (float)
COMISION (float)
Se utilizará un FileOutputFile para crear el fichero.

b)A partir de los datos del fichero “EMPLEADOS.DAT” crear un fichero 
llamado “EMPLEADOS.XML” usando DOM y que tenga una estructura como la siguiente 
(se puede hacer con un método que recibe como parámetro el String “EMPLEADOS.DAT”):
EMPLEADOS.XML ” usando DOM y que tenga una estructura como la siguiente
(se puede hacer con un método que recibe como parámetro el String
“EMPLEADOS.DAT”)
EMPLEADOS.DAT”):
---------------------------------------------------
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<EMPLEADOS>
  <Empleado>
    <CODIGO>1</CODIGO>
    <COMISION>12.0</COMISION>
    <DIRECCION>Madrid</DIRECCION>
    <NOMBRE>Juan</NOMBRE>
    <SALARIO>1500.0</SALARIO>
  </Empleado>
  <Empleado>
    <CODIGO>2</CODIGO>
    <COMISION>9.0</COMISION>
    <DIRECCION>Murcia</DIRECCION>
    <NOMBRE>María</NOMBRE>
    <SALARIO>1700.0</SALARIO>
  </Empleado>
.............................................
.............................................
</EMPLEADOS> */




import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Archivo " + archivo + " creado correctamente...");
            oos.close();
         }catch (Exception e) {
           System.out.println("Problemas en la creacion del archivo...");
         }
    }

    public static void creacionXML(String archivoDat){
       List<Empleado> listaEmpleados = new ArrayList<>();//Lista para almacenar los empleados leidos del dat
      
       try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDat))) {

      //Inputs para leer el archivo empleados dat que hemos creado 
        
        while (true) {
          try {
              Empleado empleado = (Empleado) ois.readObject();
              listaEmpleados.add(empleado);
          } catch (EOFException e) {
              break;
          }
}
              //CREO DOM VACIO, CON DOCUMENT BUILDE FACTORU, DOM IMPLEMENTATION o COMO EJEMPLO
              //CHEQUEAR REFERENCIAS DESDE STATICOS A NO ESTATICOS Y QUE ES MEJOR DEMAS FORMAS DE HACERLo

              
              DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
              Document doc = dBuilder.newDocument();
              //SE CREA ASI? UNO POR UNO? 
              Element elementoRaiz = doc.createElement("EMPLEADOS");
              doc.appendChild(elementoRaiz);

              for(Empleado empleado : listaEmpleados){
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
              }

            //GUARDAR EL DOCUMENTO COMO XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoXml));
            
            transformer.transform(source, result);
    }catch(Exception e) {
      
    } 
}
}
