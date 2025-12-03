package entities;

import javax.persistence.*;

@MappedSuperclass
public class Persona {

    @Id
    @Column(name = "persona_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personaId;

    private String nombre;
    private String dni;

    public Persona() {
    }

    public Persona(long personaId, String nombre, String dni) {
    //this.personaId = personaId; //Esta linea sobra, se encarga la BBDD y es autoincremental
        this.nombre = nombre;
        this.dni = dni;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "personaId=" + personaId +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
