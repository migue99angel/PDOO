
package modeloqytetet;

/**
 *
 * @author miguel
 */
public abstract class Casilla {
    private int numeroCasilla;
    private int coste;

    protected Casilla(int numeroCasilla){
        this.numeroCasilla=numeroCasilla;
    }
    
    int getNumeroCasilla() {
        return numeroCasilla;
    }

    int getCoste() {
        return coste;
    }
    
    protected abstract boolean soyEdificable();
    
    protected abstract boolean tengoPropietario();
    
    protected abstract TituloPropiedad getTitulo();
    
    protected abstract TipoCasilla getTipo();
    
    void setCoste(int coste) {
        this.coste = coste;
    }

    @Override
    public String toString() {
        return "Casilla{" + "numeroCasilla=" + numeroCasilla + ", coste=" + coste + '}';
    }
    
}


