
//package modeloqytetet;
//import java.util.ArrayList;
//import java.util.Scanner;
//
///**
// *
// * @author miguel
// */
//public class PruebaQytetet {
//    private static Qytetet juego = Qytetet.Instance();
//    static Tablero tablero = new Tablero();
//    private static final Scanner in =new Scanner (System.in);
//    /**
//     * @param args the command line arguments
//     */
//    private static ArrayList<Sorpresa> SorpresasPositivas (ArrayList<Sorpresa> mazo){
//        ArrayList<Sorpresa> positivo = new ArrayList<>();
//        for (Sorpresa s : mazo){
//            if(s.getValor()>0)
//                positivo.add(s);
//        }
//        return positivo;
//    }
//    
//    private static ArrayList<Sorpresa> SorpresasCasillas (ArrayList<Sorpresa> mazo){
//        ArrayList<Sorpresa> casillas = new ArrayList<>();
//        for (Sorpresa s : mazo){
//            if(s.getTipo()==TipoSorpresa.IRACASILLA)
//                casillas.add(s);
//        }
//        return casillas;
//    }
//    
//    private static ArrayList<Sorpresa> SorpresasTipo (ArrayList<Sorpresa> mazo, TipoSorpresa tipo){
//        ArrayList<Sorpresa> tipoSorpresa = new ArrayList<>();
//        for (Sorpresa s : mazo){
//            if(s.getTipo()==tipo)
//                tipoSorpresa.add(s);
//        }
//        return tipoSorpresa;
//    }
//    
//    private static ArrayList<String>  getNombreJugadores(){
//        int numeroJugadores;
//        ArrayList<String> nombres = new ArrayList<>(); 
//        System.out.println("Introduzca el numero de jugadores (entre 2 y 4)");{
//            do{
//                numeroJugadores = in.nextInt();
//            }while (numeroJugadores < 2 && numeroJugadores > 4);
//        }
//        for (int i=0; i < numeroJugadores ;i++){
//            System.out.println("Introduzca el nombre del jugador "+i);
//            String s = in.next();
//            nombres.add(s);
//        }
//          return nombres;  
//    }
//    
//    public static void main(String[] args) {
//        ArrayList <String> nombres = new ArrayList<String>();
//        nombres=getNombreJugadores();
//        juego.inicializarJuego(nombres);
//        juego.getMazo().toString();
//               
//        for(int i=0;i<9;i++){
//            
//            System.out.println(juego.getMazo().get(i));
//        }
//        tablero.getCasillas().toString();
//        for(int i=0;i<20;i++){
//            System.out.println(tablero.getCasillas().get(i));
//            
//            }               
//        System.out.println("Los jugadores  son: ");
//        System.out.println(juego.getJugadores().toString()); 
//
//        System.out.println("La unica instacia de Qytetet:");
//        System.out.println(juego);
//       //Vamos a probar lo implementado en la práctica 3
//        juego.mover(3);
//        juego.getJugadorActual().comprarTituloPropiedad();
//        juego.siguienteJugador();
//        juego.mover(3);
//        System.out.println(juego.getJugadorActual().getCasillaActual());
//        System.out.println(juego.getJugadorActual().getSaldo());
//        // Vamos a caer en todas las Sorpresas
//       juego.jugar();
//       juego.mover(2);
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.aplicarSorpresa();
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.mover(7);
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.aplicarSorpresa();   
//       juego.mover(12);
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.aplicarSorpresa();
//       juego.siguienteJugador();
//       juego.hipotecarPropiedad(3);
//       juego.cancelarHipoteca(3);
//       juego.venderPropiedad(3);
//       juego.mover(4);
//       juego.comprarTituloPropiedad();
//       juego.edificarCasa(4);
//       juego.edificarCasa(4);
//       juego.edificarCasa(4);
//       juego.edificarCasa(4);
//       juego.edificarHotel(4);
//       System.out.println(juego.getJugadorActual().getCasillaActual().getTitulo().getNumCasas());
//       System.out.println(juego.getJugadorActual().getCasillaActual().getTitulo().getNumHoteles());
//       juego.hipotecarPropiedad(juego.getJugadorActual().getCasillaActual().getNumeroCasilla());
//       //juego.siguienteJugador();
//       //juego.getJugadorActual().irACarcel(juego.obtenerCasillasTablero().get(5));
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       /*if(juego.getJugadorActual().tengoCartaLibertad()){
//           juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
//       }       
//       else{
//           boolean salirDeLaCarcel = juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);           
//           if(salirDeLaCarcel)
//               System.out.println("Consigue salir de la cárcel");     
//           else
//               System.out.println("Al palo");       
//        }
//       juego.jugar();
//       System.out.println(juego.getJugadores());
//       juego.getJugadorActual().modificarSaldo(-300);
//       juego.siguienteJugador();
//       juego.getJugadorActual().modificarSaldo(300);
//       juego.obtenerRanking();
//       System.out.println(juego.getJugadores());
//       System.out.println("Vamos a probar la practica 4");
//       juego.mover(2);
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.aplicarSorpresa();
//       juego.siguienteJugador();
//       juego.mover(7);
//       System.out.println(juego.getJugadorActual().getCasillaActual());
//       juego.aplicarSorpresa();
//       System.out.println(juego.getJugadores());*/
//        }
//    }
//
