package org.example;
public class Partecipante {

    private String nomeUtente;
    private String password;
    private Feedback feedbackCorrente;

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

    public Feedback inserisciFeedback(Integer numeroStelle, String descrizione){
        if(numeroStelle>=0 && numeroStelle<=5){
            feedbackCorrente= new Feedback(numeroStelle, descrizione);
            return feedbackCorrente;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nome utente: " + nomeUtente + ", Password: " + password;
    }

}


