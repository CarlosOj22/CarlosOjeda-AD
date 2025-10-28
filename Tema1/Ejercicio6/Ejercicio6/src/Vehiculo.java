import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vehiculo")
public class Vehiculo {
    private String marca;
    private String modelo;
    private Motor motor;
    private Ruedas ruedas;

    public Vehiculo(String marca, String modelo, Motor motor, Ruedas ruedas) {
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
        this.ruedas = ruedas;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public Motor getMotor() {
        return motor;
    }
    public void setMotor(Motor motor) {
        this.motor = motor;
    }
    public Ruedas getRuedas() {
        return ruedas;
    }
    public void setRuedas(Ruedas ruedas) {
        this.ruedas = ruedas;
    }
}
