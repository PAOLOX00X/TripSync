package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartecipanteTest {
    static Partecipante p;

    @BeforeAll
    public static void initTest(){
        p=new Partecipante("Filippo", "ff270402");


    }


    @AfterAll
    public static void tearDown(){
        p=null;

    }

    @Test
    public void TestInserisciFeedback(){
        assertNotNull(p.InserisciFeedback(5, "ottimo viaggio"));

        assertNull(p.InserisciFeedback(6, "ciao"));
    }

}