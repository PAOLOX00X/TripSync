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

        //L'operazione non va a buon fine perchè la data di inizio è più antica rispetto alla data odierna
        Exception exception1= assertThrows(CredenzialiNonValideException.class,() -> tripSync.creaViaggio(1,"Catania", "Napoli", "2024-06-19", "2025-06-23"));
        assertEquals("Errore: La data di inizio non può essere nel passato.", exception1.getMessage());
        //L'operazione non va a buon fine perchè la data di fine è più antica rispetto alla data di inizio
        Exception exception2=assertThrows(CredenzialiNonValideException.class, ()->tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-18"));
        assertEquals("Errore: La data di fine non può essere precedente alla data di inizio.", exception2.getMessage());
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
        Exception exception1=assertThrows(CredenzialiNonValideException.class, ()->tripSync.aggiungiTappa("Campo dei fiori", "2025-06-25 10:00", "2025-06-25 10:00", 0.00));
        assertEquals("Errore: la data di inizio e fine e' la stessa", exception1.getMessage());
        Exception exception2=assertThrows(CredenzialiNonValideException.class, ()->tripSync.aggiungiTappa("Fontana di trevi", "2025-06-25 10:30", "2025-06-25 12:30", 23.00));
        assertEquals("Errore: Esiste già una tappa con le stesse date e orari.", exception2.getMessage());

        //Le seguenti operazioni non vanno a buon fine perchè la data di inizio è più antica della data di inizio del Viaggio
        //e la data di fine è più recente della data di fine del viaggio
        Exception exception3=assertThrows(CredenzialiNonValideException.class, ()->tripSync.aggiungiTappa("Campo dei fiori", "2025-06-18 10:00", "2025-06-18 12:00", 0.00));
        assertEquals("Errore: Le date della tappa devono essere comprese tra 2025-06-19 e 2025-06-27", exception3.getMessage());
        Exception exception4=assertThrows(CredenzialiNonValideException.class, ()->tripSync.aggiungiTappa("Campo dei fiori", "2025-06-28 10:00", "2025-06-28 12:00", 0.00));
        assertEquals("Errore: Le date della tappa devono essere comprese tra 2025-06-19 e 2025-06-27", exception4.getMessage());

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
        assertThrows(ElementoGiaPresenteException.class, ()->tripSync.creaViaggio(1,"Catania", "Napoli", "2025-06-19", "2025-06-23"));
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
        assertThrows(ElementoGiaPresenteException.class, ()->tripSync.confermaPartecipante());
    }

    @Test
    public void TestVisualizzaItinerario(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-06-19", "2025-06-27");
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
        Exception exception1=assertThrows(CredenzialiNonValideException.class, ()->tripSync.modificaTappa("Stadio San Siro", "2025-06-25 19:00", "2025-06-25 18:00", 35.00));
        assertEquals("Errore: il software non è una macchina del tempo. Inserire date corrette", exception1.getMessage());
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

        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-19", "2025-06-23");
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

        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-19", "2025-06-23");
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

    @Test
    public void TestVerificaCredenziali(){
        tripSync.creaViaggio(2,"Palermo", "Messina", "2025-06-19", "2025-06-23");
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(2));
        assertNotNull(tripSync.inserisciPartecipante("Barbara"));
        tripSync.confermaPartecipante();
        assertNotNull(tripSync.getViaggioCorrente().getGestore().getElencoPartecipazioni().get("Barbara"));

        assertEquals(true, tripSync.verificaCredenziali("Barbara", "bf231202"));
        //L'operazione non va a buon fine perchè il partecipante non è associato al viaggio
        assertEquals(false, tripSync.verificaCredenziali("Filippo", "ff270402"));
        //L'operazione non va a buon fine perchè le credenziali sono invalide
        assertEquals(false, tripSync.verificaCredenziali("Barbara", "bf2567"));

        /* La seguente operazione è andata a buon fine, ma è stata commentata per poter applicare tripSync.annullaPartecipazione()
        tripSync.confermaPartecipazione("Barbara");
        assertEquals(true, tripSync.verificaCredenziali("Barbara", "bf231202"));
        */

        tripSync.annullaPartecipazione("Barbara");
        //L'operazione non va a buon fine perchè la partecipazione è stata annullata
        assertEquals(false, tripSync.verificaCredenziali("Barbara", "bf231202"));

    }

    @Test
    public void TestCalcolaCosto(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-06-19", "2025-06-27");
        tripSync.aggiungiMezzo("aereo", 156.00);
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-06-25 13:30", "2025-06-25 15:30", 35.00);
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(3));

        //Non viene visualizzato alcun costo, perchè non ci sono partecipanti al viaggio
        tripSync.calcolaCosto();
    }

    @Test
    public void TestCalcolaCostoFestivo(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-12-20", "2025-12-30");
        tripSync.aggiungiMezzo("aereo", 156.00);
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-12-23 13:30", "2025-12-23 15:30", 35.00);
        tripSync.confermaInserimento();
        assertNotNull(tripSync.selezionaViaggio(3));
        tripSync.inserisciPartecipante("Filippo");
        tripSync.confermaPartecipante();

        //è stata applicata correttamente la regola di dominio R3
        tripSync.calcolaCosto();

    }

    @Test
    public void TestCalcolaCostoPartecipanteMinorenne(){
        tripSync.creaViaggio(3,"Firenze", "Bologna", "2025-12-20", "2025-12-30");
        tripSync.aggiungiMezzo("aereo", 156.00);
        tripSync.aggiungiTappa("Ristorante Barbieri", "2025-12-23 13:30", "2025-12-23 15:30", 35.00);
        tripSync.confermaInserimento();

        assertNotNull(tripSync.selezionaViaggio(3));
        tripSync.inserisciPartecipante("Filippo");
        tripSync.confermaPartecipante();
        tripSync.inserisciPartecipante("Federico");
        tripSync.confermaPartecipante();
        tripSync.calcolaCosto();

        //Annullando la partecipazione, vengono ricalcolati i costi
        tripSync.annullaPartecipazione("Filippo");
        tripSync.calcolaCosto();

    }


}