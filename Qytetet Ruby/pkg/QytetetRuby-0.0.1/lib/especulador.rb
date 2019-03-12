#encoding: utf-8

module ModeloQytetet
class Jugador
  
end
class Especulador < Jugador
  attr_accessor :fianza
  def self.copia(jugador,fianza)
#    @fianza
    salida=super(jugador)
    salida.fianza=fianza
    
    salida
  end
 
  
  def pagar_fianza
    pago_realizado=false
    if tengo_saldo(@fianza)
      modificar_saldo(-@fianza)
      pago_realizado=true
    end
    return pago_realizado
  end
  

  
  def pagar_impuesto
    modificar_saldo(-casilla_actual.precio/2)
  end
  
  def debo_ir_a_carcel
    carcel=false
    if !pagar_fianza && super
      carcel = true
    end
    return carcel
  end
  
  def convertirme(fianza)
    return self
  end
  
  def puedo_edificar_casa(titulo)
    tengo=false
    if tengo_saldo(titulo.precio_edificar)
      tengo=true
    end
    return tengo && titulo.num_casas<8 && !titulo.hipotecada
  end
  
  def puedo_edificar_hotel(titulo)
    tengo=false
    if(tengo_saldo(titulo.precio_edificar))
      tengo=true
    end
    return tengo && titulo.num_hoteles<8 && !titulo.hipotecada
  end
  
  def to_s
    return super + "\n Este jugador es un especulador, fianza: #{@fianza}"
    end

end
end