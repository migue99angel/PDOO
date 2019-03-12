/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author migue
 */
public class Especulador extends Jugador {
    
    private int fianza;
    protected Especulador(Jugador jugador, int fianza){
        super(jugador);
        this.fianza=fianza;
    }

    @Override
    protected void pagarImpuesto(){
        modificarSaldo(-getCasillaActual().getCoste()/2);
    }
    @Override
    protected boolean deboIrACarcel(){
        return super.deboIrACarcel() && !this.pagarFianza();
    }
    
    @Override
    protected Especulador convertirme(int fianza){
        return this;
    }
    
    private boolean pagarFianza(){
        boolean pagoRealizado=false;
        if(this.tengoSaldo(fianza)){
            pagoRealizado=true;
            this.modificarSaldo(-fianza);
        }
        return pagoRealizado;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        return this.tengoSaldo(titulo.getPrecioEdificar()) && titulo.getNumCasas()<8 && !titulo.getHipotecada();
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        return this.tengoSaldo(titulo.getPrecioEdificar()) && titulo.getNumHoteles()< 8 && !titulo.getHipotecada();
    }

    @Override
    public String toString() {
        return super.toString()+"Especulador{" + "fianza=" + fianza + '}';
    }
    
    
}
