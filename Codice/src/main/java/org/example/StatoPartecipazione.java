package org.example;

public abstract class StatoPartecipazione {
    public void conferma(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("Errore: Operazione non consentita in questo stato.");
    }

    public void annulla(GestorePartecipazioni gestore, String nomeUtente) {
        System.out.println("Errore: Operazione non consentita in questo stato.");
    }
}