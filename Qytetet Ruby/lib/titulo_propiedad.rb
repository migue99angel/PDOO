#encoding: utf-8
module ModeloQytetet
  class TituloPropiedad
    def initialize (calle, precio_calle, alquiler_base, factor_revalorizado, hipoteca_base, precio_edificar)
      @calle=calle
      @hipotecada=false
      @precio_compra=precio_calle
      @alquiler_base=alquiler_base
      @factor_revalorizado=factor_revalorizado
      @hipoteca_base=hipoteca_base
      @precio_edificar=precio_edificar
      @num_casas=0
      @num_hoteles=0
      @propietario=nil
    end

     attr_reader :calle ,:precio_compra, :alquiler_base, :factor_revalorizado, :precio_edificar, :num_casas, :num_hoteles ,:hipoteca_base
     attr_accessor :hipotecada, :propietario
        
    
    def calcular_coste_cancelar()
      coste = calcular_coste_hipotecar
      
      return coste + coste*0.1
    end
    def calcular_coste_hipotecar()
        return @hipoteca_base + @num_casas*0.5*@hipoteca_base + @num_hoteles*@hipoteca_base
    end
    def calcular_importe_alquiler()
        return @alquiler_base + (@num_casas*0.5 + @num_hoteles*2)
    end
    def calcular_precio_venta()
        return @precio_compra + (@num_casas + @num_hoteles)*@precio_edificar * @factor_revalorizado
    end
    def cancelar_hipoteca()
        @hipotecada=false
    end
    
    def edificar_casa()
        @num_casas+=1
    end
    def edificar_hotel()
        @num_hoteles+=1
        @num_casas=@num_casas-4
    end
    def hipotecar() 
        coste_hipoteca=calcular_coste_hipotecar
        @hipotecada=true
        return coste_hipoteca
    end
    def pagar_alquiler()
        coste_alquiler=calcular_importe_alquiler
        @propietario.modificar_saldo(coste_alquiler)
        
      return coste_alquiler
    end
    def propietario_encarcelado()
        return  @propietario.encarcelado
    end
    def set_hipotecada(hipotecada)
        @hipotecada=hipotecada
    end
    def set_propietario(propietario)
        @propietario=propietario
    end
    def tengo_propietario()
      return @propietario != nil
    end

     def to_s
        "\n nombre: #{@calle} \n precioCompra: #{@precio_compra} \n alquilerBase: #{@alquier_base} \n factorRevalorización: #{@factor_revalorizado} \n hipotecaBase: #{@hipoteca_base} \n precioEdificar: #{@precio_edificar} \n hipotecada: #{@hipotecada} \n numHoteles: #{@num_hoteles} \n numCasas: #{@num_casas}"
     
    end
  end
end
