package entities;

import java.util.List;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;


//Si quiero un nombre distinto al de la clase en las tablas se le debe poner un name en Entity
@Entity(name = "actores")
public class Actor {

    //Se cambia el nombre de cada id secuencia para que no compartar numero
    //Tiene que tene rmismo name sequence generator que en generated value el generator,
    //Pero otro nombre sequence name para que no choquen las secuencias en la base de datos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceActor")
    @SequenceGenerator(name = "id_SequenceActor", sequenceName = "ID_SEQ_ACT")
    private int id;
    private String nombre;
    private String fecha_nacimiento;
    @ManyToMany(cascade = CascadeType.DETACH,mappedBy = "actores")
    private List<Pelicula> peliculas;


    //----------CONSTRUCTOR VACIO------
    public Actor(){

    }
    //------CONSTRUCTOR-----------
    public Actor(String nombre, String fecha_nacimiento) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
    }//constructor
    

    //-----GETTERS AND SETTERS----
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    //---------------------


    //----METODOS PARA RELACION PELICULAS ACTORES
    public void añadirPelicula(Pelicula pelicula){
        //AÑADIMOS LA PELICULA A EL ARRAY DE LAS PELICULAS DEL ACTOR
        peliculas.add(pelicula);
        //AÑADIMOS EN ESA PELICULA EN SU ARRAY DE ACTORES ESTE ACTORE
        pelicula.getActores().add(this);
    }

    public List<Pelicula> getPeliculas(){
        return peliculas;
    }

    
    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", peliculas=" + peliculas +
                '}';
    }
}
