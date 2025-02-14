package org.example;
import java.util.HashMap;
import java.util.Map;

public class GestorePartecipazioni {
    private Map <String, StatoPartecipazione> elencoPartecipazioni= new HashMap<>();

    public Map<String, StatoPartecipazione> getElencoPartecipazioni(){
        return elencoPartecipazioni;
    }

    public void confermaPartecipazione(String nomeUtente){
        StatoPartecipazione stato= elencoPartecipazioni.get(nomeUtente);
        if (stato != null) {
            stato.conferma(this, nomeUtente);
        } else {
            System.out.println("Partecipazione non trovata per " + nomeUtente);

        }
    }

    public void annullaPartecipazione(String nomeUtente){
        StatoPartecipazione stato = elencoPartecipazioni.get(nomeUtente);
        if (stato != null) {
            stato.annulla(this, nomeUtente);
        } else {
            System.out.println("Partecipazione non trovata per " + nomeUtente);
        }
    }

    public void cambiaStato(String nomeUtente, StatoPartecipazione stato){
        elencoPartecipazioni.put(nomeUtente, stato);
    }


}
