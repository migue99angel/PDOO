  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.Random;

/**
 *
 * @author migue
 */
public class Dado {
    private static final Dado instance = new Dado();

    static Dado getInstance() {
        return instance;
    }
    private int valor;
    
    public Dado(){
        valor=0;
    }
    int tirar(){
        /*Random rand = new Random();
        
        int obtenido = rand.nextInt(6) + 1;
        this.valor = obtenido;
        
        return obtenido;*/
        return 2;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Dado{" + "valor=" + valor + '}';
    }
    
}
