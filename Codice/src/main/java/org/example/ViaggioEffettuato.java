package org.example;

public class ViaggioEffettuato extends Viaggio {

    private int codice;
    private String partenza;
    private String destinazione;
    private List<MezzoTrasporto> elencoMezzi;
    private List<Tappa> elencoTappe;
    private Map<String, Partecipante> elencoPartecipanti;
    private Tappa t;
    private List<Feedback> elencoFeedback;

    public ViaggioEffettuato(int codice, String partenza, String destinazione) {
        super(codice, partenza, destinazione);
        this.elencoMezzi = new ArrayList<>();
        this.elencoTappe = new ArrayList<>();
        this.elencoPartecipanti = new HashMap<>();
        this.elencoFeedback = new ArrayList<>();
    }

}