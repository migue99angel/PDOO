##encoding: utf-8
#require_relative "sorpresa"
#require_relative "qytetet"
#require_relative "tipo_sorpresa"
#require_relative "tablero"
#require_relative "casilla"
#require_relative "metodo_salir_carcel"
#require_relative "especulador"
#
#module ModeloQytetet
#  
#  class PruebaQytetet
#    @@juego=Qytetet.instance 
# 
#    def self.mayor_cero()
#      mayor_cero=Array.new
#      for s in @@juego.mazo.each
#        if s.valor>0
#          mayor_cero<<s      
#        end
#      end
#    return mayor_cero
#    end
#    
#    def self.ir_a_casilla()
#      ir_a_casilla=Array.new
#      for s in @@juego.mazo.each
#        if(s.tipo==:Ir_a_casilla)
#          ir_a_casilla<<s
#        end
#        
#      end
#     return ir_a_casilla
#    end
#    def self.tipo_sorpresa (sorpresa)
#      tipo_sorpresa=Array.new
#      for s in @@juego.mazo.each
#        if s.tipo==sorpresa
#          tipo_sorpresa<<s
#        end
#        
#      end
#      return tipo_sorpresa
#    end
#    def self.get_nombres_jugadores()
#      puts "Introduzca el numero de jugadores"
#      numero=gets.chomp.to_i
#      nombres=Array.new
#      
#      for i in 1..numero
#        puts "Introduzca el nombre del jugador #{i}"
#        nombres.push(gets.chomp)
#      end      
#      return nombres
#    end
#    
#    def self.main
#      tablero=Tablero.new
#      nombres = get_nombres_jugadores
#      @@juego.inicializar_juego(nombres)
#      @@juego.mover(7)
##      @@juego.comprar_titulo_propiedad
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.mover(7)
#      puts @@juego.jugador_actual
#      puts @@juego.carta_actual
#      @@juego.aplicar_sorpresa
##      puts @@juego.carta_actual
##      puts @@juego.jugadores
##      @@juego.mover(3)
##      puts @@juego.jugador_actual.casilla_actual
##      @@juego.comprar_titulo_propiedad
#      puts @@juego.jugador_actual
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_hotel(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_hotel(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_hotel(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_hotel(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      puts @@juego.jugador_actual
##      @@juego.siguiente_jugador
##      @@juego.mover(3)
##      puts @@juego.jugadores
##      @@juego.mover(3)
##      @@juego.jugador_actual.comprar_titulo_propiedad()
##      @@juego.siguiente_jugador
##      @@juego.mover(3)
##      puts @@juego.jugadores
##      @@juego.siguiente_jugador
##      # Probamos a caer en una sorpresa y a aplicar sorpresa después
##      @@juego.mover(2)
##      @@juego.aplicar_sorpresa
##      puts @@juego.jugador_actual.casilla_actual
##      @@juego.mover(3)
##      @@juego.hipotecar_propiedad(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.cancelar_hipoteca(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.vender_propiedad(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_casa(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##      @@juego.edificar_hotel(@@juego.jugador_actual.casilla_actual.numeroCasilla)
##       #Probamos a salir de la carcel
##      @@juego.jugador_actual.ir_a_carcel(tablero.carcel)
##      if(@@juego.jugador_actual.tengo_carta_libertad)
##        @@juego.intentar_salir_carcel(MetodoSalirCarcel::PAGANDOLIBERTAD)
##      else
##        consigue = @@juego.intentar_salir_carcel(MetodoSalirCarcel::TIRANDODADO)
##
##        if consigue
##          puts "Sales de la cárcel"
##        else
##          puts "NO sales de la carcel"
##        end
##      end
#    end
#    
#end
#PruebaQytetet.main
#end