#encoding: utf-8

require_relative "sorpresa"
require_relative "tipo_sorpresa"
require_relative "tablero"
require_relative "casilla"
require_relative "jugador"
require_relative "dado"
require "singleton"


module ModeloQytetet
  class Qytetet
      include Singleton
      @@MAX_JUGADORES=4
      @@NUM_SORPRESAS=10
      @@NUM_CASILLAS=20
      @@PRECIO_LIBERTAD=200
      @@SALDO_SALIDA=1000
      
    def initialize
      @mazo=Array.new
      inicializar_tablero      
      @dado = Dado.instance        
      @casilla_actual=0
      @carta_actual=nil
      @jugador_actual = nil
      @jugadores=Array.new
      @estado
    end
    attr_reader :mazo, :dado, :jugadores, :tablero, :estado
    attr_accessor :estado, :jugador_actual, :carta_actual, :casilla_actual

    def inicializar_tablero
      @tablero = Tablero.new
    end
    
    
    private :inicializar_tablero
    
    def inicializar_cartas_sorpresa  
      @mazo<< Sorpresa.new("Descubren irregularidades en tu declaracion de la renta, ¡Vas a la carcel!", @tablero.carcel.numeroCasilla , TipoSorpresa::IRACASILLA) 
      @mazo<< Sorpresa.new("Te toca pagar la comunidad, paga 50 euros por cada casa u hotel de tu propiedad", -50, TipoSorpresa::PORCASAHOTEL)
      @mazo<< Sorpresa.new("Descubren un error en la factura de la luz, te compensan con 50 euros por cada propiedad", 50, TipoSorpresa::PORCASAHOTEL)
      @mazo<< Sorpresa.new("¡Enhorabuena!, Te toca la loteria", 500, TipoSorpresa::PAGARCOBRAR)
      @mazo<< Sorpresa.new("¡Acabas de invocar al creador del juego, avanza hacia su calle!", 9, TipoSorpresa::IRACASILLA)
      @mazo<< Sorpresa.new("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 3000",3000,TipoSorpresa::CONVERTIRME)
      @mazo<< Sorpresa.new("Hoy es tu dia de suerte, acabas de convertirte en un especulador con fianza de 5000",5000,TipoSorpresa::CONVERTIRME)     
      @mazo<< Sorpresa.new("¡Martes 13!, avanza hacia la casilla nº 13", 13, TipoSorpresa::IRACASILLA)    
      @mazo<< Sorpresa.new("Te multan por exceso de velocidad", -500, TipoSorpresa::PAGARCOBRAR)
      @mazo<< Sorpresa.new("Te levanatas de buen humor e ivitas a un restaurante de lujo a cada jugador, 150 euros por cabeza", -150, TipoSorpresa::PORJUGADOR)
      @mazo<< Sorpresa.new("Es tu cumpleaños, recibes un regalo de 150 euros de cada jugador, !Felicidades¡", 150, TipoSorpresa::PORJUGADOR)
      @mazo<< Sorpresa.new("El presidente del Gobierno te concede un indulto y sales de la carcel", 0, TipoSorpresa::SALIRCARCEL)
    end
    
    def actuar_si_en_casilla_edificable()
      debo_pagar=@jugador_actual.debo_pagar_alquiler
      if debo_pagar
        @jugador_actual.pagar_alquiler
        if @jugador_actual.saldo <= 0
          @estado=EstadoJuego::ALGUNJUGADORENBANCARROTA
        end
      end
      casilla=obtener_casilla_jugador_actual
      tengo_propietario=casilla.tengo_propietario
      if @estado != EstadoJuego::ALGUNJUGADORENBANCARROTA
        if tengo_propietario
          @estado=EstadoJuego::JA_PUEDEGESTIONAR
        else
          @estado=EstadoJuego::JA_PUEDECOMPRAROGESTIONAR
        end
      end
    end
    
    def actuar_si_en_casilla_no_edificable()
      @estado = EstadoJuego::JA_PUEDEGESTIONAR
      casilla_actual = @jugador_actual.casilla_actual
      
      if casilla_actual.tipo == TipoCasilla::IMPUESTO
        @jugador_actual.pagar_impuesto
      else
        if casilla_actual.tipo == TipoCasilla::JUEZ
          encarcelar_jugador
        else
          if casilla_actual.tipo == TipoCasilla::SORPRESA
            @carta_actual=@mazo.delete_at(0)
            @estado = EstadoJuego::JA_CONSORPRESA
          end
        end
      end
    end
    
    
    def mover(numCasillaDestino)
        casilla_inicial=@jugador_actual.casilla_actual
        casilla_final=@tablero.obtener_casilla_numero(numCasillaDestino)
        @jugador_actual.casilla_actual=casilla_final
        if numCasillaDestino < casilla_inicial.numeroCasilla
          @jugador_actual.modificar_saldo(@@SALDO_SALIDA)
        end
        if casilla_final.soy_edificable
          actuar_si_en_casilla_edificable
        else
          actuar_si_en_casilla_no_edificable
        end
    end
    def tirar_dado()
        @dado.tirar
    end
    
    public
    def aplicar_sorpresa()    
      @estado=EstadoJuego::JA_PUEDEGESTIONAR
      if @carta_actual.tipo==TipoSorpresa::SALIRCARCEL
        @jugadorActual.set_carta_libertad(@carta_actual)
      else
        if @carta_actual.tipo==TipoSorpresa::PAGARCOBRAR
          @jugador_actual.modificar_saldo(@carta_actual.valor)
          if @jugador_actual.saldo < 0
            @estado=EstadoJuego::ALGUNJUGADORENBANCARROTA
          end
          else
            if @carta_actual.tipo==TipoSorpresa::IRACASILLA
              valor=@carta_actual.valor
              casilla_carcel=@tablero.es_casilla_carcel(valor)
              if casilla_carcel
                encarcelar_jugador
              else
                mover(valor)
              end
            else
              if @carta_actual.tipo==TipoSorpresa::PORCASAHOTEL
                cantidad=@carta_actual.valor
                numero_total=@jugador_actual.cuantas_casas_hoteles_tengo
                @jugador_actual.modificar_saldo(cantidad*numero_total)
                if @jugador_actual.saldo < 0 
                  @estado=EstadoJuego::ALGUNJUGADORENBANCARROTA
                end
              else
                if @carta_actual.tipo==TipoSorpresa::PORJUGADOR
                  for i in @@MAX_JUGADORES-1
                    jugador = siguiente_jugador
                    if jugador != @jugador_actual
                      jugador.modificar_saldo(-@carta_actual.valor)
                      @jugador_actual.modificar_saldo(@carta_actual.valor)
                    end
                    if jugador.saldo < 0 
                      @estado=EstadoJuego::ALGUNJUGADORENBANCARROTA
                    end
                    @jugador_actual.modificar_saldo(-@carta_actual.valor)
                    if @jugador_actual.saldo < 0 
                      @estado=EstadoJuego::ALGUNJUGADORENBANCARROTA
                  end
                end
              end
              if @carta_actual.tipo == TipoSorpresa::CONVERTIRME
                i=@jugadores.index(@jugador_actual)
                @jugador_actual=@jugador_actual.convertirme(@carta_actual.valor)
                @jugadores[i]=@jugador_actual
              end
            end
        end
      end
    end
    end
    
    
    def cancelar_hipoteca(numeroCasilla)
      casilla=@tablero.obtener_casilla_numero(numeroCasilla)
      titulo=casilla.titulo
      cancelar=@jugador_actual.cancelar_hipoteca(titulo)
      @estado=EstadoJuego::JA_PUEDEGESTIONAR
      return cancelar
    end
    
    
    def comprar_titulo_propiedad()
      comprado=@jugador_actual.comprar_titulo_propiedad
      if comprado
        @estado=EstadoJuego::JA_PUEDEGESTIONAR
      end
    end
    
    
    def edificar_casa(numeroCasilla)
      casilla=@tablero.obtener_casilla_numero(numeroCasilla)
      titulo=casilla.titulo
      
      edificada=@jugador_actual.edificar_casa(titulo)
      
      if edificada
        @estado=EstadoJuego::JA_PUEDEGESTIONAR
      end
    end
    
    
    def edificar_hotel(numeroCasilla)
      casilla=@tablero.obtener_casilla_numero(numeroCasilla)
      if casilla.tipo==TipoCasilla::CALLE && casilla.titulo.num_casas>=4 
        titulo=casilla.titulo
        edificada=@jugador_actual.edificar_hotel(titulo)

        if edificada
          @estado=EstadoJuego::JA_PUEDEGESTIONAR
        end
      end
    end
    
    
    def get_valor_dado()
      @dado.valor
    end
    
    
    def intentar_salir_carcel(metodo)
        if metodo == MetodoSalirCarcel::TIRANDODADO
          resultado=tirar_dado
          if resultado >= 5
            @jugador_actual.encarcelado=false
          end
        end
        if metodo == MetodoSalirCarcel::PAGANDOLIBERTAD
          @jugador_actual.pagar_libertad(@@PRECIO_LIBERTAD)
        end
        encarcelado=@jugador_actual.encarcelado
        if encarcelado
          @estado=EstadoJuego::JA_ENCARCELADO
        else
          @estado=EstadoJuego::JA_PREPARADO
        end
        return !encarcelado
    end
    
    
    def jugar() 
      valor = @dado.tirar
      casilla = @tablero.obtener_casilla_final(@jugador_actual.casilla_actual, valor)
      mover(casilla.numeroCasilla)
    end
    
    def inicializar_juego(nombres)
      inicializar_cartas_sorpresa
      inicializar_tablero
      inicializar_jugadores(nombres)
      salida_jugadores
    end
    
    def hipotecar_propiedad(numeroCasilla)
      casilla=@tablero.obtener_casilla_numero(numeroCasilla)
      titulo=casilla.titulo
      @jugador_actual.hipotecar_propiedad(titulo)
      @estado=EstadoJuego::JA_PUEDEGESTIONAR
    end
    
    
    def obtener_casilla_jugador_actual()
      return @jugador_actual.casilla_actual
    end
     
    
    def obtener_casillas_tablero()
      return @tablero.casillas
    end
    
    
    def obtener_propiedades_jugador()
      casillas=Array.new()
      calle=Calle.new(0,TituloPropiedad.new(0,0,0,0,0,0))
      for i in obtener_casillas_tablero
        casilla =  i
        calle=i
        if casilla.soy_edificable
          if calle.titulo.propietario == @jugador_actual
          casillas << @tablero.casillas.index(calle)
          end
        end
      end
      return casillas
    end
    
    
    def obtener_propiedades_jugador_segun_estado_hipoteca(estadoHipoteca)
      casillas=Array.new()
      calle=Calle.new(0,TituloPropiedad.new(0,0,0,0,0,0))
      for i in obtener_casillas_tablero
        casilla =  i
        calle=i
        if casilla.soy_edificable
          if calle.titulo.propietario == @jugador_actual && calle.titulo.hipotecada == estadoHipoteca
          casillas << @tablero.casillas.index(calle)
          end
        end
      end
      return casillas
    end
    
    
    
    def obtener_ranking()
      @jugadores=@jugadores.sort
    end
    
    
    def obtener_saldo_jugador_actual()
       @jugadorActual.saldo
    end
    
    
    def siguiente_jugador()
      indice = @jugadores.index(@jugador_actual)
      @jugador_actual = @jugadores.at((indice+1)%@jugadores.size)
      
      if @jugador_actual.encarcelado
        @estado=EstadoJuego::JA_ENCARCELADOCONOPCIONDELIBERTAD
        
      else
        @estado=EstadoJuego::JA_PREPARADO
      end
    end
    
    
    
    def vender_propiedad(numeroCasilla)
      casilla=@tablero.obtener_casilla_numero(numeroCasilla)
      @jugador_actual.vender_propiedad(casilla)
      @estado=EstadoJuego::JA_PUEDEGESTIONAR
    end
    
    def encarcelar_jugador()
      if @jugador_actual.debo_ir_a_carcel
        casilla_carcel = @tablero.carcel
        @jugador_actual.ir_a_carcel(casilla_carcel)
        @estado=EstadoJuego::JA_ENCARCELADO
      else
        if @jugador_actual.carta_libertad != nil
          carta=@jugador_actual.devolver_carta_libertad
          @mazo.push(carta)
        end
        @estado=EstadoJuego::JA_PUEDEGESTIONAR
      end
    end
    
    
    def inicializar_jugadores(nombres)
      nombres.each{|unNombre|
      @jugadores << Jugador.new(unNombre)
      }
    end
    
    
    def salida_jugadores()
      r=Random.new()
      for i in @jugadores
        i.casilla_actual= @tablero.obtener_casilla_numero(0)
      end
      @indiceJA=r.rand(@jugadores.size)
      @jugador_actual=@jugadores[@indiceJA]
      @estado=EstadoJuego::JA_PREPARADO
      
    end
    
    
    def set_carta_actual(carta_actual)
      @carta_actual=carta_actual
    end
    
    
private :encarcelar_jugador, :salida_jugadores
end
  end