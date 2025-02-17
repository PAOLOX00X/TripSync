package org.example;

import java.util.ArrayList;
import java.util.List;

public class ViaggioEffettuato extends Viaggio{

    private List<Feedback> elencoFeedback;


    public ViaggioEffettuato(int codice, String partenza, String destinazione, String dataInizio, String dataFine) {
        super(codice, partenza, destinazione, dataInizio, dataFine);

        this.elencoFeedback=new ArrayList<>();
    }


    public void confermaFeedback(Feedback f){
        elencoFeedback.add(f);
        System.out.println("Feedback aggiunto correttamente");
    }
    @Override
    public void visualizzaItinerario() {
        super.visualizzaItinerario();
        System.out.println("Elenco Feedback: ");
        for(int i=0;i<elencoFeedback.size();i++){
            Feedback f=elencoFeedback.get(i);
            System.out.println("NumStelle: "+f.getNumeroStelle()+" descrizione: "+f.getDescrizione());
        }

    }


}
