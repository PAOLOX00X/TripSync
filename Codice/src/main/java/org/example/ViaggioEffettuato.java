package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViaggioEffettuato extends Viaggio{

    private List<Feedback> elencoFeedback;


    public ViaggioEffettuato(int codice, String partenza, String destinazione) {
        super(codice, partenza, destinazione);

        this.elencoFeedback=new ArrayList<>();
    }


    public void confermaFeedback(Feedback f){
        elencoFeedback.add(f);
        System.out.println("Feedback aggiunto correttamente");
    }
    @Override
    public void visualizzaItinerario() {
        super.visualizzaItinerario();
        for(int i=0;i<elencoFeedback.size();i++){
            Feedback f=elencoFeedback.get(i);
            System.out.println("NumStelle: "+f.getNumeroStelle()+" descrizione: "+f.getDescrizione());
        }

    }


}
