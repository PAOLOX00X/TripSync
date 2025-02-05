package org.example;

import java.util.HashMap;
import java.util.Map;

public class TripSync {

    private static TripSync instance = null;
    private Map<Integer, Viaggio> elencoViaggi;
    private Map<String, Partecipante> elencoUtenti;
    private Map<Integer, ViaggioEffettuato> elencoViaggiEffettuati;

    private Viaggio viaggioCorrente;
    private Partecipante partecipanteSelezionato;
    private Feedback feedbackCorrente;
    private Tappa tappaSelezionata;
    private ViaggioEffettuato viaggioEffettuatoSelezionato;



    TripSync() {
        this.elencoViaggi = new HashMap<>();
        this.elencoUtenti = new HashMap<>();
        this.elencoViaggiEffettuati=new HashMap<>();

        loadUtenti();
        loadViaggiEffettuati();
    }

    public void loadUtenti(){
        Partecipante p1=new Partecipante("Barbara", "bf231202");
        Partecipante p2=new Partecipante("Filippo", "ff270402");
        Partecipante p3=new Partecipante("Paolo", "pa251002");

        this.elencoUtenti.put("Filippo", p2);
        this.elencoUtenti.put("Barbara", p1);
        this.elencoUtenti.put("Paolo", p3);

        System.out.println("Utenti caricati con successo");

    }

    public void loadViaggiEffettuati(){
        ViaggioEffettuato ve1=new ViaggioEffettuato(001, "Catania", "Milano" );
        ViaggioEffettuato ve2=new ViaggioEffettuato(002, "Catania", "Madrid" );
        ViaggioEffettuato ve3=new ViaggioEffettuato(003, "Palermo", "Torino" );

        MezzoTrasporto mt1= new MezzoTrasporto("aereo", 120.00);
        MezzoTrasporto mt2= new MezzoTrasporto("aereo", 110.00);
        MezzoTrasporto mt3=new MezzoTrasporto("pullman", 20.00);
        MezzoTrasporto mt4= new MezzoTrasporto("nave", 50.00);
        MezzoTrasporto mt5= new MezzoTrasporto("pullman", 20.00);

        Tappa t1= new Tappa("Pinacoteca di Brera", "2024-06-06 10:30", "2024-06-06 12:30", 20.00);
        Tappa t2= new Tappa("Duomo", "2024-06-06 14:30", "2024-06-06 15:30", 0.00);
        Tappa t3= new Tappa("Parco del rey", "2023-07-07 10:00", "2023-07-07 12:00", 10.00);
        Tappa t4= new Tappa ("Parco del retiro", "2023-07-07 15:00", "2023-07-07 16:00", 10.00);
        Tappa t5= new Tappa("Museo Egizio", "2024-10-10 16:00", "2024-10-10 18:00", 20.00);
        Tappa t6= new Tappa("Museo del cinema", "2024-10-11 10:00", "2024-10-11 12:00", 25.00);

        ve1.getElencoMezzi().add(mt1);
        ve2.getElencoMezzi().add(mt2);
        ve2.getElencoMezzi().add(mt3);
        ve3.getElencoMezzi().add(mt4);
        ve3.getElencoMezzi().add(mt5);

        ve1.getElencoTappe().add(t1);
        ve1.getElencoTappe().add(t2);
        ve2.getElencoTappe().add(t3);
        ve2.getElencoTappe().add(t4);
        ve3.getElencoTappe().add(t5);
        ve3.getElencoTappe().add(t6);


        Partecipante p1= elencoUtenti.get("Barbara");
        Partecipante p2= elencoUtenti.get("Filippo");
        Partecipante p3=elencoUtenti.get("Paolo");

        ve1.getElencoPartecipanti().put("Barbara", p1);
        ve1.getElencoPartecipanti().put("Filippo", p2);
        ve1.getElencoPartecipanti().put("Paolo", p3);



        ve2.getElencoPartecipanti().put("Paolo", p3);
        ve2.getElencoPartecipanti().put("Barbara", p1);



        ve3.getElencoPartecipanti().put("Paolo", p3);
        ve3.getElencoPartecipanti().put("Filippo", p2);


        elencoViaggiEffettuati.put(001, ve1);
        elencoViaggiEffettuati.put(002, ve2);
        elencoViaggiEffettuati.put(003, ve3);

        System.out.println("Viaggi caricati con successo!");


    }


