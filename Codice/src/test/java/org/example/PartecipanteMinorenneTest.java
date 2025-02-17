package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartecipanteMinorenneTest {
    private PartecipanteMinorenne partecipanteMinorenne;

    @BeforeEach
    public void setUp() {
        partecipanteMinorenne = new PartecipanteMinorenne();
    }
    @AfterEach
    public void tearDown() {
        partecipanteMinorenne = null;
    }

    @Test
    public void testCalcoloCostoAggiornato(){
        double costoBase=23;
        double costoScontato= partecipanteMinorenne.calcolaCostoAggiornato(costoBase);
        double fattore = Math.pow(10, 2);
        costoScontato= Math.round(costoScontato * fattore) / fattore;
        assertEquals(18.40, costoScontato);
    }

    @Test
    public void testCalcoloCostoAggiornatoNullo(){
        double costoBase=0.00;
        double costoScontato= partecipanteMinorenne.calcolaCostoAggiornato(costoBase);
        double fattore = Math.pow(10, 2);
        costoScontato= Math.round(costoScontato * fattore) / fattore;
        assertEquals(0.00, costoScontato);
    }

}