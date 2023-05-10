package com.company.entities;

public class Transaction {

    private int id;
    private String mode;

    public Transaction() {
    }

    public Transaction(int id, String mode) {
        this.id = id;
        this.mode = mode;
    }

    public Transaction(String mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


}