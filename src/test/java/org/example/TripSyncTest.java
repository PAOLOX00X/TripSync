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
    public void testAggiungiMezzo(){
        tripSync.aggiungiMezzo("aereo", 156.00);
        assertNotNull(tripSync.getV().getElencoMezzi());
    }

    @Test
    public void testSelezionaViaggio(){
        assertNull(tripSync.selezionaViaggio(004));
    }

}