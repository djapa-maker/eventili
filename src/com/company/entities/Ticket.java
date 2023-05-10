package com.company.entities;

public class Ticket {

    private int id;
    private Transaction transaction;
    private int nbTick;
    private float prix;
    private String status;
    private Evenement evenement;

    public Ticket() {
    }

    public Ticket(int id, Transaction transaction, int nbTick, float prix, String status, Evenement evenement) {
        this.id = id;
        this.transaction = transaction;
        this.nbTick = nbTick;
        this.prix = prix;
        this.status = status;
        this.evenement = evenement;
    }

    public Ticket(Transaction transaction, int nbTick, float prix, String status, Evenement evenement) {
        this.transaction = transaction;
        this.nbTick = nbTick;
        this.prix = prix;
        this.status = status;
        this.evenement = evenement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getNbTick() {
        return nbTick;
    }

    public void setNbTick(int nbTick) {
        this.nbTick = nbTick;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }


}