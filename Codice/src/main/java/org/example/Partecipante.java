package org.example;
public class Partecipante {

    private String nomeUtente;
    private String password;

    public Partecipante(String nomeUtente, String password) {

        this.nomeUtente = nomeUtente;
        this.password = password;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }
}
