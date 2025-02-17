package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AumentoFestivitaTest {
    private AumentoFestivita viaggioFestivo;

    @BeforeEach
    public void setUp() {
        viaggioFestivo = new AumentoFestivita();
    }
    @AfterEach
    public void tearDown() {
        viaggioFestivo = null;
    }

    @Test
    public void testCalcoloCostoAggiornato(){
        double costoBase=23;
        double costoScontato= viaggioFestivo.calcolaCostoAggiornato(costoBase);
        double fattore = Math.pow(10, 2);
        costoScontato= Math.round(costoScontato * fattore) / fattore;
        assertEquals(27.60, costoScontato);
    }

    @Test
    public void testCalcoloCostoAggiornatoNullo(){
        double costoBase=0.00;
        double costoScontato= viaggioFestivo.calcolaCostoAggiornato(costoBase);
        double fattore = Math.pow(10, 2);
        costoScontato= Math.round(costoScontato * fattore) / fattore;
        assertEquals(0.00, costoScontato);
    }

}