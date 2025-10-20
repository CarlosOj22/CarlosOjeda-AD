import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class App {
    String archivo = "libros.xml";
    public static void main(String[] args) throws Exception {
        

    }

    public void tecnologiaDOM(String archivo){
        try {
            File inputFile = new File(archivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void tecnologiaSTAX(String archivo){

    }
}
