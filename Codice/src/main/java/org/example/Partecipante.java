package org.example;
public class Partecipante {

    private String nomeUtente;
    private String password;
    public Partecipante(String nomeUtente, String password) {

        this.nomeUtente = nomeUtente;
        this.password= password;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public Feedback InserisciFeedback(Integer numStelle, String Descrizione){
        if(numStelle>=0 && numStelle<=5){
            Feedback f= new Feedback(numStelle, Descrizione);
            return f;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nome utente: " + nomeUtente + ", Password: " + password;
    }

}


