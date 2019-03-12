#encoding: utf-8
require "singleton"
require_relative "estado_juego"
require_relative "qytetet"
require_relative "opcion_menu"
require_relative "metodo_salir_carcel"
module ControladorQytetet
  class ControladorQytetet
    include Singleton
    @@modelo=ModeloQytetet::Qytetet.instance 
    def initialize
      @nombre_jugadores=Array.new
      @estado=nil
      @metodo=nil
    end
    
    def set_nombre_jugadores(nombres)
      @nombre_jugadores=nombres
      @@modelo.inicializar_juego(@nombre_jugadores)
    end
    
    def obtener_operaciones_juego_validas
      operaciones_validas=Array.new
      @estado=@@modelo.estado
      
      if @nombre_jugadores.empty?
        operaciones_validas<<OpcionMenu.index(:INICIARJUEGO)
        return operaciones_validas
      end
      
      case @@modelo.estado
      when ModeloQytetet::EstadoJuego::JA_CONSORPRESA
        operaciones_validas<<OpcionMenu.index(:APLICARSORPRESA)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
      when ModeloQytetet::EstadoJuego::JA_PREPARADO
        operaciones_validas<<OpcionMenu.index(:JUGAR)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
      when ModeloQytetet::EstadoJuego::JA_PUEDEGESTIONAR
        operaciones_validas<<OpcionMenu.index(:PASARTURNO)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
        if !@@modelo.obtener_propiedades_jugador.empty?
          operaciones_validas<<OpcionMenu.index(:EDIFICARCASA)
          operaciones_validas<<OpcionMenu.index(:EDIFICARHOTEL)
          operaciones_validas<<OpcionMenu.index(:HIPOTECARPROPIEDAD)
          operaciones_validas<<OpcionMenu.index(:VENDERPROPIEDAD)
          if !@@modelo.obtener_propiedades_jugador_segun_estado_hipoteca(true).empty?
            operaciones_validas<<OpcionMenu.index(:CANCELARHIPOTECA)
          end
        end
      when ModeloQytetet::EstadoJuego::JA_PUEDECOMPRAROGESTIONAR
        operaciones_validas<<OpcionMenu.index(:PASARTURNO)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
        operaciones_validas<<OpcionMenu.index(:COMPRARTITULOPROPIEDAD)
        if !@@modelo.obtener_propiedades_jugador.empty?
          operaciones_validas<<OpcionMenu.index(:EDIFICARCASA)
          operaciones_validas<<OpcionMenu.index(:EDIFICARHOTEL)
          operaciones_validas<<OpcionMenu.index(:HIPOTECARPROPIEDAD)
          operaciones_validas<<OpcionMenu.index(:VENDERPROPIEDAD)
          if !@@modelo.obtener_propiedades_jugador_segun_estado_hipoteca(true).empty?
            operaciones_validas<<OpcionMenu.index(:CANCELARHIPOTECA)
          end
        end
      when ModeloQytetet::EstadoJuego::ALGUNJUGADORENBANCARROTA
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
      when ModeloQytetet::EstadoJuego::JA_ENCARCELADO
        operaciones_validas<<OpcionMenu.index(:PASARTURNO)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
      when ModeloQytetet::EstadoJuego::JA_ENCARCELADOCONOPCIONDELIBERTAD
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        operaciones_validas<<OpcionMenu.index(:MOSTRARJUGADORES)
        operaciones_validas<<OpcionMenu.index(:MOSTRARTABLERO)
        operaciones_validas<<OpcionMenu.index(:TERMINARJUEGO)
        operaciones_validas<<OpcionMenu.index(:INTENTARSALIRCARCELPAGANDOLIBERTAD)
        operaciones_validas<<OpcionMenu.index(:INTENTARSALIRCARCELTIRANDODADO)
      end
      return operaciones_validas
    end
    
    def necesita_elegir_casilla(opcion_menu)
      resultado=false
      if (opcion_menu == OpcionMenu.index(:HIPOTECARPROPIEDAD) || opcion_menu == OpcionMenu.index(:CANCELARHIPOTECA) || 
            opcion_menu == OpcionMenu.index(:EDIFICARCASA) || opcion_menu == OpcionMenu.index(:EDIFICARHOTEL) || 
            opcion_menu == OpcionMenu.index(:VENDERPROPIEDAD))
        resultado=true
      end
      return resultado
    end
    
    def obtener_casillas_validas(opcion_menu)
      validas=Array.new
      if (opcion_menu == OpcionMenu.index(:EDIFICARCASA) || opcion_menu == OpcionMenu.index(:Edificar_hotel) || 
            opcion_menu == OpcionMenu.index(:VENDERPROPIEDAD))
        validas = @@modelo.obtener_propiedades_jugador
      else
        if opcion_menu == OpcionMenu.index(:CANCELARHIPOTECA)
          validas = @@modelo.obtener_propiedades_jugador_segun_estado_hipoteca(true)
        else
          if opcion_menu == OpcionMenu.index(:HIPOTECARPROPIEDAD)
            validas = @@modelo.obtener_propiedades_jugador_segun_estado_hipoteca(false)
          end
        end
      end
 
    return validas
    end
    
    def realizar_operacion(opcion_elegida,casilla_elegida)
      case opcion_elegida
      when OpcionMenu.index(:INICIARJUEGO) 
        @@modelo.inicializar_juego(@nombre_jugadores)
        devolver="Juego iniciado"
      when OpcionMenu.index(:JUGAR) 
        @@modelo.jugar 
        devolver="El valor que ha salido en el dado es " + @@modelo.get_valor_dado.to_s + ". Has caido en la casilla "+@@modelo.obtener_casilla_jugador_actual.to_s
      when OpcionMenu.index(:APLICARSORPRESA)
        @@modelo.aplicar_sorpresa
        devolver="SORPRESA: " + @@modelo.carta_actual.to_s
      when OpcionMenu.index(:INTENTARSALIRCARCELPAGANDOLIBERTAD)
        salir = @@modelo.intentar_salir_carcel(ModeloQytetet::MetodoSalirCarcel::PAGANDOLIBERTAD)
        if salir
          devolver="Pagas la fianza. ¡Sales de la cárcel!"
        else
          devolver="No te puedes permitir pagar la fianza, sigues en la cárcel"
        end
      when OpcionMenu.index(:INTENTARSALIRCARCELTIRANDODADO)
        salir_dado = @@modelo.intentar_salir_carcel(ModeloQytetet::MetodoSalirCarcel::TIRANDODADO)
        if salir_dado
          devolver="Has sacado un " + @@modelo.get_valor_dado.to_s + ". Es tu día de suerte, ¡Sales de la cárcel!"
        else
          devolver="Has sacado un " + @@modelo.get_valor_dado.to_s + ". Otra vez será, pero de momento te quedas en la cárcel"
        end
      when OpcionMenu.index(:COMPRARTITULOPROPIEDAD)
        comprado = @@modelo.comprar_titulo_propiedad
        if comprado
          devolver="¡Enhorabuena! acabas de adquirir la propiedad de la calle" + @@modelo.obtener_casilla_jugador_actual.to_s
        else
          devolver="No es posible comprar la propiedad de la casilla actual..."
        end
      when OpcionMenu.index(:HIPOTECARPROPIEDAD)
        @@modelo.hipotecar_propiedad(casilla_elegida)
        devolver="Se ha hipotecado la propiedad de la casilla " + casilla_elegida.to_s
      when OpcionMenu.index(:CANCELARHIPOTECA)
        cancelada = @@modelo.cancelar_hipoteca(casilla_elegida)
        if cancelada
          devolver="Se ha cancelado con éxito la casilla " + casilla_elegida.to_s
        else
          devolver="No ha sido posible cancelar la hipoteca de la casilla " + casilla_elegida.to_s
        end
      when OpcionMenu.index(:EDIFICARCASA)
        casa_edificada = @@modelo.edificar_casa(casilla_elegida)
        if casa_edificada
          devolver="Has construido una casa en la casilla " + casilla_elegida.to_s 
        else
          devolver="No ha sido posible edificar la casa en la casilla " + casilla_elegida.to_s
        end
      when OpcionMenu.index(:EDIFICARHOTEL)
        casa_edificada = @@modelo.edificar_hotel(casilla_elegida)
        if casa_edificada
          devolver="Has construido un hotel en la casilla " + casilla_elegida 
        else
          devolver="No ha sido posible edificar el hotel en la casilla" + casilla_elegida
        end
      when OpcionMenu.index(:VENDERPROPIEDAD) 
        @@modelo.vender_propiedad(casilla_elegida)
        devolver="Has vendido la propiedad de la casilla " + casilla_elegida
      when OpcionMenu.index(:PASARTURNO)
        @@modelo.siguiente_jugador
        devolver = "El jugador actual termina su turno"
      when OpcionMenu.index(:TERMINARJUEGO)
        @@modelo.obtener_ranking
        puts "El ranking es: "
        puts @@modelo.jugadores.to_s
        puts "Fin del juego"
        exit(0)
      when OpcionMenu.index(:MOSTRARJUGADORACTUAL)
        puts @@modelo.jugador_actual.to_s
      when OpcionMenu.index(:MOSTRARJUGADORES)
        puts @@modelo.jugadores.to_s
      when OpcionMenu.index(:MOSTRARTABLERO) 
        puts @@modelo.tablero.to_s
        
      end

      return devolver
    end
  end    
end
