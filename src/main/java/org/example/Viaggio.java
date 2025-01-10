package org.example;
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
    }

    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {
        Tappa t=new Tappa(luogo, inizio, fine, costo);
        elencoTappe.add(t);
    }

    public void confermaPartecipante(String nomeUtente, Partecipante p) {
            elencoPartecipanti.put(nomeUtente, p);
    }

    Tappa t;
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


}
