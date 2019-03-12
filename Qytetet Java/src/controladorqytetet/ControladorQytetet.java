/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;
import java.util.ArrayList;
import modeloqytetet.EstadoJuego;
import modeloqytetet.Qytetet;
import modeloqytetet.MetodoSalirCarcel;
/**
 *
 * @author migue
 */
public class ControladorQytetet {
    private ArrayList<String> nombreJugadores;
    private static Qytetet modelo = Qytetet.Instance();
    private static final ControladorQytetet instance = new ControladorQytetet();
    private EstadoJuego estado;
    private MetodoSalirCarcel metodo;
    
    private ControladorQytetet() {
        nombreJugadores = new ArrayList<>();
        this.estado=null;
    }
    
    public static ControladorQytetet Instance() {
        return instance;
    }
    
    public void setNombreJugadores(ArrayList<String> nombres){
        this.nombreJugadores=nombres;
        modelo.inicializarJuego(nombres);
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList<Integer> operacionesValidas=new ArrayList<>();
        this.estado= modelo.getEstadoJuego();
        
        if(nombreJugadores.isEmpty()){
            operacionesValidas.add(OpcionMenu.INICIARJUEGO.ordinal());
            return operacionesValidas;
        }
        
        switch(estado){
            case JA_CONSORPRESA:
                operacionesValidas.add(OpcionMenu.APLICARSORPRESA.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                break;
            case JA_PREPARADO:
                operacionesValidas.add(OpcionMenu.JUGAR.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                break;
            case JA_PUEDEGESTIONAR:    
                operacionesValidas.add(OpcionMenu.PASATURNO.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                if(!modelo.obtenerPropiedadesJugador().isEmpty()){
                    operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    if(!modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true).isEmpty())
                        operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                }
                break;
            case JA_PUEDECOMPRAROGESTIONAR:
                operacionesValidas.add(OpcionMenu.PASATURNO.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                operacionesValidas.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                if(!modelo.obtenerPropiedadesJugador().isEmpty()){
                    operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal()); 
                    operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    if(!modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true).isEmpty())
                        operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                }
                
                break;
            case ALGUNJUGADORENBANCARROTA:
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                break;
            case JA_ENCARCELADO:
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                operacionesValidas.add(OpcionMenu.PASATURNO.ordinal());
                break;
            case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
                operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
        }
        return operacionesValidas;
        
    }

    public boolean necesitaElegirCasilla(int opcionMenu){
        boolean resultado=false;
        if(opcionMenu==OpcionMenu.HIPOTECARPROPIEDAD.ordinal()||opcionMenu==OpcionMenu.CANCELARHIPOTECA.ordinal()
                || opcionMenu==OpcionMenu.EDIFICARCASA.ordinal() || opcionMenu==OpcionMenu.EDIFICARHOTEL.ordinal()
                || opcionMenu==OpcionMenu.VENDERPROPIEDAD.ordinal())
            resultado=true;
        return resultado;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu){
        ArrayList<Integer> validas = new ArrayList<>();
        if(opcionMenu == OpcionMenu.EDIFICARCASA.ordinal() || opcionMenu == OpcionMenu.EDIFICARHOTEL.ordinal() 
                || opcionMenu == OpcionMenu.VENDERPROPIEDAD.ordinal()){
            validas=modelo.obtenerPropiedadesJugador();
        }
        else{
            if(opcionMenu==OpcionMenu.CANCELARHIPOTECA.ordinal()){
                validas=modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
            }
            else{
                if(opcionMenu == OpcionMenu.HIPOTECARPROPIEDAD.ordinal())
                    validas=modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
            }
        }
        return validas;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida){
        String devolver=null;
        OpcionMenu opcion=OpcionMenu.values()[opcionElegida];
        switch(opcion){
            case INICIARJUEGO:
                modelo.inicializarJuego(nombreJugadores);
                devolver="Juego iniciado";
                break;
            case JUGAR:
                modelo.jugar();
                devolver="El valor que ha salido en el dado es "+modelo.getValorDado()+". Has caido en la casilla"+modelo.obtenerCasillaJugadorActual();
                break;
            case APLICARSORPRESA:
                modelo.aplicarSorpresa();
                devolver="SORPRESA: "+modelo.getCartaActual();
                break;
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                boolean salir=false;
                salir=modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                if(!salir)
                    devolver="Pagas la fiaza. ¡Sales de la cárcel!";
                else
                    devolver="No te puedes permitir pagar la fianza, sigues en la cárcel";
                break;
            case INTENTARSALIRCARCELTIRANDODADO:
                boolean salir_dado=false;
                salir_dado=modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
               
                if(!salir_dado)
                    devolver="Has sacado un "+modelo.getValorDado()+". Es tu día de suerte, ¡Sales de la cárcel!";
                else
                    devolver="Has sacado un "+modelo.getValorDado()+".Otra vez será, pero de momento te quedas en la cárcel";
                break;
            case COMPRARTITULOPROPIEDAD:
                boolean comprado=modelo.comprarTituloPropiedad();
                if(comprado)
                    devolver="¡Enhorabuena! acabas de adquirir la propiedad de la calle "+modelo.obtenerCasillaJugadorActual();
                else
                    devolver="No es posible comprar la propiedad de la casilla actual...";
                break;
            case HIPOTECARPROPIEDAD:
                modelo.hipotecarPropiedad(casillaElegida);
                devolver="Se ha hipotecado la propiedad de la casilla "+casillaElegida;
                break;
            case CANCELARHIPOTECA:
                boolean cancelada=modelo.cancelarHipoteca(casillaElegida);
                if(cancelada)
                    devolver="Se ha cancelado con éxito la casilla "+casillaElegida;
                else
                    devolver="No ha sido posible cancelar la hipoteca de la casilla "+casillaElegida+"...";
                break;
            case EDIFICARCASA:
                boolean casa_edificada=modelo.edificarCasa(casillaElegida);
                if(casa_edificada)
                    devolver="Has construido una casa en la casilla "+casillaElegida;
                else
                    devolver="No ha sido posible edificar la casa en la casilla "+casillaElegida+"...";
                break;
            case EDIFICARHOTEL:   
                boolean hotel_edificado=modelo.edificarHotel(casillaElegida);
                if(hotel_edificado)
                    devolver="Has construido un hotel en la casilla "+casillaElegida;
                else
                    devolver="No ha sido posible edificar el hotel en la casilla "+casillaElegida+"...";
                break;
            case VENDERPROPIEDAD:
                modelo.venderPropiedad(casillaElegida);
                devolver="Has vendido la propiedad de la casilla "+casillaElegida;
                break;
            case PASATURNO:
                modelo.siguienteJugador();
                devolver="El jugador actual termina su turno";
                break;
                
            case TERMINARJUEGO:
                System.out.println("El ranking es: ");
                modelo.obtenerRanking();
                System.out.println(modelo.getJugadores());
                System.out.println("Fin del juego");
                System.exit(0);
                break;
            case MOSTRARJUGADORACTUAL:
                System.out.println(modelo.getJugadorActual());
                break;
            case MOSTRARJUGADORES:
                System.out.println(modelo.getJugadores());
                break;
            case MOSTRARTABLERO:
                System.out.println(modelo.getTablero());
                break;
            default:
                break;
        }
        return devolver;
    }

}
