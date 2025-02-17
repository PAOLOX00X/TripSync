package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TripSyncTest {
    static TripSync tripSync;

    @BeforeEach
    public void setUp() {

        if(tripSync == null) {
            tripSync = TripSync.getInstance();
        }

        tripSync.reset();
    }

    @AfterEach
    public void tearDown(){
        tripSync=null;
    }
    @Test
    public void testCreaViaggio(){
        tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-23");
        assertNotNull(tripSync.getViaggioCorrente());
    }

    @Test
    public void TestAggiungiMezzo(){
        tripSync.creaViaggio(1,"Catania", "Napoli","2025-06-19", "2025-06-23");
        tripSync.aggiungiMezzo("aereo", 156.00);
        assertNotNull(tripSync.getViaggioCorrente().getElencoMezzi().get(0));
        assertEquals("aereo", tripSync.getViaggioCorrente().getElencoMezzi().get(0).getNome());
        assertEquals(156.00, tripSync.getViaggioCorrente().getElencoMezzi().get(0).getCosto());
    }

    @Test
    public void TestInserisciTappa(){
        tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-27");
        tripSync.aggiungiTappa("colosseo", "2025-06-25 10:30", "2025-06-25 12:30", 35.00);
        assertNotNull(tripSync.getViaggioCorrente().getElencoTappe().get(0));

        tripSync.aggiungiTappa("Piazza di Spagna", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        assertEquals(2, tripSync.getViaggioCorrente().getElencoTappe().size());

        //Le seguenti operazioni non vanno a buon fine in quanto in una gli orari sono gli stessi e nell'altra
        // uguali a quelli di un'altra tappa
        tripSync.aggiungiTappa("Campo dei fiori", "2025-06-25 10:00", "2025-06-25 10:00", 0.00);
        tripSync.aggiungiTappa("Fontana di trevi", "2025-06-25 10:30", "2025-06-25 12:30", 23.00);

        //ci aspettiamo che la dimensione dell'elenco di tappe rimanga immutata
        assertEquals(2, tripSync.getViaggioCorrente().getElencoTappe().size());

    }

    @Test
    public void TestConfermaInserimento(){
        tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-23");
        tripSync.confermaInserimento();
        assertEquals(1, tripSync.getElencoViaggi().size());
        assertNotNull(tripSync.getElencoViaggi().get(1));


        //La seguente operazione non va a buon fine perchè il viaggio esiste già
        tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-23");

        //ci si aspetta che la dimensione della mappa rimanga immutata
        assertEquals(1, tripSync.getElencoViaggi().size());
    }

    @Test
    public void TestSelezionaViaggio(){
        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-19", "2025-06-23");
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
        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-19", "2025-06-23");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
        assertNotNull(tripSync.getViaggioCorrente().getElencoPartecipanti().get("Barbara"));

        //l'operazione non va a buon fine perchè l'utente è già presente nell'elenco dei partecipanti
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
    }

    @Test
    public void TestVisualizzaTappe(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-06-24", "2025-06-25");
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(3));
        tripSync.visualizzaItinerario();

    }

    @Test
    public void TestSelezionaTappa(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-06-24", "2025-06-25");
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(3));
        assertNotNull(tripSync.selezionaTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));


        //L'operazione restituisce un valore nullo perchè la tappa non esiste
        assertNull(tripSync.selezionaTappa("Castello di Paterno", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));


    }

    @Test
    public void TestModificaTappa(){
        tripSync.creaViaggio(1,"Catania", "Milano", "2025-06-25", "2025-06-26");
        tripSync.aggiungiTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(1));
        assertNotNull(tripSync.selezionaTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));

        tripSync.modificaTappa("Duomo di Milano", "2025-06-25 16:00", "2025-06-25 18:00", 35.00);

        //L'operazione non va a buon fine perchè l'ora di inizio è maggiore
        tripSync.modificaTappa("Stadio San Siro", "2025-06-25 19:00", "2025-06-25 18:00", 35.00);
    }


    @Test
    public void TestEliminaTappa(){
        tripSync.creaViaggio(1,"Catania", "Milano", "2025-06-19", "2025-06-26");
        tripSync.aggiungiTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(1));

        assertNotNull(tripSync.selezionaTappa("Stadio San Siro", "2025-06-25 13:30", "2025-06-25 15:30", 35.00));

        tripSync.eliminaTappa();
        assertEquals(0, tripSync.getViaggioCorrente().getElencoTappe().size());

    }

    @Test
    public void TestSelezionaViaggioEffettuato(){
        assertNotNull(tripSync.selezionaViaggioEffettuato(1));

        //l'operazione restituisce un valore null perchè il viaggio non esiste
        assertNull(tripSync.selezionaViaggioEffettuato(5));

    }

    @Test
    public void TestInserisciCredenziali(){


        assertNotNull(tripSync.selezionaViaggioEffettuato(2));
        assertNotNull(tripSync.inserisciCredenziali("Barbara", "bf231202", "effettuato"));

        //l'operazione restituisce un valore nullo perchè il partecipante non fa parte del viaggio
        assertNull(tripSync.inserisciCredenziali("Filippo", "ff270402", "effettuato"));

        //l'operazione restituisce un valore nullo perchè le credenziali non sono valide
        assertNull(tripSync.inserisciCredenziali("Barbara", "sslv3", "effettuato"));

    }

    @Test
    public void TestInserisciFeedback(){
        assertNotNull(tripSync.selezionaViaggioEffettuato(3));
        assertNotNull(tripSync.inserisciCredenziali("Filippo", "ff270402", "effettuato"));
        assertNotNull(tripSync.inserisciFeedback(5, "viaggio molto divertente"));


        //L'operazione ritorna null perchè il numero di stelle è insufficiente
        assertNull(tripSync.inserisciFeedback(6, "viaggio non corretto"));

    }

    @Test
    public void TestConfermaFeedback(){
        assertNotNull(tripSync.selezionaViaggioEffettuato(3));
        assertNotNull(tripSync.inserisciCredenziali("Filippo", "ff270402", "effettuato"));
        assertNotNull(tripSync.inserisciFeedback(5, "viaggio molto divertente"));

        tripSync.confermaFeedback();
    }

    @Test
    public void TestVisualizzaItinerarioPassato(){

        assertNotNull(tripSync.selezionaViaggioEffettuato(1));
        assertNotNull(tripSync.inserisciCredenziali("Filippo", "ff270402","effettuato"));
        assertNotNull(tripSync.inserisciFeedback(5,"Bel viaggio"));
        tripSync.confermaFeedback();

        assertNotNull(tripSync.selezionaViaggioEffettuato(1));
        tripSync.visualizzaItinerarioPassato();
    }

    @Test
    public void TestConfermaPartecipazione(){

        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-25", "2025-06-26");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
        assertNotNull(tripSync.getViaggioCorrente().getGestore().getElencoPartecipazioni().get("Barbara"));

        tripSync.confermaPartecipazione("Barbara");
        //se proviamo a confermare una seconda volta, il sistema notifica la conferma già avvenuta
        tripSync.confermaPartecipazione("Barbara");
        //se proviamo a riconfermare la partecipazione, il sistema da errore, come previsto dalle estensioni
        tripSync.annullaPartecipazione("Barbara");
        //se proviamo un utente che non partecipa al viaggio, verrà segnalato un opportuno messaggio di errore
        tripSync.confermaPartecipazione("Filippo");

    }


    @Test
    public void TestAnnullaPartecipazione(){

        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-25", "2025-06-26");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
        assertNotNull(tripSync.getViaggioCorrente().getGestore().getElencoPartecipazioni().get("Barbara"));

        tripSync.annullaPartecipazione("Barbara");

        //se proviamo ad annullare una seconda volta, il sistema notifica che l'annullamento è già avvenuto
        tripSync.annullaPartecipazione("Barbara");

        //se proviamo a confermare la partecipazione, il sistema da errore, come previsto dalle estensioni
        tripSync.confermaPartecipazione("Barbara");

        //se proviamo un utente che non partecipa al viaggio, verrà segnalato un opportuno messaggio di errore
        tripSync.confermaPartecipazione("Filippo");


    }


}