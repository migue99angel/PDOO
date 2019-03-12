/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;
import java.util.ArrayList;
/**
 *
 * @author migue
 */
public class Jugador implements Comparable{
    private boolean encarcelado;
    private String nombre;
    private int saldo;
    private Sorpresa cartaLibertad;
    private Casilla casillaActual;
    private ArrayList<TituloPropiedad> propiedades = new ArrayList<>();    

    protected Jugador(){          
        encarcelado = false;
        nombre = null;
        saldo = 7500;
    }
    
    protected Jugador(String nombre){
        this.nombre=nombre;
        encarcelado=false;
        saldo=7500;
    }
    protected Jugador(Jugador j){
        this.cartaLibertad=j.cartaLibertad;
        this.casillaActual=j.casillaActual;
        this.encarcelado= j.encarcelado;
        this.nombre= j.nombre;
        this.propiedades=j.propiedades;
        this.saldo=j.saldo;
    }

    boolean getEncarcelado() {
        return encarcelado;
    }

    String getNombre() {
        return nombre;
    }
    ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }

    public int getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Jugador{" + "encarcelado=" + encarcelado + ", nombre=" + nombre + 
                ", saldo=" + saldo + ", capital=" + this.obtenerCapital() + ", Carta Libertad: "+cartaLibertad+ "}";
    }

    boolean cancelarHipoteca(TituloPropiedad titulo){
        boolean cancelar=false;
        int costeCancelar=titulo.calcularCosteCancelar();
        if(this.getSaldo()>costeCancelar){
            this.modificarSaldo(-costeCancelar);
            titulo.cancelarHipoteca();
            cancelar=true;
        }
        return cancelar;
    }
    
    boolean comprarTituloPropiedad(){
       boolean compra=false;
       int costeCompra=casillaActual.getCoste();
       
       if(costeCompra<saldo){
           compra=true;
           Calle calle = (Calle) this.casillaActual;
           TituloPropiedad titulo = calle.getTitulo();
           titulo.setPropietario(this);
           propiedades.add(titulo);
           this.modificarSaldo(-costeCompra);          
       }
       return compra;
    }
    int cuantasCasasHotelesTengo(){
        int casashoteles=0;
        for(int i=0;i<propiedades.size();i++){
            casashoteles+=propiedades.get(i).getNumCasas()+propiedades.get(i).getNumHoteles();
        }
        return casashoteles;
    }
    boolean deboPagarAlquiler(){
        Calle calle = (Calle) this.casillaActual;
        TituloPropiedad titulo = calle.getTitulo();
        boolean miPropiedad = this.esDeMiPropiedad(titulo);
        boolean tienePropietario = false;
        boolean encarcelado = false;
        boolean estaHipotecada = false;
       
        if(!miPropiedad)
            tienePropietario = titulo.tengoPropietario();        
        if(!miPropiedad && tienePropietario)
            encarcelado = titulo.propietarioEncarcelado();
        if(!miPropiedad && tienePropietario && !encarcelado)
            estaHipotecada = titulo.getHipotecada();
        
        return !miPropiedad && tienePropietario && !encarcelado && !estaHipotecada;
    }
    Sorpresa devolverCartaLibertad(){
       Sorpresa carta = new Sorpresa(cartaLibertad.getTexto(),cartaLibertad.getValor(),cartaLibertad.getTipo());
       cartaLibertad=null;
        return carta;
    }
    boolean edificarCasa(TituloPropiedad titulo){
        int numCasas=titulo.getNumCasas();
        boolean edificada=false;
        if(this.puedoEdificarCasa(titulo)){
            int costeEdificarCasa= titulo.getPrecioEdificar();
            if(tengoSaldo(costeEdificarCasa)){
                titulo.edificarCasa();
                this.modificarSaldo(-costeEdificarCasa);
                edificada=true;
            }
        }
        return edificada;
    }
    boolean edificarHotel(TituloPropiedad titulo){
        int numHoteles=titulo.getNumHoteles();
        boolean edificada=false;
        if(this.puedoEdificarHotel(titulo)){
            
            int costeEdificarHotel= titulo.getPrecioEdificar();
            if(tengoSaldo(costeEdificarHotel)){
                titulo.edificarHotel();
                this.modificarSaldo(-costeEdificarHotel);
                edificada=true;
            }
        }
        return edificada;
    }
    private void eliminarDeMisPropiedades(TituloPropiedad titulo){
        this.propiedades.remove(titulo);
        titulo.setPropietario(null);
        this.modificarSaldo(titulo.calcularPrecioVenta());
    }
    private boolean esDeMiPropiedad(TituloPropiedad titulo){
        boolean resultado=false;
        if(propiedades.contains(titulo))
            resultado=true;
        return resultado;
    }
    boolean estoyEnCalleLibre(){
        return this.estoyEncalleLibre();
    }
 
    Casilla getCasillaActual(){
        return casillaActual;
    }
    Sorpresa getCartaLibertad(){
        return cartaLibertad;
    }

    private boolean estoyEncalleLibre(){
        boolean libre=true;
        Calle calle=(Calle) this.casillaActual;
        if(calle.tengoPropietario())
            libre=false;
        return libre;
    }
    void hipotecarPropiedad(TituloPropiedad titulo){
        int costeHipoteca=titulo.hipotecar();
        this.modificarSaldo(costeHipoteca);
    }
    void irACarcel(Casilla casilla){
        setEncarcelado(true);
        setCasillaActual(casilla);
    }
    int modificarSaldo(int cantidad){
        return this.saldo+=cantidad;
    }
    int obtenerCapital(){
        int capital=0;
        for (int i=0;i<propiedades.size();i++){
            capital+=propiedades.get(i).getPrecioCompra() + 
                    propiedades.get(i).getPrecioEdificar()*propiedades.get(i).getNumCasas()+
                    propiedades.get(i).getPrecioEdificar()*propiedades.get(i).getNumHoteles();
            if(propiedades.get(i).getHipotecada())
                capital-=propiedades.get(i).getHipotecaBase(); 
        }
        
        return capital+saldo;
    }
    ArrayList<TituloPropiedad> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean hipotecada) {
        ArrayList<TituloPropiedad> resultado = new ArrayList<>();
        for(int i=0;i<propiedades.size();i++){
            if(propiedades.get(i).getHipotecada()==hipotecada)
                resultado.add(propiedades.get(i));                
        }
        return resultado;
    }
    void pagarAlquiler(){
        Calle calle = (Calle) this.casillaActual;
        int costeAlquiler=(int) calle.pagarAlquiler();
        this.modificarSaldo((int) -costeAlquiler);

    }
    protected void pagarImpuesto(){
        this.saldo-=this.getCasillaActual().getCoste();
    }
    void pagarLibertad(int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        
        if(tengoSaldo){
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
            
    }
    void setCartaLibertad(Sorpresa carta){
        if(carta.getTipo()==TipoSorpresa.SALIRCARCEL)
            cartaLibertad=carta;
        else
            throw new Error("Tipo de carta diferente a SALIRCARCEL");
    }
    void setCasillaActual(Casilla casilla){
        this.casillaActual=casilla;
    }
    void setEncarcelado(boolean encarcelado){
        this.encarcelado=encarcelado;
    }
    boolean tengoCartaLibertad() {
        return this.cartaLibertad != null;
    }
    protected boolean tengoSaldo(int cantidad){
        return this.saldo>cantidad;
    }
    void venderPropiedad(Casilla casilla){
        Calle calle=(Calle) casilla;
        this.eliminarDeMisPropiedades(calle.getTitulo());
    }
    
    protected Especulador convertirme(int fianza){
        Especulador convertido = new Especulador(this, fianza);
        return convertido;
    }
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        return this.tengoSaldo(titulo.getPrecioEdificar()) && !titulo.getHipotecada();
    }
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        return this.tengoSaldo(titulo.getPrecioEdificar()) && titulo.getNumHoteles()<4 && !titulo.getHipotecada();
    }
    protected boolean deboIrACarcel(){
        return !this.tengoCartaLibertad();
    }
    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - this.obtenerCapital();
    }    
}
