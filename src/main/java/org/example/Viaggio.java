package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Viaggio {

    private int codice;
    private String partenza;
    private String destinazione;
    private List<MezzoTrasporto> elencoMezzi;
    private List<Tappa> elencoTappe;
    private Map<String, Partecipante> elencoPartecipanti;
    private Tappa t;


    public Viaggio(int codice, String partenza, String destinazione) {
        this.codice = codice;
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.elencoMezzi = new ArrayList<>();
        this.elencoTappe = new ArrayList<>();
        this.elencoPartecipanti = new HashMap<>();
    }


    public void aggiungiMezzo(String nome, double costo) {
        MezzoTrasporto mt;
        mt=new MezzoTrasporto(nome, costo);
        elencoMezzi.add(mt);
        System.out.println("Aggiunto il mezzo con mezzo "+mt.getNome()+" e costo "+mt.getCosto());
    }

    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dataInizio = LocalDateTime.parse(inizio, formatter);
        LocalDateTime dataFine = LocalDateTime.parse(fine, formatter);
        if(dataInizio.equals(dataFine)){
            System.out.println("Errore: la data di inizio e fine e' la stessa");
            return;
        }

        for (Tappa tappa : elencoTappe) {
            if (tappa.getInizio().equals(dataInizio) && tappa.getFine().equals(dataFine)) {
                System.out.println("Errore: Esiste già una tappa con le stesse date e orari.");
                return;
            }
        }
        Tappa t=new Tappa(luogo, inizio, fine, costo);
        elencoTappe.add(t);
    }

    public void confermaPartecipante(String nomeUtente, Partecipante p) {
            elencoPartecipanti.put(nomeUtente, p);
    }


    public void visualizzaItinerario() {
        for(int i=0;i<elencoTappe.size();i++){
            t=elencoTappe.get(i);
            System.out.println(t);
        }
    }


    public int getCodice() {
        return codice;
    }

    public String getPartenza() {
        return partenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public List<MezzoTrasporto> getElencoMezzi() {
        return elencoMezzi;
    }
}
