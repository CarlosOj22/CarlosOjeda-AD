package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity(name="directores")
public class Director {
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_SequenceDirector", sequenceName = "ID_SEQ")
    private int id;
    private String nombre;
    private String fecha_nacimiento;
    //Es un array de peliculas que ha dirigido, cada una se sabe por su ID
    private List<Pelicula> peliculas;

    //-----------CONSTRUCTOR-----------
    public Director(String nombre, String fecha_nacimiento, List<Pelicula> peliculas) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.peliculas = peliculas;
    }//constructor

    //----------------GETTERS AND SETTERS-----------------
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
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
    //----------------------------------------
    
}
