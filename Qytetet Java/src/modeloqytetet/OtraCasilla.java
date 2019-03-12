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
public class OtraCasilla extends Casilla {
    private TipoCasilla tipo;
    
    OtraCasilla(int numeroCasilla,TipoCasilla tipo){
        super(numeroCasilla);
        this.tipo=tipo;
        super.setCoste(0);
    }
    
    protected TipoCasilla getTipo(){
        return tipo;
    }
    @Override
    protected boolean soyEdificable(){
        return false;
    }
    @Override
    protected TituloPropiedad getTitulo(){
        return null;
    }
    @Override
    protected boolean tengoPropietario(){
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString()+"OtraCasilla{" + "tipo=" + tipo + '}';
    }
}