    public static TripSync getInstance() {
        if (instance == null) {
            instance = new TripSync();
        }
        return instance;
    }

    public void reset() {
        this.elencoViaggi.clear();
        this.elencoUtenti.clear();
        this.elencoViaggiEffettuati.clear();


        this.viaggioCorrente = null;
        this.partecipanteSelezionato = null;
        this.feedbackCorrente = null;
        this.tappaSelezionata = null;
        this.viaggioEffettuatoSelezionato = null;


        loadUtenti();
        loadViaggiEffettuati();
    }

    public Map<Integer, Viaggio> getElencoViaggi() {
        return elencoViaggi;
    }

    public Viaggio getViaggioCorrente() {
        return viaggioCorrente;
    }

    public Map<String, Partecipante> getElencoUtenti() {
        return elencoUtenti;
    }

    public ViaggioEffettuato getViaggioEffettuatoSelezionato() {
        return viaggioEffettuatoSelezionato;
    }


    public void creaViaggio(int codice, String partenza, String destinazione) {
        if(elencoViaggi.containsKey(codice)==true){
            System.out.println("Impossibile creare il viaggio perche il codice esiste gia");

        }
        else{
            viaggioCorrente= new Viaggio(codice, partenza, destinazione);
            System.out.println("Viaggio creato correttamente");
        }

    }

    public void aggiungiMezzo(String nome, double costo) {

        viaggioCorrente.aggiungiMezzo(nome, costo);
    }

    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {
        viaggioCorrente.aggiungiTappa(luogo, inizio, fine, costo);
    }

    public void confermaInserimento() {
        Integer codice=viaggioCorrente.getCodice();
        elencoViaggi.put(codice, viaggioCorrente);
        System.out.println("Viaggio aggiunto correttamente all'elenco");
        viaggioCorrente=null;
    }


    public Viaggio selezionaViaggio(int codice) {

        if(elencoViaggi.containsKey(codice)==true){
            viaggioCorrente= elencoViaggi.get(codice);
            return viaggioCorrente;
        }
        else return null;
    }

    public Partecipante inserisciPartecipante(String nomeUtente) {

        if(elencoUtenti.containsKey(nomeUtente)==true){
            partecipanteSelezionato= elencoUtenti.get(nomeUtente);
            return partecipanteSelezionato;
        }
        else return null;
    }

    public void confermaPartecipante() {
        String nomeUtente=partecipanteSelezionato.getNomeUtente();
        viaggioCorrente.confermaPartecipante(nomeUtente, partecipanteSelezionato);
    }



    public void visualizzaItinerario() {

        viaggioCorrente.visualizzaItinerario();
    }


    public Tappa selezionaTappa(String luogo, String inizio, String fine, double costo){
        tappaSelezionata=viaggioCorrente.selezionaTappa(luogo, inizio, fine, costo);

        return tappaSelezionata;
    }

    public void modificaTappa(String luogo, String inizio, String fine, double costo){
        viaggioCorrente.modificaTappa(tappaSelezionata, luogo, inizio, fine, costo);
    }

    public void eliminaTappa(){
        viaggioCorrente.eliminaTappa(tappaSelezionata);
    }

    public ViaggioEffettuato selezionaViaggioEffettuato(Integer codice){
        if(elencoViaggiEffettuati.containsKey(codice)==true){
            viaggioEffettuatoSelezionato= elencoViaggiEffettuati.get(codice);
            return viaggioEffettuatoSelezionato;
        }
        else return null;
    }

    public Partecipante inserisciCredenziali(String nomeUtente, String password){
        partecipanteSelezionato=viaggioEffettuatoSelezionato.getElencoPartecipanti().get(nomeUtente);
        if (partecipanteSelezionato != null && partecipanteSelezionato.getPassword().equals(password)) {

            return partecipanteSelezionato;
        } else {
            System.out.println("Credenziali non valide o partecipante non trovato.");
            return null;
        }
    }

    public Feedback inserisciFeedback(Integer numeroStelle, String descrizione){
        feedbackCorrente=partecipanteSelezionato.inserisciFeedback(numeroStelle, descrizione);
        return feedbackCorrente;
    }

    public  void confermaFeedback(){
        viaggioEffettuatoSelezionato.confermaFeedback(feedbackCorrente);
    }




}
