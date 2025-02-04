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

    public List<Tappa> getElencoTappe() {
        return elencoTappe;
    }

    public Map<String, Partecipante> getElencoPartecipanti() {
        return elencoPartecipanti;
    }



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
        System.out.println("Aggiunto il mezzo all'elenco ");
    }

    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dataInizio = LocalDateTime.parse(inizio, formatter);
        LocalDateTime dataFine = LocalDateTime.parse(fine, formatter);
        if(dataInizio.equals(dataFine)){
            System.out.println("Errore: la data di inizio e fine e' la stessa");
            return;
        }

        if(dataInizio.isAfter(dataFine)){
            System.out.println("Errore: il software non è una macchina del tempo. Inserire date corrette");
            return;
        }

        for (Tappa tappa : elencoTappe) {
            if (tappa.getInizio().equals(inizio) && tappa.getFine().equals(fine)) {
                System.out.println("Errore: Esiste già una tappa con le stesse date e orari.");
                return;
            }

        }
        Tappa t=new Tappa(luogo, inizio, fine, costo);
        elencoTappe.add(t);
        System.out.println("Tappa aggiunta correttamente all'elenco");
    }

    public void confermaPartecipante(String nomeUtente, Partecipante p) {
            if(elencoPartecipanti.containsKey(nomeUtente)){
                System.out.println("errore! partecipante già presente");
            }
            else {
                elencoPartecipanti.put(nomeUtente, p);
                System.out.println("Partecipante aggiunto correttamente all'elenco ");
            }
    }


    public void visualizzaItinerario() {
        System.out.println("Codice: "+getCodice()+" Partenza: "+getPartenza()+" Destinazione: "+getDestinazione());
        for(int i=0;i<elencoTappe.size();i++){
            Tappa t=elencoTappe.get(i);
            System.out.println("Luogo: "+t.getLuogo()+" Inizio: "+t.getInizio()+" Fine: "+t.getFine()+" Costo: "+t.getCosto());
        }
    }





}
