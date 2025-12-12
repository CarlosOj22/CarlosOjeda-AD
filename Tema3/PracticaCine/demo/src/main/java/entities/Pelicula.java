package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity(name="peliculas")
public class Pelicula {
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_SequencePelicula", sequenceName = "ID_SEQ")
    
    private int id;
    private String fecha_estreno;
    private String titulo;
    private int idDirector;
    @ManyToMany(cascade = CascadeType.DETACH,mappedBy = "peliculas")
    private List<Actor> actores;

    //---------------------CONSTRUCTOR------------
    public Pelicula(String fecha_estreno, String titulo, int idDirector) {
        this.fecha_estreno = fecha_estreno;
        this.titulo = titulo;
        this.idDirector = idDirector;
    }//constructor


    //---------------GETTERS AND SETTERS--------------------
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFecha_estreno() {
        return fecha_estreno;
    }
    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getIdDirector() {
        return idDirector;
    }
    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }
    //---------------------------------------------------
}
