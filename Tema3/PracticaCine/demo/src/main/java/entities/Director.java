package entities;

import java.util.List;
import javax.persistence.*;

@Entity(name="directores")
public class Director {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceDirector")
    @SequenceGenerator(name = "id_SequenceDirector", sequenceName = "ID_SEQ_DIR")
    private int id;
    private String nombre;
    private String fecha_nacimiento;
    //Es un array de peliculas que ha dirigido, cada una se sabe por su ID
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "director")
    private List<Pelicula> peliculas;

    //-------CONSTRUCTOR VACIO---------
    public Director(){

    }
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
    //METODO PARA AÑADIR UNA SOLA PELICULA NUEVA A EL ARRAY
    public void añadirPelicula(Pelicula pelicula){
        peliculas.add(pelicula);
        //CREO QUE LA LINEA DEABAJO NO HACE FALTA
        pelicula.setDirector(this);
    }

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento == null ? "--/--/----" : fecha_nacimiento + '\'' +
                ", peliculas=" + peliculas +
                '}';
    }
}

