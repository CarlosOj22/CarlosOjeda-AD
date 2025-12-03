package entities;

import javax.persistence.*;

@Entity(name="centros")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nombre",
name = "nombreUniqueConstraint"))
public class Centro {

    @Id
    @Column(name = "centro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long centroId;

    private String nombre;

    public Centro() {
    }

    public Centro(String nombre) {
        this.nombre = nombre;
    }

    public long getCentroId() {
        return centroId;
    }

    public void setCentroId(long centroId) {
        this.centroId = centroId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return "Centro{" +
                "centroId=" + getCentroId() +
                ", nombre='" + getNombre() + '\'' +
                '}';
    }
}
