package org.example;

import javax.naming.PartialResultException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TripSync {

    private static TripSync instance = null;
    private Map<Integer, Viaggio> elencoViaggi;
    private Map<String, Partecipante> elencoUtenti;
    private Viaggio v;
    private Viaggio viaggioSelezionato;
    private Partecipante p;
    private Feedback f;
    private Tappa t;
    private ViaggioEffettuato ve;
    private Map<Integer, ViaggioEffettuato> elencoViaggiEffettuati;



    TripSync() {
        this.elencoViaggi = new HashMap<>();
        this.elencoUtenti = new HashMap<>();
        this.elencoViaggiEffettuati=new HashMap<>();

        loadUtenti();
        loadViaggiEffettuati();
    }

    public void loadUtenti(){
        Partecipante p2=new Partecipante("Barbara", "bf231202");
        Partecipante p1=new Partecipante("Filippo", "ff270402");
        Partecipante p3=new Partecipante("Paolo", "pa251002");

        this.elencoUtenti.put("Filippo", p1);
        this.elencoUtenti.put("Barbara", p2);
        this.elencoUtenti.put("Paolo", p3);
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

    public Map<Integer, Viaggio> getElencoViaggi() {
        return elencoViaggi;
    }

    public Viaggio getV() {
        return v;
    }


    public Map<String, Partecipante> getElencoUtenti() {
        return elencoUtenti;
    }

    public Partecipante getP() {
        return p;
    }

    public Viaggio getViaggioSelezionato() {
        return viaggioSelezionato;
    }


    public void creaViaggio(int codice, String partenza, String destinazione) {
        if(elencoViaggi.containsKey(codice)==true){
            System.out.println("Impossibile creare il viaggio perche il codice esiste gia");

        }
        else{
            v= new Viaggio(codice, partenza, destinazione);
            System.out.println("Viaggio creato correttamente");
        }

    }

    public void aggiungiMezzo(String nome, double costo) {
        v.aggiungiMezzo(nome, costo);
    }

    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {
        v.aggiungiTappa(luogo, inizio, fine, costo);
    }

    public void confermaInserimento() {
        Integer codice=v.getCodice();
        elencoViaggi.put(codice, v);
        System.out.println("Viaggio aggiunto correttamente all'elenco");
    }


    public Viaggio selezionaViaggio(int codice) {

        if(elencoViaggi.containsKey(codice)==true){
            viaggioSelezionato= elencoViaggi.get(codice);
            return viaggioSelezionato;
        }
        else return null;
    }

    public Partecipante inserisciPartecipante(String nomeUtente) {

        if(elencoUtenti.containsKey(nomeUtente)==true){
            p= elencoUtenti.get(nomeUtente);
            return p;
        }
        else return null;
    }

    public void confermaPartecipante() {
        String nomeUtente=p.getNomeUtente();
        viaggioSelezionato.confermaPartecipante(nomeUtente, p);
    }



    public void visualizzaItinerario() {
        viaggioSelezionato.visualizzaItinerario();
    }


    public Tappa SelezionaTappa(String Luogo, String Inizio, String Fine, double Costo){
        t=v.SelezionaTappa(Luogo, Inizio, Fine, Costo);
        return t;
    }

    public void ModificaTappa(String Luogo, String Inizio, String Fine, double Costo){
        v.ModificaTappa(t, Luogo, Inizio, Fine, Costo);
    }

    public void EliminaTappa(){
        v.EliminaTappa(t);
    }


    public ViaggioEffettuato SelezionaViaggioEffettuato(Integer codice){
        ve=elencoViaggiEffettuati.get(codice);
        return ve;
    }

    public Partecipante InserisciCredenziali(String nomeUtente, String Password){
        p=ve.InserisciCredenziali(nomeUtente, Password);
        return p;
    }

    public Feedback InsersciFeedback(Integer numStelle, String Descrizione){
        f=p.InserisciFeedback(numStelle, Descrizione);
        return f;
    }

    public  void ConfermaFeedback(){
        ve.ConfermaFeedback(f);
    }




}
