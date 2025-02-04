package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViaggioEffettuato extends Viaggio{
    private int codice;
    private String partenza;
    private String destinazione;
    private List<MezzoTrasporto> elencoMezzi;
    private List<Tappa> elencoTappe;
    private Map<String, Partecipante> elencoPartecipanti;
    private List<Feedback> elencoFeedback;
    private Tappa t;
    private Partecipante p;


    public ViaggioEffettuato(int codice, String partenza, String destinazione) {
        super(codice, partenza, destinazione);
        this.elencoMezzi = new ArrayList<>();
        this.elencoTappe = new ArrayList<>();
        this.elencoPartecipanti = new HashMap<>();
        this.elencoFeedback=new ArrayList<>();
    }






    public void ConfermaFeedback(Feedback f){
        elencoFeedback.add(f);
        System.out.println("Feedback aggiunto correttamente");
    }


}
