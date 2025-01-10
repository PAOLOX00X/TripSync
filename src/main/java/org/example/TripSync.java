package org.example;

import javax.naming.PartialResultException;
import java.util.HashMap;
import java.util.Map;

public class TripSync {

    private static TripSync instance = null;
    private Map<Integer, Viaggio> elencoViaggi;
    private Map<String, Partecipante> elencoUtenti;



    private TripSync() {
        this.elencoViaggi = new HashMap<>();
        this.elencoUtenti = new HashMap<>();
        this.viaggioSelezionato = null;
    }

    public static TripSync getInstance() {
        if (instance == null) {
            instance = new TripSync();
        }
        return instance;
    }


    private Viaggio v;
    public void creaViaggio(int codice, String partenza, String destinazione) {
        if(elencoViaggi.containsKey(codice)==true){
            System.out.println("Impossibile creare il viaggio perche il codice esiste gia");

        }
        else v= new Viaggio(codice, partenza, destinazione);
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
    }


    private Viaggio viaggioSelezionato;
    public Viaggio selezionaViaggio(int codice) {

        if(elencoViaggi.containsKey(codice)==true){
            viaggioSelezionato= elencoViaggi.get(codice);
            return viaggioSelezionato;
        }
        else return null;
    }


    private Partecipante p;
    public Partecipante inserisciPartecipante(String nomeUtente) {

        if(elencoUtenti.containsKey(nomeUtente)==true){
            p= elencoUtenti.get(nomeUtente);
            return p;
        }
        else return null;
    }

    public void confermaPartecipante() {
        String nomeUtente=p.getNomeUtente();
        v.confermaPartecipante(nomeUtente, p);

    }

    public Viaggio recuperaViaggio(int codice) {
        if(elencoViaggi.containsKey(codice)==true){
            viaggioSelezionato= elencoViaggi.get(codice);
            return viaggioSelezionato;
        }
        else return null;
    }

    public void visualizzaItinerario() {
        viaggioSelezionato.visualizzaItinerario();
    }


}
