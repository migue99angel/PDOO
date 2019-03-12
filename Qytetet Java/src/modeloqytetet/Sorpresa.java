
package modeloqytetet;

/**
 *
 * @author miguel
 */
public class Sorpresa {
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    
    public Sorpresa(String texto,int valor, TipoSorpresa tipo){
        this.texto=texto;
        this.tipo=tipo;
        this.valor=valor;
    }
    
    public String getTexto(){
        return texto;
    }
    
    public TipoSorpresa getTipo(){
        return tipo;
    }
    
    public int getValor(){
        return valor;
    }
    
    //Este metodo devuelve un string con el estado del objeto correspondiente
    public String toString(){
        return "Sorpresa{"+"texto="+texto + ",valor="+
                Integer.toString(valor) + ",tipo="+tipo + "}";
    }
           
}
