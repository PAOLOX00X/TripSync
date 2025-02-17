package org.example;
public class Tappa {

    private String luogo;
    private String inizio;
    private String fine;
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

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Tappa{" +
                "luogo='" + luogo + '\'' +
                ", inizio='" + inizio + '\'' +
                ", fine='" + fine + '\'' +
                ", costo=" + costo +
                '}';
    }
}
