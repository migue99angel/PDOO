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
public class Calle extends Casilla {
    private TituloPropiedad titulo;
    
    Calle(int numeroCasilla,TituloPropiedad titulo){
        super(numeroCasilla);
        super.setCoste(titulo.getPrecioCompra());
        this.titulo=titulo;
    }
    public void asignarPropietario(Jugador jugador){
        this.titulo.setPropietario(jugador);
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return TipoCasilla.CALLE;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
        return titulo;
    }

    public int pagarAlquiler (){
       return this.titulo.pagarAlquiler();
    }
    
    private void setTitulo(TituloPropiedad titulo){
        this.titulo=titulo;
    }
    @Override
    protected boolean soyEdificable(){
        return true;
    }
    @Override
    public boolean tengoPropietario(){
        return this.titulo.tengoPropietario();
    }
    
    @Override
    public String toString() {
        return super.toString() + "Calle{" + "titulo=" + titulo + '}';
    }
    
}
