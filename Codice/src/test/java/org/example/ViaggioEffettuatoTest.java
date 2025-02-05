package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViaggioEffettuatoTest {
    static ViaggioEffettuato ve1;

    @BeforeAll
    public static void initTest(){
        ve1=new ViaggioEffettuato(001, "Catania", "Milano" );


    }


    @AfterAll
    public static void tearDown(){
        ve1=null;

    }



    @Test
    public void ConfermaFeedback(){

        Feedback f=new Feedback(5, "Ottimo viaggio");
        ve1.confermaFeedback(f);
    }
}