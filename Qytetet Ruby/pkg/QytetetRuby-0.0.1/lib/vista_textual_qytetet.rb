# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "controlador_qytetet"
require_relative "opcion_menu"
module VistaTextualQytetet
    class VistaTextualQytetet
      @@controlador=ControladorQytetet::ControladorQytetet.instance
      
      def obtener_nombre_jugadores
      puts "Introduzca el numero de jugadores"
      numero=gets.chomp.to_i
      nombres=Array.new
      
      for i in 1..numero
        puts "Introduzca el nombre del jugador #{i}"
        nombres.push(gets.chomp)
      end      
      return nombres
      end
      
      def elegir_casilla(opcion_menu)
        lista=@@controlador.obtener_casillas_validas(opcion_menu)
        casillas=Array.new
        if lista.empty?
          return -1
        else
          puts "Las casillas validas son: "
          for i in lista.each 
            puts i
            casillas<<i.to_s
          end
        end
        return leer_valor_correcto(casillas).to_i      
      end
      
      def leer_valor_correcto(valores_correctos)
        correcto=false
        valor_retornado =""
        puts "Selecciona una de las siguientes opciones: "
        
        for i in valores_correctos.each
          puts "Opcion: "+ i+" - " + ControladorQytetet::OpcionMenu[i.to_i].to_s
        end
        
        loop do
          valor_leido = gets.chomp.to_i
          for i in valores_correctos.each
            
          if valor_leido.to_i == i.to_i
            correcto=true
            valor_retornado = valor_leido
          end      
          
        end
        
        break if correcto
      end
      return valor_retornado
    end
    
    def elegir_operacion
      lista=@@controlador.obtener_operaciones_juego_validas()
      operaciones=Array.new
      for i in lista
        operaciones<<i.to_s
      end
      return leer_valor_correcto(operaciones).to_i
    end
    
    def self.main
      ui= VistaTextualQytetet.new
      @@controlador.set_nombre_jugadores(ui.obtener_nombre_jugadores)
      loop do
        operacion_elegida=ui.elegir_operacion
        necesita_elegir_casilla=@@controlador.necesita_elegir_casilla(operacion_elegida)
        if necesita_elegir_casilla
          casilla_elegida=ui.elegir_casilla(operacion_elegida)
        end
        if !necesita_elegir_casilla || casilla_elegida>=0
          puts @@controlador.realizar_operacion(operacion_elegida,casilla_elegida)
        end
        break if 1!=1
      end
    end
    
  end
  VistaTextualQytetet.main
end