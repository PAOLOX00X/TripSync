package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViaggioTest {
    @Test
    public void TestAggiungiMezzo(){
        Viaggio v=new Viaggio(1, "Catania", "Napoli");
        v.aggiungiMezzo("aereo", 156.00);
        assertNotNull(v.getElencoMezzi().get(0));
    }

    @Test
    public void TestAggiungiTappa(){
        Viaggio v=new Viaggio(2, "Catania", "Napoli");
        v.aggiungiTappa("Piazza garibaldi", "2025-06-25 10:30", "2025-06-25 12:30", 23.00);
        assertEquals("Piazza garibaldi", v.getElencoTappe().get(0).getLuogo());

        //Le seguenti operazioni non vanno a buon fine in quanto in una gli orari sono gli stessi e nell'altra
        // uguali a quelli di un'altra tappa
        v.aggiungiTappa("Quartieri Spagnoli", "2025-06-25 10:00", "2025-06-25 10:00", 0.00);
        v.aggiungiTappa("Stadio Maradona", "2025-06-25 10:30", "2025-06-25 12:30", 23.00);

        assertEquals(1, v.getElencoTappe().size());

    }

    @Test
    public void TestConfermaPartecipante(){

        Viaggio v=new Viaggio(3, "Firenze", "Bologna");
        Partecipante p1=new Partecipante("Barbara");
        v.confermaPartecipante("Barbara", p1);
        assertEquals("Barbara", v.getElencoPartecipanti().get("Barbara").getNomeUtente());

        // l'operazione non va a buon fine perchè l'utente è già presente
        v.confermaPartecipante("Barbara", p1);

    }

    @Test
    public void TestVisualizzaItinerario(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi");
        v.aggiungiTappa("torre eiffel", "2025-02-07 18:00", "2025-02-07 19:00", 30.00);
        v.aggiungiTappa("Louvre", "2025-02-07 10:00", "2025-02-07 13:00", 15.00);
        v.visualizzaItinerario();
    }
}