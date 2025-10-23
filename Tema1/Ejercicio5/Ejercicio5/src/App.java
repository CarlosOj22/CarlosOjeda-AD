//Carlos Ojeda Corona
//Ejercicio 5-ACCESO A FICHEROS XML (DOM Y STAX)
/*Realiza un programa Java que visualice todas las etiquetas del fichero “libros.xml"
que tiene el siguiente contenido:
<libros>
    <libro año="1994">
        <titulo>TCP/IP Illustrated</titulo>
        <autor>
            <apellido>Stevens</apellido>
            <nombre>W.</nombre>
        </autor>
        <editorial>Addison-Wesley</editorial>
        <precio> 65.95</precio>
    </libro>
    <libro año="1992">
        <titulo>Advan Programming for Unix environment</titulo>
        <autor>
            <apellido>Stevens</apellido>
            <nombre>W.</nombre>
        </autor>
        <editorial>Addison-Wesley</editorial>
        <precio>70.95</precio>
    </libro>
    <libro año="2000">
        <titulo>Data on the Web</titulo>
        <autor>
            <apellido>Abiteboul</apellido>
            <nombre>Serge</nombre>
        </autor>
        <autor>
            <apellido>Buneman</apellido>
            <nombre>Peter</nombre>
        </autor>
        <autor>
            <apellido>Suciu</apellido>
            <nombre>Dan</nombre>
        </autor>
        <editorial>Morgan Kaufmann editorials</editorial>
        <precio>39.95</precio>
    </libro>
</libros>
El contenido se mostrará 2 veces:
La primera utilizando un método que recibe la cadena “libros.xml” como parámetro y utiliza la
tecnología DOM.
La segunda mediante un método que recibe la cadena “libros.xml” como parámetro y utiliza la
tecnología STAX.
La salida debe tener un aspecto como el siguiente:
-----------
COMIENZO LECTURA DOM
--------------
Elemento: libro
Año : 1994
Autor : Stevens , Arthur
Titulo : TCP/IP Illustrated
Editorial : Addison-Wesley
Precio : 65.95
...................................
...................................
Elemento: libro
Año : 1992
Autor : Stevens , Arthur
Titulo : Programming for Unix
Editorial : Addison-Wesley
Precio : 70.95
...................................
...................................*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App {
    
    public static void main(String[] args) throws Exception {
        String archivo = "libros.xml";
        System.out.println("LECTURA CON DOM:");
        tecnologiaDOM(archivo);
        System.out.println("---------------------------");
        System.out.println("LECTURA CON STAX:");
        tecnologiaSTAX(archivo);

    }

    public static void tecnologiaDOM(String archivo) throws FileNotFoundException,XMLStreamException{
        try {
            File inputFile = new File(archivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Raiz del documento: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("libro");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;
                    System.out.println("Elemento: libro");
                    System.out.println("Año: " + elemento.getAttribute("año"));
                    NodeList autores = elemento.getElementsByTagName("autor");
                    //SACO TODOS LOS AUTORES SI TIENEN VARIOS
                    for (int j = 0; j < autores.getLength(); j++) {
                        Element autor = (Element) autores.item(j);
                        System.out.println("Autor: " + autor.getElementsByTagName("nombre").item(0).getTextContent() + " , " + autor.getElementsByTagName("apellido").item(0).getTextContent());
                    }
                    System.out.println("Titulo: " + elemento.getElementsByTagName("titulo").item(0).getTextContent());
                    System.out.println("Editorial: " + elemento.getElementsByTagName("editorial").item(0).getTextContent());
                    System.out.println("Precio: " + elemento.getElementsByTagName("precio").item(0).getTextContent());
                    System.out.println("...................................");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void tecnologiaSTAX(String archivo)throws FileNotFoundException,XMLStreamException{
        XMLInputFactory entradaXmlStax = XMLInputFactory.newInstance();
        XMLStreamReader lecturaXmlStax = entradaXmlStax.createXMLStreamReader
        (new FileReader(archivo));
        String tag = null;
        int tipoEvento;
        String apellido="";
        System.out.println("Lista de Libros: ");
        while(lecturaXmlStax.hasNext()){
            tipoEvento=lecturaXmlStax.next();//Guardamos el tipo de evento para el case
            switch (tipoEvento) {
                case XMLEvent.START_ELEMENT:
                    tag = lecturaXmlStax.getLocalName();
                    if(tag.equals("libro")){
                        String año = lecturaXmlStax.getAttributeValue(null, "año");
                        System.out.println("Elemento: libro");
                        System.out.println("Año: " + año);
                    } else if(tag.equals("titulo")) {
                            System.out.println("Titulo: " + lecturaXmlStax.getElementText());
                    } else if(tag.equals("apellido")) {
                        apellido= lecturaXmlStax.getElementText();
                    } else if(tag.equals("nombre")) {
                        System.out.println("Autor: " + apellido+ " , " +lecturaXmlStax.getElementText());
                    } else if(tag.equals("editorial")) {
                        System.out.println("Editorial: " + lecturaXmlStax.getElementText());
                    } else if(tag.equals("precio")) {
                        System.out.println("Precio: " + lecturaXmlStax.getElementText());
                        System.out.println("...................................");
                    }
                    
                    break;
                    case XMLEvent.END_DOCUMENT:
                        System.out.println("Fin del documento");
                    break;
           
                default:
                    break;
                }
            }
        }

    }

