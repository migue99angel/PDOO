package modeloqytetet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author miguel
 */
public class Qytetet {

    private static final Qytetet instance = new Qytetet();
    private ArrayList<Sorpresa> mazo = new ArrayList<>();
    private Tablero tablero;
    private static final Dado dado = Dado.getInstance();
    private Jugador jugadorActual;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private Sorpresa cartaActual;
    private int indiceJA;
    private EstadoJuego estado;

    public static int MAX_JUGADORES = 4;
    public static int NUM_CASILLAS = 20;
    static int NUM_SORPRESAS = 10;
    static int PRECIO_LIBERTAD = 200;
    static int SALDO_SALIDA = 1000;

    private Qytetet() {
        mazo = new ArrayList<>();
    }

    public static Qytetet Instance() {
        return instance;
    }

    void actuarSiEnCasillaEdificable() {
        boolean deboPagar=jugadorActual.deboPagarAlquiler();
        boolean tengoPropietario=jugadorActual.getCasillaActual().tengoPropietario();
        if(deboPagar){
            jugadorActual.pagarAlquiler();
            if(jugadorActual.getSaldo() <=0 )
                this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        if(tengoPropietario)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        else
            setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
    }

    void actuarSiEnCasillaNoEdificable() {
        Casilla casillaActual =this.jugadorActual.getCasillaActual();
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        if(casillaActual.getTipo()==TipoCasilla.IMPUESTO)
            this.jugadorActual.pagarImpuesto();
        else{
            if(casillaActual.getTipo()==TipoCasilla.JUEZ)
                encarcelarJugador();
            else{
                if(casillaActual.getTipo()==TipoCasilla.SORPRESA){
                    this.cartaActual=this.mazo.get(0);
                    mazo.remove(cartaActual);
                    setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
                }
            }
           }
         }

    public void aplicarSorpresa(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        if(cartaActual.getTipo()==TipoSorpresa.SALIRCARCEL)
            jugadorActual.setCartaLibertad(cartaActual);
        else 
            mazo.add(cartaActual);                
        if(cartaActual.getTipo()==TipoSorpresa.PAGARCOBRAR){
            this.jugadorActual.modificarSaldo(cartaActual.getValor());
            if(jugadorActual.getSaldo()<0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        else{
            if(cartaActual.getTipo()==TipoSorpresa.IRACASILLA){
                int valor= this.cartaActual.getValor();
                boolean esCarcel=this.tablero.esCasillaCarcel(valor);
                if(esCarcel)
                    this.encarcelarJugador();
                else
                    this.mover(valor);
            }
            if(cartaActual.getTipo()==TipoSorpresa.PORCASAHOTEL){
                int cantidad=this.cartaActual.getValor();
                int numeroTotal=this.jugadorActual.cuantasCasasHotelesTengo();
                this.jugadorActual.modificarSaldo(cantidad*numeroTotal);
                if(jugadorActual.getSaldo()<0)
                   setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
            if(cartaActual.getTipo()==TipoSorpresa.PORJUGADOR){                
                for(int i=0; i<jugadores.size() ;i++){
                    Jugador jugador=jugadores.get(i);
                    if(jugadores.get(i)!=jugadorActual){
                        this.jugadorActual.modificarSaldo(this.cartaActual.getValor());
                        jugador.modificarSaldo(-this.cartaActual.getValor());
                        if(jugador.getSaldo()<0)
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        if(jugadorActual.getSaldo()<0)
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        }
                }                
            }
            if(cartaActual.getTipo()==TipoSorpresa.CONVERTIRME){
                this.jugadorActual=this.jugadorActual.convertirme(cartaActual.getValor());
                this.jugadores.set(indiceJA, jugadorActual);
            }
        }
    }

    public boolean cancelarHipoteca(int numeroCasilla) {
        Casilla casilla=tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo= casilla.getTitulo();
        boolean cancelar=jugadorActual.cancelarHipoteca(titulo);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return cancelar;
    }

    public boolean comprarTituloPropiedad() {
        boolean resultado=jugadorActual.comprarTituloPropiedad();
        if(resultado)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return resultado;
    }

    public boolean edificarCasa(int numeroCasilla) {
        Casilla casilla=this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo=casilla.getTitulo();
        boolean edificada=this.jugadorActual.edificarCasa(titulo);
        if(edificada)
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return edificada;
        
    }

    public boolean edificarHotel(int numeroCasilla) {
        boolean edificada=false;
        Casilla casilla=this.tablero.obtenerCasillaNumero(numeroCasilla);
        if(casilla.getTipo()==TipoCasilla.CALLE && casilla.getTitulo().getNumCasas()>=4){
            TituloPropiedad titulo=casilla.getTitulo();
            edificada=jugadorActual.edificarHotel(titulo);
            if(edificada)
                this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);       
        }
        return edificada;
    }

    private void encarcelarJugador() {
        if(!jugadorActual.tengoCartaLibertad()){
            Casilla carcel=tablero.getCarcel();
            this.jugadorActual.irACarcel(carcel);
            this.jugadorActual.setCasillaActual(carcel);
            this.jugadorActual.setEncarcelado(true);
            
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            Sorpresa libertad= this.jugadorActual.devolverCartaLibertad();
            mazo.add(libertad);
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }

    }

    public Sorpresa getCartaActual() {
        return cartaActual;
    }

    Dado getDado() {
        return dado;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public EstadoJuego getEstadoJuego() {
        return estado;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    ArrayList getMazo() {
        return mazo;
    }

    public int getValorDado() {
        return dado.getValor();
    }

    public void hipotecarPropiedad(int numeroCasilla) {
        Calle calle=(Calle) this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo=calle.getTitulo();
        this.jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);

    }

    public Tablero getTablero() {
        return tablero;
    }

    private void inicializarTablero() {
        tablero = new Tablero();
    }

    public void inicializarJuego(ArrayList<String> nombres) {
        this.inicializarTablero();
        this.inicializarJugadores(nombres);
        this.inicializarCartasSorpreasa();
        this.salidaJugadores();
    }

    private void inicializarJugadores(ArrayList<String> nombres) {
        int numeroJugadores = jugadores.size();
        for (int i = 0; i < nombres.size(); i++) {
            this.jugadores.add(new Jugador(nombres.get(i)));
        }

    }

    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        if(metodo==MetodoSalirCarcel.TIRANDODADO){
            int resultado=tirarDado();
            if(resultado>=5)
                jugadorActual.setEncarcelado(false);
        }
        else{
            if(metodo==MetodoSalirCarcel.PAGANDOLIBERTAD){
                jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
            }
       }
        boolean encarcelado=jugadorActual.getEncarcelado();
        if(encarcelado)
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        else
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        return encarcelado;
    }

    public void jugar() {
        int valor = this.tirarDado();
        int casilla=this.tablero.obtenerCasillaFinal(this.jugadorActual.getCasillaActual(), valor).getNumeroCasilla();
        this.mover(casilla);
    }

    private void inicializarCartasSorpreasa() {
        inicializarTablero();
        ArrayList<Sorpresa> aux = new ArrayList<>();
        
        /*aux.add(new Sorpresa("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 5000", 5000, TipoSorpresa.CONVERTIRME));
        aux.add(new Sorpresa("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 3000", 3000, TipoSorpresa.CONVERTIRME));
        aux.add(new Sorpresa("¡Martes 13!, avanza hacia la casilla nº 13", 13, TipoSorpresa.IRACASILLA));
        aux.add(new Sorpresa("¡Acabas de invocar al creador del juego, avanza hacia su calle!", 9, TipoSorpresa.IRACASILLA));
        aux.add(new Sorpresa("¡Enhorabuena!, Te toca la loteria", 500, TipoSorpresa.PAGARCOBRAR));
        aux.add(new Sorpresa("Te multan por exceso de velocidad", -500, TipoSorpresa.PAGARCOBRAR));
        aux.add(new Sorpresa("Te toca pagar la comunidad, paga 50 euros por cada casa u hotel de tu propiedad", 50, TipoSorpresa.PORCASAHOTEL));
        aux.add(new Sorpresa("Descubren un error en la factura de la luz, te compensan con 50 euros por cada propiedad", 50, TipoSorpresa.PORCASAHOTEL));
        aux.add(new Sorpresa("Te levanatas de buen humor e ivitas a un restaurante de lujo"
                + "a cada jugador, 150 euros por cabeza", -150, TipoSorpresa.PORJUGADOR));
        aux.add(new Sorpresa("Es tu cumpleaños, recibes un regalo de 150 euros de cada jugador, !Felicidades¡", 150, TipoSorpresa.PORJUGADOR));
        aux.add(new Sorpresa("El presidente del Gobierno te concede un indulto y sales de la carcel", 0, TipoSorpresa.SALIRCARCEL));
        aux.add(new Sorpresa("Descubren irregularidades en tu declaracion "
                + "de la renta, ¡Vas a la carcel!", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        */
        
        mazo.add(new Sorpresa("Te levanatas de buen humor e ivitas a un restaurante de lujo"
                + "a cada jugador, 150 euros por cabeza", -150, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Es tu cumpleaños, recibes un regalo de 150 euros de cada jugador, !Felicidades¡", 150, TipoSorpresa.PORJUGADOR));
        
        mazo.add(new Sorpresa("¡Enhorabuena!, Te toca la loteria", 500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Descubren irregularidades en tu declaracion "
                + "de la renta, ¡Vas a la carcel!", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Descubren un error en la factura de la luz, te compensan con 50 euros por cada propiedad", 50, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 5000", 5000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 3000", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("¡Martes 13!, avanza hacia la casilla nº 13", 13, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("¡Acabas de invocar al creador del juego, avanza hacia su calle!", 9, TipoSorpresa.IRACASILLA));
        
        mazo.add(new Sorpresa("Te multan por exceso de velocidad", -500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te toca pagar la comunidad, paga 50 euros por cada casa u hotel de tu propiedad", 50, TipoSorpresa.PORCASAHOTEL));

        
        mazo.add(new Sorpresa("El presidente del Gobierno te concede un indulto y sales de la carcel", 0, TipoSorpresa.SALIRCARCEL));

   
        /*
        //Mezclamos las sorpresas   
        while(!aux.isEmpty()){
            Random pos=new Random();
            int obtenido=pos.nextInt(aux.size());
            mazo.add(aux.get(obtenido));
            aux.remove(aux.get(obtenido));
        }*/
    }

    void mover(int numCasillaDestino) {
        Casilla casillainicial=this.jugadorActual.getCasillaActual();
        Casilla casillafinal=this.tablero.obtenerCasillaNumero(numCasillaDestino);
        this.jugadorActual.setCasillaActual(casillafinal);
        if(numCasillaDestino<casillainicial.getNumeroCasilla())
            this.jugadorActual.modificarSaldo(SALDO_SALIDA);
        if(casillafinal.soyEdificable())
            this.actuarSiEnCasillaEdificable();
        else
            this.actuarSiEnCasillaNoEdificable();
        
    }

    public Casilla obtenerCasillaJugadorActual() {
        return this.jugadorActual.getCasillaActual();
    }

    public ArrayList<Casilla> obtenerCasillasTablero() {
        return this.tablero.getCasillas();
    }

    public ArrayList<Integer> obtenerPropiedadesJugador() {
        ArrayList<Integer> casillas = new ArrayList<>();
        Casilla cas;
        Calle calle;
        
        for (int i=0;i<this.obtenerCasillasTablero().size();i++){
            cas=this.obtenerCasillasTablero().get(i);
            
            if(cas.soyEdificable()){
                if(cas.getTitulo().getPropietario()==this.jugadorActual){
                    calle=(Calle) cas;
                    casillas.add(this.obtenerCasillasTablero().indexOf(calle));
                }
            }
        }
        return casillas;
    }

    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca) {
        ArrayList<Integer> casillas = new ArrayList<>();
        Casilla cas;
        Calle calle;
        
        for (int i=0;i<this.obtenerCasillasTablero().size();i++){
            cas=this.obtenerCasillasTablero().get(i);
            
            if(cas.soyEdificable()){
                if(cas.getTitulo().getPropietario()==this.jugadorActual && cas.getTitulo().getHipotecada() == estadoHipoteca){
                    calle=(Calle) cas;
                    casillas.add(this.obtenerCasillasTablero().indexOf(calle));
                }
            }
        }
        return casillas;
    }

    public void obtenerRanking() {
        Collections.sort(this.jugadores);
    }

    public int obtenerSaldoJugadorActual() {
        return this.jugadorActual.getSaldo();
    }

    private void salidaJugadores() {
        Random r = new Random();
        for (int i = 0; i < jugadores.size(); i++) {
            this.jugadores.get(i).setCasillaActual(tablero.obtenerCasillaNumero(0));
        }
        this.indiceJA = r.nextInt(jugadores.size());
        this.jugadorActual=this.jugadores.get(indiceJA);
        this.setEstadoJuego(EstadoJuego.JA_PREPARADO);
    }

    private void setCartaActual(Sorpresa cartaActual) {
        this.cartaActual = cartaActual;
    }

    public void setEstadoJuego(EstadoJuego estado) {
        this.estado = estado;
    }

    public void siguienteJugador() {
        indiceJA = (indiceJA + 1) % jugadores.size();
        jugadorActual = jugadores.get(indiceJA);

        if (jugadorActual.getEncarcelado()) {
            setEstadoJuego(EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD);
        } else {
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }

    }

    private int tirarDado() {
        return this.dado.tirar();
    }

    public boolean jugadorActualEnCalleLibre() {
        Calle calle=(Calle) this.jugadorActual.getCasillaActual();
       return calle.soyEdificable() && !calle.tengoPropietario();
        
    }

    public void venderPropiedad(int numeroCasilla) {
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }

}
