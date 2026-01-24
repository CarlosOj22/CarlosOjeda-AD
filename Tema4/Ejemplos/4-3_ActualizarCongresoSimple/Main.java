/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizarcongreso;

/**
 *
 * @author IMCG
 */
//API necesaria de db4o
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
            "congreso.db4o");
    try {
      almacenarPonentes(db);
      consultarPonentes(db);
      consultarPonente200(db);
      actualizarEmailPonente(db, "22B", "isaperez@gmail.com");
      borrarPonenteporNif(db, "11A");
      consultarPonentes(db);
    } catch (Exception e) {
      //código para el tratamiento de la excepción
    } finally {
      db.close(); // cerrar la base de datos antes de salir
    }
  }

  //método que modifica el e-mail de un ponente cuyo nif se pasa como parámetro
  // y almacena en la base de objetos los nuevos valores
  public static void actualizarEmailPonente(ObjectContainer db, String nif, String em) {
    //se consulta a la base de objetos por el ponente del nif indicado
    ObjectSet res = db.queryByExample(new ponente(nif, null, null, 0));
    ponente p = (ponente) res.next(); //se obtiene el objeto consultado en p
    p.setEmail(em); //se cambia el email del objeto
    db.store(p); //se alamcena de nuevo el objeto poenente
  }

  // Método que elimina de la base de objetos [con delete()] el ponente cuyo
  //nif se pasa como parámetro
  public static void borrarPonenteporNif(ObjectContainer db, String nif) {
    //se consulta a la base de objetos por el ponente del nif indicado
    ObjectSet res = db.queryByExample(new ponente(nif, null, null, 0));
    ponente p = (ponente) res.next(); //se obtiene el objeto consultado en p
    db.delete(p); //se elimina el objeto poenente de la base de objetos
  }

  //Método para mostrar objetos recuperados de la Base de Objetos
  public static void mostrarConsulta(ObjectSet resul) {
    System.out.println("Recuperados " + resul.size() + " Objetos");
    while (resul.hasNext()) {
      System.out.println(resul.next());
    }
  }

  //Consultar Objetos. consultas QBE queryByExample()
  //consulta de todos los ponentes
  public static void consultarPonentes(ObjectContainer db) {
    ponente p = new ponente(null, null, null, 0);
    ObjectSet res = db.queryByExample(p);
    mostrarConsulta(res);
  }

  //consulta de un ponente en concreto. Consultar ponentes de cache 200.
  public static void consultarPonente200(ObjectContainer db) {
    ponente p = new ponente(null, null, null, 200);
    ObjectSet res = db.queryByExample(p);
    mostrarConsulta(res);
  }

  //Método para almacenar datos en la Base de Objetos.
  public static void almacenarPonentes(ObjectContainer db) {
    //se crean cuatro objetos tipo alumno con valores asignados
    ponente p1 = new ponente("11A", "Antonio Camaco", "acamacho@gmail.es", 300);
    ponente p2 = new ponente("22B", "Isabel Pérez", "iperez@hotmail.es", 100);
    ponente p3 = new ponente("33C", "Ana Navarro", "anavarro@yahoo.com", 200);
    ponente p4 = new ponente("44D", "Pedro Sánchez", "psanchez@mixmail.com", 90);
    //Persistir Objetos: almacenamos los objetos con el método store()
    db.store(p1);
    db.store(p2);
    db.store(p3);
    db.store(p4);
  }
}
