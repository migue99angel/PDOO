
package modeloqytetet;

/**
 *
 * @author miguel
 */
public class TituloPropiedad {
    private String calle;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizado;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    private Jugador propietario;
    
    public TituloPropiedad( String calle,int precioCalle, int alquilerBase, float factorRevalorizado, int hipotecaBase, int precioEdificar ){
        this.calle=calle;
        this.hipotecada=false;
        this.precioCompra=precioCalle;
        this.alquilerBase=alquilerBase;
        this.factorRevalorizado=factorRevalorizado;
        this.hipotecaBase=hipotecaBase;
        this.precioEdificar=precioEdificar;
        this.numCasas=0;
        this.numHoteles=0;
        this.propietario=null;
    }


    int calcularCosteCancelar(){
        return (int) (this.calcularCosteHipotecar() + this.calcularCosteHipotecar()*0.1);
    }
    int calcularCosteHipotecar(){
        return (int) (this.hipotecaBase+this.numCasas*0.5*this.hipotecaBase+this.numHoteles*this.hipotecaBase);
    }
    double calcularImporteAlquiler(){
        double costeAlquiler;
        costeAlquiler = this.alquilerBase + this.numHoteles*0.5 + this.numCasas*2;
        return costeAlquiler;
    }
    int calcularPrecioVenta(){
        return (int) (this.precioCompra + (this.numCasas+this.numHoteles)*this.factorRevalorizado*this.precioEdificar);
    }
    void cancelarHipoteca(){
        this.hipotecada=false;
    }
    void edificarCasa(){
        this.numCasas++;
    }
    void edificarHotel(){
        this.numHoteles+=1;
        this.numCasas=0;
    }
        
    String getCalle() {
        return calle;
    }

    boolean getHipotecada() {
        return hipotecada;
    }

    int getPrecioCompra() {
        return precioCompra;
    }

    int getAlquilerBase() {
        return alquilerBase;
    }

    float getFactorRevalorizado() {
        return factorRevalorizado;
    }

    int getHipotecaBase() {
        return hipotecaBase;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }

    int getNumHoteles() {
        return numHoteles;
    }

    int getNumCasas() {
        return numCasas;
    }
    Jugador getPropietario(){
        return propietario;
    }
    
    int hipotecar(){
        this.setHipoteca(true);
        return this.calcularCosteHipotecar();
    }
    int pagarAlquiler(){
        int costeAlquiler=(int) calcularImporteAlquiler();
        this.propietario.modificarSaldo(costeAlquiler);
        return costeAlquiler;
    }
    boolean propietarioEncarcelado(){
        return this.propietario.getEncarcelado();
    }
    
    void setHipoteca(boolean hipotecada){
        this.hipotecada=hipotecada;
    }
    void setPropietario(Jugador propietario){
        this.propietario=propietario;
    }
    boolean tengoPropietario(){
        boolean propietario=false;
        if(this.propietario!=null)
            propietario=true;
        return propietario;
    }
    
    @Override
    public String toString() {
        return "TituloPropiedad{" + "calle=" + calle + ", hipotecada=" + hipotecada + ", precioCompra=" + precioCompra + ", alquilerBase=" + alquilerBase + ", factorRevalorizado=" + factorRevalorizado + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + '}';
    }
    
}
