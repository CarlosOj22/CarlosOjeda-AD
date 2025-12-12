package entities;

import java.util.List;

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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_SequenceActor", sequenceName = "ID_SEQ")
    @ManyToMany(cascade = CascadeType.DETACH)
    private int id;
    private String nombre;
    private String fecha_nacimiento;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name="actores_peliculas", joinColumns={@JoinColumn(name="id_actor")},
    inverseJoinColumns = {@JoinColumn(name="id_pelicula")})
    private List<Pelicula> peliculas;


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
}
