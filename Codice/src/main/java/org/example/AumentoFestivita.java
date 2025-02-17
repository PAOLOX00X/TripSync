package org.example;

public class AumentoFestivita implements Strategy{
    @Override
    public double calcolaCostoAggiornato(double costo) {
        return costo*1.2;
    }

}
