#encoding: utf-8
module ModeloQytetet
  class Casilla
    def initialize (numeroCasilla,tipo,precio)
      @numeroCasilla=numeroCasilla
      @tipo=tipo
      @precio=precio
    end  
      
      attr_reader  :precio, :numeroCasilla, :tipo
      
    def to_s
      " Numero casilla: #{@numeroCasilla} \n Tipo casilla: #{@tipo} \n Precio: #{@precio} \n "
    end

    
    def soy_edificable
      return  @tipo == TipoCasilla::CALLE
    end
  end
end