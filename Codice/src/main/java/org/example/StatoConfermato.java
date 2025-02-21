package org.example;

public class StatoConfermato extends StatoPartecipazione{
    @Override
    public void conferma(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("Partecipazione gia' confermata per " + nomeUtente);
    }

    @Override
    public void annulla(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("Non e' possibile annullare una partecipazione confermata per " + nomeUtente);
    }

}