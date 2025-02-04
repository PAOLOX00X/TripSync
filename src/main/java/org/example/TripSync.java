package org.example;

import javax.naming.PartialResultException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TripSync {

    private static TripSync instance = null;
    private Map<Integer, Viaggio> elencoViaggi;
    private Map<String, Partecipante> elencoUtenti;
    private Map<Integer, ViaggioEffettuato> elencoViaggiEffettuati;
    private Viaggio v;
    private Viaggio viaggioSelezionato;
    private Partecipante p;


    TripSync() {
        this.elencoViaggi = new HashMap<>();
        this.elencoUtenti = new HashMap<>();
        this.elencoViaggiEffettuati= new HashMap<>();
        this.v= null;
        this.viaggioSelezionato=null;
        this.p=null;
        loadUtenti();
    }

    public void loadUtenti(){
        Partecipante p2=new Partecipante("Barbara", "bf231202");
        Partecipante p1=new Partecipante("Filippo", "ff270402");
        Partecipante p3=new Partecipante("Paolo", "pa251002");

        this.elencoUtenti.put("Filippo", p1);
        this.elencoUtenti.put("Barbara", p2);
        this.elencoUtenti.put("Paolo", p3);
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





}
