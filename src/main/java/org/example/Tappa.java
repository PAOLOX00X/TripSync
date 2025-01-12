package org.example;
public class Tappa {

    private String luogo;
    private String inizio;   // semplificazione: stringa invece di Date
    private String fine;     // idem
    private double costo;

    public Tappa(String luogo, String inizio, String fine, double costo) {
        this.luogo = luogo;
        this.inizio = inizio;
        this.fine = fine;
        this.costo = costo;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getInizio() {
        return inizio;
    }

    public String getFine() {
        return fine;
    }

    public double getCosto() {
        return costo;
    }
}
