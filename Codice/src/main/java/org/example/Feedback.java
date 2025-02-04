package org.example;

public class Feedback {
    private int numeroStelle;
    private String descrizione;

    public Feedback(int numeroStelle, String descrizione) {
        this.numeroStelle = numeroStelle;
        this.descrizione= descrizione;
    }


    @Override
    public String toString() {
        return "Feedback{" +
                "numeroStelle=" + numeroStelle +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
