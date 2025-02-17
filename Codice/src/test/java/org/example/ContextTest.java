package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
    Context context;

    @BeforeEach
    public void setUp() {
        context = new Context();
    }

    @AfterEach
    public void tearDown() {
        context = null;
    }

    @Test
    public void TestExecuteStrategyFestivo(){
        context.setStrategy(new AumentoFestivita());
        double costoBase=23.00;
        double costoAggiornato=context.executeStrategy(costoBase);
        double fattore = Math.pow(10, 2);
        costoAggiornato= Math.round(costoAggiornato * fattore) / fattore;
        assertEquals(27.60, costoAggiornato);
    }

    @Test
    public void TestExecuteStrategyMinorenne(){
        context.setStrategy(new PartecipanteMinorenne());
        double costoBase=23.00;
        double costoAggiornato=context.executeStrategy(costoBase);
        double fattore = Math.pow(10, 2);
        costoAggiornato= Math.round(costoAggiornato * fattore) / fattore;
        assertEquals(18.40, costoAggiornato);
    }

}