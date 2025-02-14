package org.example;

public class StatoAnnullato extends StatoPartecipazione{
    @Override
    public void conferma(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("❌ Non è possibile confermare una partecipazione annullata per " + nomeUtente);
    }

    @Override
    public void annulla(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("⚠️ Partecipazione già annullata per " + nomeUtente);
    }

}
