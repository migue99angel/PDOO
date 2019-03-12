#encoding: utf-8
require_relative "titulo_propiedad"
require_relative "casilla"
require_relative "tipo_sorpresa"
require_relative "estado_juego"
require_relative "especulador"


module ModeloQytetet
  class Jugador
  def initialize(nombre, encarcelado=false,saldo=7500,propiedades=Array.new,casilla_actual=nil,carta_libertad=nil)
    @encarcelado=encarcelado
    @saldo=saldo
    @propiedades=propiedades
    @casilla_actual=casilla_actual
    @carta_libertad=carta_libertad
    @nombre=nombre
  end

#  def self.nuevo(nombre)
#      new(nombre)
#    end
    
  def self.copia(jugador)
    self.new(jugador.nombre,jugador.encarcelado,jugador.saldo,jugador.propiedades,jugador.casilla_actual,jugador.carta_libertad)
  end
  
  attr_reader   :encarcelado, :nombre, :saldo
  attr_accessor :encarcelado, :carta_libertad, :casilla_actual,:propiedades
  

  def cancelar_hipoteca(titulo)
    cancelar=false
    coste_cancelar=titulo.calcular_coste_cancelar
    if tengo_saldo(coste_cancelar)
      cancelar=true
      modificar_saldo(-coste_cancelar)
      titulo.cancelar_hipoteca
    end
    return cancelar
  end
  
  def comprar_titulo_propiedad()   
    coste_compra=@casilla_actual.precio
    comprado=false
    if coste_compra < @saldo
      titulo=@casilla_actual.asignar_propietario(self)
      @propiedades.push(titulo)
      modificar_saldo(-coste_compra)
      comprado=true
    end
    return comprado
  end
  
  def cuantas_casas_hoteles_tengo()
     casashoteles=0
     for i in @propiedades
       casashoteles+=i.num_casas + i.num_hoteles
    end
    return casashoteles
  end
  
  def debo_pagar_alquiler()
      titulo = @casilla_actual.titulo
      es_de_mi_propiedad = es_de_mi_propiedad(titulo)
      debo_pagar=false
      
    if !es_de_mi_propiedad
      tiene_propietario=titulo.tengo_propietario
      if tiene_propietario
        esta_hipotecada=titulo.hipotecada
        if !esta_hipotecada
          debo_pagar=true
        end
      end
    end
    debo_pagar
  end
  
  def devolver_carta_libertad()
    carta=Sorpresa.new(@carta_libertad.texto, @carta_libertad.valor,@carta_libertad.tipo)
    return carta
  end

  def cuantas_casas_hoteles_tengo()
     casashoteles=0
     for i in @propiedades
       casashoteles+=i.num_casas + i.num_hoteles
    end
    return casashoteles
  end
  
  def edificar_casa(titulo)
      edificada =false
      coste_edificar_casa=titulo.precio_edificar
      puedo=tengo_saldo(coste_edificar_casa)
      if puedo        
        titulo.edificar_casa
        modificar_saldo(-coste_edificar_casa)
        edificada =true
      end
       return edificada
    end

  def edificar_hotel(titulo)
    edificada=false
    
    if puedo_edificar_hotel(titulo)
      coste_edificar_hotel=titulo.precio_edificar
      puedo=tengo_saldo(coste_edificar_hotel)
      if puedo
        titulo.edificar_hotel
        modificar_saldo(-coste_edificar_hotel)
        edificada=true
      end
    end
    return edificada
  end
  
  def estoy_en_calle_libre()
    return !@casilla_actual.tengo_propietario
  end
  
  def hipotecar_propiedad(titulo)
    coste_hipoteca=titulo.hipotecar()
    modificar_saldo(coste_hipoteca)
  end
  
  def debo_ir_a_carcel
    return !tengo_carta_libertad
  end
  
  def ir_a_carcel(casilla)
    @casilla_actual=casilla
    @encarcelado=true
  end
  
  def modificar_saldo(cantidad)
    @saldo = @saldo+cantidad
  end
  
  def obtener_capital()
    capital=0
    for i in @propiedades.each
      capital+=i.precio_compra + i.precio_edificar*i.num_casas + i.precio_edificar*i.num_hoteles
      if(i.hipotecada)
        capital-=i.hipoteca_base
      end
    end
    return capital+saldo
  end
  
  def obtener_propiedades(hipotecada)
    resultado=Array.new()
    for i in @propiedades
      if(i.hipotecada==hipotecada)
        resultado.add(i)
      end
    end 
  end
  
  def pagar_alquiler()
    coste_alquiler=@casilla_actual.pagar_alquiler
    modificar_saldo(-coste_alquiler)
  end
  
  def pagar_impuesto()
    @saldo-=@casilla_actual.precio
  end
  
  def pagar_libertad(cantidad)
    tengo_saldo=tengo_saldo(cantidad)
    if tengo_saldo
      @encarcelado=false
      modificar_saldo(-cantidad)
    end
  end
  
  def set_carta_libertad(carta)
    if(carta.getTipo ==TipoSorpresa::Salir_carcel )
        @carta_libertad=carta
    else
       "Tipo de carta diferente a Salir_carcel"
    end
  end
  
  def set_casilla_actual(casilla)
    @casilla_actual=casilla
  end
  
  def set_encarcelado(encarcelado=false)
    @encarcelado=encarcelado
  end
  
  def tengo_carta_libertad()
    return if @carta_libertad!=nil
  end
  def vender_propiedad(casilla)
    titulo=casilla.titulo
    eliminar_de_mis_propiedades(titulo)
    precio_venta=titulo.calcular_precio_venta
    modificar_saldo(precio_venta)
  end

  def eliminar_de_mis_propiedades(titulo)
    @propiedades.delete(titulo)
    titulo.propietario=nil  
  end
  
  def es_de_mi_propiedad(titulo)
    contenido = false      
      for i in @propiedades
        if i == titulo
          contenido = true
        end
      end     
      return contenido
  end
  
  def tengo_saldo(cantidad)
    tengo=false
    if @saldo > cantidad
      tengo=true
    end
    return tengo
  end
  
  def convertirme(fianza)
    convertido = Especulador.copia(self,fianza)
    return convertido
  end
  
  def puedo_edificar_casa(titulo)
    return  tengo_saldo(titulo.precio_edificar) && !titulo.hipotecada;
  end
  
  def puedo_edificar_hotel(titulo)
    return  tengo_saldo(titulo.precio_edificar) && titulo.num_hoteles <4 && !titulo.hipotecada;
  end
   public 
   
  def to_s
    "Jugador: #{@nombre} \n capital: #{obtener_capital} \n encarcelado: #{@encarcelado} \n propiedades: #{@propiedades} \n saldo: #{@saldo} \n casillaActual: #{@casilla_actual} \n"
  end
# protected  :debo_ir_a_carcel,  :pagar_impuesto, :puedo_edificar_casa, :puedo_edificar_hotel, :tengo_saldo 
 
  def <=>(otroJugador)
    otro_capital = otroJugador.obtener_capital
    mi_capital = obtener_capital

    if (otro_capital>mi_capital)
      return 1 end

    if (otro_capital<mi_capital)
      return -1 end

    return 0
  end
  
end
end
