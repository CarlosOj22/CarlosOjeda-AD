package entities;

import java.util.List;
import javax.persistence.*;

@Entity(name="peliculas")
public class Pelicula {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequencePelicula")
    @SequenceGenerator(name = "id_SequencePelicula", sequenceName = "ID_SE_PEL")
    private int id;
    private String fecha_estreno;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "id_director")
    private Director director;
    @ManyToMany
    @JoinTable(name="actores_peliculas", joinColumns={@JoinColumn(name="pelicula_id")},
    inverseJoinColumns = {@JoinColumn(name="actor_id")})
    private List<Actor> actores;

    
    //HIBERNATE PARECE SER QUE NECESITA CONSTRUCTOR VACIO
    public Pelicula(){

    }
    //---------------------CONSTRUCTOR------------
    public Pelicula(String fecha_estreno, String titulo, Director Director) {
        this.fecha_estreno = fecha_estreno;
        this.titulo = titulo;
        this.director = Director;
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
    public Director getDirector() {
        return director;
    }
    public void setDirector(Director director) {
        this.director = director;
    }
    //---------------------------------------------------

    //METODOS PARA EL ARRAYLIST DE LOS ACTORES DE LA PELI
    public void añadirActor(Actor actor){
        actores.add(actor);
        actor.getPeliculas().add(this);
    }

    public List<Actor> getActores(){
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    //TO STRING PARA MOSTRAR DATOS DE LAS PELICULAS
    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", fecha_estreno='" + fecha_estreno + '\'' +
                ", titulo='" + titulo + '\'' +
                ", director=" + director +
                ", actores=" + actores +
                '}';
    }
}

