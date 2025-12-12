package entities;

import java.net.Socket;
import java.util.List;

import javax.persistence.*;

@Entity(name="alumnos")
public class Alumno extends Persona {

    Socket socket; //AÑADIDO PARA QUE ME CUENTE COMO CAMBIO PARA GIT

    @OneToOne(cascade = CascadeType.ALL) //Esto representa la relacion 1:M (Un alumno pertenece a un solo centro
    //(1:1) pero un centro puede ser de muchos alumnos 1:M)) del modelo relacional
    @JoinColumn(name = "centro_id",referencedColumnName = "centro_id",
        foreignKey = @ForeignKey(name = "FK_alumno_centro"))
    private Centro centro;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Modulo> modulos;

    private int edad;

    public Alumno() {
    }

    public Alumno(long personaId, String nombre, String dni, Centro centro, int edad) {
        super(personaId, nombre, dni);
        this.centro = centro;
        this.edad = edad;
    }//Construcor 1 sin modulos

    public Alumno(long personaId, String nombre, String dni, Centro centro, List<Modulo> modulos, int edad) {
        super(personaId, nombre, dni);
        this.centro = centro;
        this.modulos=modulos;
        this.edad = edad;
    }//Constructor 2 con modulos

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

        public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }
    
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "personaId=" + getPersonaId() +
                ", nombre='" + getNombre() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", centro=" + (centro != null ? centro.getCentroId() : "null") +
                ", edad=" + edad +
                '}';
    }
}
