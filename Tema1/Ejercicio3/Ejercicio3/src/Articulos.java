//Carlos Ojeda Corona
/*Clase Articulos que contiene metodos necesarios para la administracion de sus propiedades y la
muestra de informacio */
import java.io.Serializable;

public class Articulos implements Serializable {
    
    private int codigo;
    private String nombre;
    private double precio;
    private int stock;

    public Articulos(int codigo, String nombre, double precio, int stock){
        this.codigo=codigo;
        this.nombre=nombre;
        this.precio=precio;
        this.stock=stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String toString(){
        return "-----------------------\n" +
        "Articulo: " + getNombre()+ "\n"+ 
        "Codigo: " + getCodigo()+ "\n"+ 
        "Precio: " + getPrecio()+ " Euros\n"+ 
        "Stock: " + getStock()+ "\n"
         ;
    }//To string

}
