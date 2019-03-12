#encoding: utf-8
require "singleton"
module ModeloQytetet
  
  class Dado
    include Singleton
    def initialize
     @valor=0
    end
    def tirar
    r = rand(1..6)
    @valor=r
    return r
    end
    attr_reader :valor
  end
end