//Carlos Ojeda Corona
/*Se desea implementar un sistema de gestión de información sobre un VEHICULO utilizando
JAXB para la serialización y deserialización de objetos a XML.
1. Crea una clase principal llamada Vehiculo que contenga la información básica del mismo:
Marca(String)
Modelo(String)
Motor (objeto de la clase Motor)
Ruedas (objeto de la clase Ruedas)
2.
Crea las clases:
Motor: con los atributos
Combustible(String)
Cilindros(int)
Potencia (int) :será un atributo de la propiedad Motor en el fichero XML generado
Ruedas: con los atributos
Numero (int)
Tipo (String)
3.
Implementa las anotaciones necesarias con JAXB para que:
La clase principal se convierta en un nodo raíz XML.
Las propiedades anidadas Motor y Ruedas, aparezcan como subnodos dentro del XML.
4.
Realiza un programa de prueba que:
Cree un objeto de tipo Vehiculo con todos sus datos.
Lo serialice a un archivo XML.
Lea nuevamente el archivo XML generado en el punto anterior
y muestre los datos por consola. 

EJEMPLO DE SALIDA DEL FICHERO VEHICULO.XML
<vehiculo>
    <marca>Toyota</marca>
    <modelo>Corolla</modelo>
    <motor potencia="150">
    <combustible>Gasolina</combustible>
        <cilindros>4</cilindros>
    </motor>
    <ruedas>
        <numero>4</numero>
        <tipo>Alloy</tipo>
    </ruedas>
</vehiculo>
*/

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class App {
    public static void main(String[] args) throws Exception {
        // Crear objeto Motor
        Motor motor = new Motor("Gasolina", 150, 4);
        // Crear objeto Ruedas
        Ruedas ruedas = new Ruedas(4, "Michelin");
        // Crear objeto Vehiculo
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", motor, ruedas);

        // Serializar a XML
        JAXBContext context = JAXBContext.newInstance(Vehiculo.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(vehiculo, new File("vehiculo.xml"));

        // Leer desde XML
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Vehiculo vehiculoLeido = (Vehiculo) unmarshaller.unmarshal(new File("vehiculo.xml"));
        System.out.println("Vehiculo leído desde XML:");
        System.out.println("Marca: " + vehiculoLeido.getMarca());
        System.out.println("Modelo: " + vehiculoLeido.getModelo());
        System.out.println("Motor:");
        System.out.println("  Combustible: " + vehiculoLeido.getMotor().getCombustible());
        System.out.println("  Cilindros: " + vehiculoLeido.getMotor().getCilindros());
        System.out.println("  Potencia: " + vehiculoLeido.getMotor().getPotencia());
        System.out.println("Ruedas:");
        System.out.println("  Número: " + vehiculoLeido.getRuedas().getNumero());
        System.out.println("  Tipo: " + vehiculoLeido.getRuedas().getTipo());
    }
}


