package org.example;

public class Context {
    private Strategy strategy;

    public Context(){

    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public double executeStrategy(double costo){
        return strategy.calcolaCostoAggiornato(costo);
    }

}
