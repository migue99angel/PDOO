
package modeloqytetet;
import java.util.ArrayList;
/**
 *
 * @author miguel
 */
public class Tablero {
    private ArrayList<Casilla> casillas ;
    private Casilla carcel;
    
    public Tablero(){
        inicializar();
        carcel=casillas.get(5);

    }
    
    public Tablero(ArrayList<Casilla> casillas, Casilla carcel){
        this.casillas=casillas;
        this.carcel=carcel;
    }
    private void inicializar(){
        casillas = new ArrayList<>();
        int contador=0;
        //Inicializamos primero los títulos de propiedad en un vector auxiliar
        ArrayList<TituloPropiedad> titulos = new ArrayList<>();
        titulos.add(new TituloPropiedad ("Calle Morgana",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Marcos Heredia",300,75,2,125,125));
        titulos.add(new TituloPropiedad ("Calle Nueva",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Capo",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Maxima",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Migue99angel",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Estufu",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Ancha",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Smite",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle Ela",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle David Broncano",200,50,7,100,100));
        titulos.add(new TituloPropiedad ("Calle calle",200,50,7,100,100));
        
      //Ahora inicializamos el vector casillas
      
        casillas.add(new OtraCasilla (0,TipoCasilla.SALIDA));
        casillas.add(new Calle(1,titulos.get(contador++)));
        casillas.add(new OtraCasilla(2,TipoCasilla.SORPRESA));
        casillas.add(new Calle(3,titulos.get(contador++)));
        casillas.add(new Calle(4,titulos.get(contador++)));
        casillas.add(new OtraCasilla(5,TipoCasilla.CARCEL));
        casillas.add(new Calle(6,titulos.get(contador++)));
        casillas.add(new OtraCasilla(7,TipoCasilla.SORPRESA));
        casillas.add(new Calle(8,titulos.get(contador++)));
        casillas.add(new Calle(9,titulos.get(contador++)));
        casillas.add(new OtraCasilla(10,TipoCasilla.PARKING));
        casillas.add(new Calle(11,titulos.get(contador++)));
        casillas.add(new OtraCasilla(12,TipoCasilla.SORPRESA));
        casillas.add(new Calle(13,titulos.get(contador++)));
        casillas.add(new Calle(14,titulos.get(contador++)));
        casillas.add(new OtraCasilla(15,TipoCasilla.JUEZ));
        casillas.add(new Calle(16,titulos.get(contador++)));
        casillas.add(new OtraCasilla(17,TipoCasilla.IMPUESTO));
        casillas.add(new Calle(18,titulos.get(contador++)));
        casillas.add(new Calle(19,titulos.get(contador++)));
            
    }
    
    ArrayList<Casilla> getCasillas() {
        return casillas;
    }

        Casilla getCarcel() {
            return carcel;
    }
    
    boolean esCasillaCarcel(int numeroCasilla){
        boolean coinciden=false;
        if(numeroCasilla==carcel.getNumeroCasilla())
            coinciden = true;
        return coinciden;
    }
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){        
        return casillas.get((casilla.getNumeroCasilla()+desplazamiento)%20);
    }
    Casilla obtenerCasillaNumero(int numeroCasilla){
        return casillas.get(numeroCasilla);
    }

    @Override
    public String toString() {
        return "Tablero{" + "casillas=" + casillas + ", carcel=" + carcel + '}';
    }
    
    
    
}
