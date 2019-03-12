# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module ModeloQytetet
  class Calle < Casilla
    def initialize(numero,titulo)
      super(numero,TipoCasilla::CALLE,titulo.precio_compra)
      @titulo=titulo
    end
    attr_accessor   :titulo
    def tengo_propietario()
        return  @titulo.tengo_propietario
    end

    def asignar_propietario(jugador)
      @titulo.set_propietario(jugador)  
      return @titulo
    end

    def pagar_alquiler()
      return @titulo.pagar_alquiler      
    end
    
    def propietario_encarcelado()
      return @titulo.propietario.encarcelado      
    end
    def soy_edificable
      return true
    end
    def to_s
      return super + "Titulo propiedad: #{@titulo}"
    end
  end
end