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

        tripSync.aggiungiTappa("Piazza di Spagna", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        assertEquals(2, tripSync.getV().getElencoTappe().size());

        //Le seguenti operazioni non vanno a buon fine in quanto in una gli orari sono gli stessi e nell'altra
        // uguali a quelli di un'altra tappa
        tripSync.aggiungiTappa("Campo dei fiori", "2025-06-25 10:00", "2025-06-25 10:00", 0.00);
        tripSync.aggiungiTappa("Fontana di trevi", "2025-06-25 10:30", "2025-06-25 12:30", 23.00);

        //ci aspettiamo che la dimensione dell'elenco di tappe rimanga immutata
        assertEquals(2, tripSync.getV().getElencoTappe().size());

    }

    @Test
    public void TestConfermaInserimento(){
        tripSync.creaViaggio(001,"Catania", "Napoli");
        tripSync.confermaInserimento();
        assertEquals(1, tripSync.getElencoViaggi().size());
        assertNotNull(tripSync.getElencoViaggi().get(001));


        //La seguente operazione non va a buon fine perchè il viaggio esiste già
        tripSync.creaViaggio(001,"Catania", "Napoli");

        //ci si aspetta che la dimensione della mappa rimanga immutata
        assertEquals(1, tripSync.getElencoViaggi().size());
    }

    @Test
    public void TestSelezionaViaggio(){
        tripSync.creaViaggio(2,"Palermo", "Messina");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));

        //Il valore è nullo perchè il viaggio non esiste
        assertNull(tripSync.selezionaViaggio(3));


    }

    @Test
    public void TestInserisciPartecipante(){
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));

        //l'operazione non restituisce un valore perchè l'utente non esiste
        assertNull(tripSync.inserisciPartecipante("Pietro"));

    }

    @Test
    public void TestConfermaPartecipante(){
        tripSync.creaViaggio(2,"Palermo", "Messina");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
        assertNotNull(tripSync.getViaggioSelezionato().getElencoPartecipanti().get("Barbara"));

        //l'operazione non va a buon fine perchè l'utente è già presente nell'elenco dei partecipanti
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
    }

    @Test
    public void TestVisualizzaTappe(){
        tripSync.creaViaggio(3,"Firenze", "Bologna");
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(3));
        tripSync.visualizzaItinerario();

    }

    @Test
    public void TestSelezionaTappa(){
        tripSync.creaViaggio(3,"Firenze", "Bologna");
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(3));
        assertNotNull(tripSync.SelezionaTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));

        //L'operazione restituisce un valore nullo perchè la tappa non esiste
        assertNull(tripSync.SelezionaTappa("Castello di Paterno", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));


    }

    @Test
    public void TestModificaTappa(){
        tripSync.creaViaggio(001,"Catania", "Milano");
        tripSync.aggiungiTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(001));
        assertNotNull(tripSync.SelezionaTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));

        tripSync.ModificaTappa("Stadio San Siro", "2025-06-25 16:00", "2025-06-25 18:00", 35.00);


        //L'operazione non va a buon fine perchè l'ora di inizio è maggiore
        tripSync.ModificaTappa("Stadio San Siro", "2025-06-25 19:00", "2025-06-25 18:00", 35.00);
    }


    @Test
    public void TestEliminaTappa(){
        tripSync.creaViaggio(001,"Catania", "Milano");
        tripSync.aggiungiTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(001));

        assertNotNull(tripSync.SelezionaTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));

        tripSync.EliminaTappa();

    }

    @Test
    public void TestSelezionaViaggioEffettuato(){
        assertNotNull(tripSync.SelezionaViaggioEffettuato(001));

        //l'operazione restituisce un valore null perchè il viaggio non esiste
        assertNull(tripSync.SelezionaViaggioEffettuato(005));

    }

    @Test
    public void TestInserisciCredenziali(){


        assertNotNull(tripSync.SelezionaViaggioEffettuato(002));
        System.out.println("Viaggio selezionato: " + tripSync.getVe().getCodice());
        System.out.println("Partecipanti del viaggio selezionato: " + tripSync.getVe().getElencoPartecipanti());
        assertNull(tripSync.InserisciCredenziali("Barbara", "arena"));


    }

    @Test
    public void TestInserisciFeedback(){
        assertNotNull(tripSync.SelezionaViaggioEffettuato(003));
        assertNotNull(tripSync.InserisciCredenziali("Filippo", "ff270402"));
        assertNotNull(tripSync.InsersciFeedback(5, "viaggio molto divertente"));

        System.out.println(tripSync.InsersciFeedback(5, "viaggio molto divertente"));

        //L'operazione ritorna null perchè il numero di stelle è insufficiente
        assertNull(tripSync.InsersciFeedback(6, "viaggio non corretto"));

    }

    @Test
    public void TestConfermaFeedback(){
        assertNotNull(tripSync.SelezionaViaggioEffettuato(003));
        assertNotNull(tripSync.InserisciCredenziali("Filippo", "ff270402"));
        assertNotNull(tripSync.InsersciFeedback(5, "viaggio molto divertente"));

        tripSync.ConfermaFeedback();
    }



}