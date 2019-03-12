#encoding: utf-8

require_relative "titulo_propiedad"
require_relative "casilla"
require_relative "tipo_casilla"
require_relative "calle"
module ModeloQytetet
  class Tablero
  

    def initialize
      inicializar_tablero
    
    end

    def self.new_casilla(casillas, carcel)
      @casillas=casillas
      @carcel=carcel
    end

    attr_reader :casillas, :carcel
    
    def to_s
      "Tablero{casillas: #{@casillas},\n carcel: #{@carcel}}"
    end
    
    def inicializar_tablero
      titulos=Array.new
      @casillas=Array.new
      titulos << TituloPropiedad.new("Calle Morgana",750,100,7,100,300)
      titulos << TituloPropiedad.new("Calle Marcos Heredia",800,125,2,125,375)
      titulos << TituloPropiedad.new("Calle Nueva",825,200,7,100,450)
      titulos << TituloPropiedad.new("Calle Capo",975,250,7,100,550)
      titulos << TituloPropiedad.new("Calle Maxima",1000,300,7,100,650)
      titulos << TituloPropiedad.new("Calle Miguel Posadas",1150,350,7,100,700)
      titulos << TituloPropiedad.new("Calle Estufu",1200,375,7,100,850)
      titulos << TituloPropiedad.new("Calle Ancha",1350,400,7,100,875)
      titulos << TituloPropiedad.new("Calle Smite",1400,425,7,100,900)
      titulos << TituloPropiedad.new("Calle Ela",1550,450,7,100,925)
      titulos << TituloPropiedad.new("Calle David Broncano",1600,500,7,100,950)
      titulos << TituloPropiedad.new("Calle calle",1800,600,7,100,1000)

      @casillas << Casilla.new(0, TipoCasilla::SALIDA, 1000)
      @casillas << Calle.new(1, titulos[0])
      @casillas << Casilla.new(2, TipoCasilla::SORPRESA, 0)
      @casillas << Calle.new(3, titulos[1])
      @casillas << Calle.new(4, titulos[2])
      @casillas << Casilla.new(5, TipoCasilla::CARCEL,0)
      @casillas << Calle.new(6, titulos[3])
      @casillas << Casilla.new(7, TipoCasilla::SORPRESA,0)
      @casillas << Calle.new(8, titulos[4])
      @casillas << Calle.new(9, titulos[5])
      @casillas << Casilla.new(10, TipoCasilla::PARKING,0)
      @casillas << Calle.new(11, titulos[6])
      @casillas << Casilla.new(12, TipoCasilla::SORPRESA,0)
      @casillas << Calle.new(13, titulos[7])
      @casillas << Calle.new(14, titulos[8])
      @casillas << Casilla.new(15, TipoCasilla::JUEZ,0)
      @casillas << Calle.new(16, titulos[9])
      @casillas << Casilla.new(17, TipoCasilla::IMPUESTO,0)
      @casillas << Calle.new(18, titulos[10])
      @casillas << Calle.new(19, titulos[11])
      @carcel=@casillas[5]
    end
      private :inicializar_tablero
    def es_casilla_carcel(numeroCasilla)
      es_carcel=false;
      if(@carcel.numeroCasilla==numeroCasilla)
        es_carcel=true;
      return es_carcel;  
      end
    end
    def obtener_casilla_final(casilla,desplazamiento)
      return @casillas[(casilla.numeroCasilla+desplazamiento)%20]
    end
    def obtener_casilla_numero(numeroCasilla)
      return @casillas[numeroCasilla];
    end
  end
  
end