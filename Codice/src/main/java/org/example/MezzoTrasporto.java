package org.example;
public class MezzoTrasporto {

    private String nome;
    private double costo;

    public MezzoTrasporto(String nome, double costo) {
        this.nome = nome;
        this.costo = costo;
    }

    public String getNome() {
        return nome;
    }

    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return "MezzoTrasporto{" +
                "nome='" + nome + '\'' +
                ", costo=" + costo +
                '}';
    }
}