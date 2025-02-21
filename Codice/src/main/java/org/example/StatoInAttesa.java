package org.example;

public class StatoInAttesa extends StatoPartecipazione{
    @Override
    public void conferma(GestorePartecipazioni gestore, String nomeUtente) {
        gestore.cambiaStato(nomeUtente, new StatoConfermato());
        System.out.println("Partecipazione confermata per " + nomeUtente);
    }

    @Override
    public void annulla(GestorePartecipazioni gestore, String nomeUtente) {
        gestore.cambiaStato(nomeUtente, new StatoAnnullato());
        System.out.println("Partecipazione annullata per " + nomeUtente);
    }

}