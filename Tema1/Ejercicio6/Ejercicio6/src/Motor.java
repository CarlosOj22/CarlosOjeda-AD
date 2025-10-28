public class Motor {
    private String combustible;
    private int potencia; //Atributo de la propiedad Motor en el XML generado
    private int cilindros;
    
    public Motor(String combustible, int potencia, int cilindros) {
        this.combustible = combustible;
        this.potencia = potencia;
        this.cilindros = cilindros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getCilindros() {
        return cilindros;
    }

    public void setCilindros(int cilindros) {
        this.cilindros = cilindros;
    }
}