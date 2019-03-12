/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;
import controladorqytetet.ControladorQytetet;
import controladorqytetet.OpcionMenu;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author migue
 */
public class VistaTextualQytetet {
    private static ControladorQytetet controlador = ControladorQytetet.Instance();
    private static final Scanner in =new Scanner (System.in);
    
    public ArrayList<String> obtenerNombreJugadores(){
        int numeroJugadores;
        ArrayList<String> nombres = new ArrayList<>(); 
        System.out.println("Introduzca el numero de jugadores (entre 2 y 4)");{
            do{
                numeroJugadores = in.nextInt();
            }while (numeroJugadores < 2 && numeroJugadores > 4);
        }
        for (int i=0; i < numeroJugadores ;i++){
            System.out.println("Introduzca el nombre del jugador "+i);
            String s = in.next();
            nombres.add(s);
        }
          return nombres;  
    }
    public int elegirCasilla(int opcionMenu){
        ArrayList<Integer> lista=controlador.obtenerCasillasValidas(opcionMenu);
        ArrayList<String> casillas = new ArrayList();
        if(lista.isEmpty())
            return -1;
        else{
            System.out.println("Las casillas válidas son: ");
            for(int i=0;i<lista.size();i++){
                System.out.println(lista.get(i));
                casillas.add(Integer.toString(lista.get(i)));
            }
        }
            
            return Integer.parseInt(leerValorCorrecto(casillas,false));
        
    }
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos, boolean opcion){
        String valorLeido, valorRetornado=null;
        boolean correcto=false;
        System.out.println("Selecciona una de las siguientes opciones: ");
        if(opcion){
            for(int i=0;i<valoresCorrectos.size();i++){
                System.out.println("Opción: "+valoresCorrectos.get(i) + " - " + OpcionMenu.values()[Integer.parseInt(valoresCorrectos.get(i))]);
             }
        }
        else{
             for(int i=0;i<valoresCorrectos.size();i++){
                System.out.println("Casilla :" + valoresCorrectos.get(i));
             }
        }

        do{
            valorLeido=in.next();
            for(int i=0;i<valoresCorrectos.size();i++){
                if(Integer.parseInt(valorLeido)==Integer.parseInt(valoresCorrectos.get(i))){
                    correcto=true;
                    valorRetornado=valorLeido;
                }
            }
        }while(!correcto);
        return valorRetornado;
    }
    
    public int elegirOperacion(){
        ArrayList<Integer> lista = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> operaciones=new ArrayList();
        
        for(int i=0;i<lista.size();i++){
            operaciones.add(Integer.toString(lista.get(i)));
        }
        return Integer.parseInt(this.leerValorCorrecto(operaciones,true));
    }
    
    public static void main(String[] args) {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida=0;
        boolean necesitaElegirCasilla;
        
        do{
            operacionElegida=ui.elegirOperacion();
            necesitaElegirCasilla=controlador.necesitaElegirCasilla(operacionElegida);
            if(necesitaElegirCasilla)
                casillaElegida=ui.elegirCasilla(operacionElegida);
            if(!necesitaElegirCasilla || casillaElegida>=0)
                System.out.println(controlador.realizarOperacion(operacionElegida, casillaElegida));
            
        }while(1==1);
    }
}
