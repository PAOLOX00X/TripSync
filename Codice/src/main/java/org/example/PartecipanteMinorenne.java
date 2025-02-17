package org.example;

public class PartecipanteMinorenne implements Strategy{
    @Override
    public double calcolaCostoAggiornato(double costo) {
        return costo*0.8;
    }
}
