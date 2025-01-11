package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TripSyncTest {
    static TripSync tripSync;

    @BeforeAll
    public static void initTest() {
        tripSync=TripSync.getInstance();


    }
    @AfterAll
    public static void tearDown(){
        tripSync=null;
    }
    @Test
    public void testCreaViaggio(){
        tripSync.creaViaggio(001,"Catania", "Napoli");
        assertNotNull(tripSync.getV());
    }

    @Test
    public void TestAggiungiMezzo(){
        tripSync.creaViaggio(001,"Catania", "Napoli");
        tripSync.aggiungiMezzo("aereo", 156.00);
        assertNotNull(tripSync.getV().getElencoMezzi().get(0));
        assertEquals("aereo", tripSync.getV().getElencoMezzi().get(0).getNome());
        assertEquals(156.00, tripSync.getV().getElencoMezzi().get(0).getCosto());
    }

    @Test
    public void TestInserisciTappa(){
        tripSync.creaViaggio(001,"Catania", "Napoli");
        tripSync.aggiungiTappa("colosseo", "2025-06-25 10:30", "2025-06-25 12:30", 35.00);
        assertNotNull(tripSync.getV().getElencoTappe().get(0));

        tripSync.aggiungiTappa("Piazza di Spagna", "2025-06-25 10:30", "2025-06-25 12:30", 35.00);
        assertEquals(2, tripSync.getV().getElencoTappe().size());

        assertNotNull(tripSync.getV().getElencoTappe().get(1));

    }

    @Test
    public void TestConfermaInserimento(){
        tripSync.creaViaggio(001,"Catania", "Napoli");
        tripSync.confermaInserimento();
        assertEquals(1, tripSync.getElencoViaggi().size());
        assertNotNull(tripSync.getElencoViaggi().get(001));

        tripSync.creaViaggio(001,"Catania", "Napoli");
    }




}