package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViaggioTest {
    @Test
    public void TestAggiungiMezzo(){
        Viaggio v=new Viaggio(1, "Catania", "Napoli", "2025-06-19", "2025-06-23");
        v.aggiungiMezzo("aereo", 156.00);
        assertNotNull(v.getElencoMezzi().get(0));
    }

    @Test
    public void TestAggiungiTappa(){
        Viaggio v=new Viaggio(2, "Catania", "Napoli", "2025-06-19", "2025-06-23");
        v.aggiungiTappa("Piazza garibaldi", "2025-06-20 10:30", "2025-06-20 12:30", 23.00);
        assertEquals("Piazza garibaldi", v.getElencoTappe().get(0).getLuogo());

        //Le seguenti operazioni non vanno a buon fine in quanto in una gli orari sono gli stessi e nell'altra
        // uguali a quelli di un'altra tappa
        Exception exception1=assertThrows(CredenzialiNonValideException.class, ()->v.aggiungiTappa("Quartieri Spagnoli", "2025-06-22 10:00", "2025-06-22 10:00", 0.00));
        assertEquals("Errore: la data di inizio e fine e' la stessa", exception1.getMessage());

        Exception exception2=assertThrows(CredenzialiNonValideException.class, ()->v.aggiungiTappa("Stadio Maradona", "2025-06-20 10:30", "2025-06-20 12:30", 23.00));
        assertEquals("Errore: Esiste già una tappa con le stesse date e orari.", exception2.getMessage());

        assertEquals(1, v.getElencoTappe().size());

    }

    @Test
    public void TestConfermaPartecipante(){

        Viaggio v=new Viaggio(3, "Firenze", "Bologna", "2025-06-19", "2025-06-23");
        Partecipante p1=new Partecipante("Barbara", "bpf231202", "2002-12-23");
        v.confermaPartecipante("Barbara", p1);
        assertEquals("Barbara", v.getElencoPartecipanti().get("Barbara").getNomeUtente());

        // l'operazione non va a buon fine perchè l'utente è già presente
        assertThrows(ElementoGiaPresenteException.class, ()->v.confermaPartecipante("Barbara", p1));

    }

    @Test
    public void TestVisualizzaItinerario(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-06", "2025-06-08");
        v.aggiungiTappa("torre eiffel", "2025-06-07 18:00", "2025-06-07 19:00", 30.00);
        v.aggiungiTappa("Louvre", "2025-06-07 10:00", "2025-06-07 13:00", 15.00);
        v.visualizzaItinerario();
    }

    @Test
    public void TestSelezionaTappa(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-26");
        v.aggiungiTappa("Stadio Maradona", "2025-06-25 10:30", "2025-06-25 12:30", 23.00);
        assertNotNull(v.selezionaTappa("Stadio Maradona", "2025-06-25 10:30", "2025-06-25 12:30", 23.00));

        //l'operazione ritorna un valore nullo perchè la tappa non esiste
        assertNull(v.selezionaTappa("Piazza plebiscito", "2025-06-25 10:30", "2025-06-25 12:30", 23.00));

    }


    @Test
    public void TestEliminaTappa(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-23");
        v.aggiungiTappa("Stadio Maradona", "2025-06-20 10:30", "2025-06-20 12:30", 23.00);

        v.eliminaTappa(v.selezionaTappa("Stadio Maradona", "2025-06-20 10:30", "2025-06-20 12:30", 23.00));

        //L'operazione ritorna un valore nullo perchè la tappa non esiste più
        assertNull(v.selezionaTappa("Stadio Maradona", "2025-06-20 10:30", "2025-06-20 12:30", 23.00));

    }

    @Test
    public void TestConfermaPartecipazione(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-23");
        Partecipante p1=new Partecipante("Barbara", "bpf231202", "2002-12-23");
        v.confermaPartecipante("Barbara", p1);
        v.confermaPartecipazione("Barbara");

        //se proviamo a confermare una seconda volta, il sistema notifica la conferma già avvenuta
        v.confermaPartecipazione("Barbara");
        //se proviamo a riconfermare la partecipazione, il sistema da errore, come previsto dalle estensioni
        v.annullaPartecipazione("Barbara");
        //se proviamo un utente che non partecipa al viaggio, verrà segnalato un opportuno messaggio di errore
        v.confermaPartecipazione("Filippo");


    }

    @Test
    public void TestAnnullaPartecipazione(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-23");
        Partecipante p1=new Partecipante("Barbara", "bpf231202", "2002-12-23");
        v.confermaPartecipante("Barbara", p1);
        v.annullaPartecipazione("Barbara");

        //se proviamo ad annullare una seconda volta, il sistema notifica che l'annullamento è già avvenuto
        v.annullaPartecipazione("Barbara");

        //se proviamo a confermare la partecipazione, il sistema da errore, come previsto dalle estensioni
        v.confermaPartecipazione("Barbara");

        //se proviamo un utente che non partecipa al viaggio, verrà segnalato un opportuno messaggio di errore
        v.confermaPartecipazione("Filippo");


    }

    @Test
    public void TestVerificaFestivita(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-23");
        assertEquals(false,v.isFestivo("2025-06-19","2025-06-23"));

        assertEquals(true,v.isFestivo("2025-12-19","2025-12-25"));
        assertEquals(true,v.isFestivo("2025-12-29","2026-01-03"));
        assertEquals(true,v.isFestivo("2025-08-12","2025-08-16"));
    }

    @Test
    public void TestVerificaPartecipante(){
        Viaggio v=new Viaggio(1, "Roma", "Parigi", "2025-06-19", "2025-06-23");

        assertEquals(false,v.isMinorenne("2002-04-27"));

        //il partecipante è diventato maggiorenne oggi (test effettuato in data 2025-02-16)
        assertEquals(false, v.isMinorenne("2007-02-16"));

        assertEquals(true, v.isMinorenne("2008-02-16"));
    }

}