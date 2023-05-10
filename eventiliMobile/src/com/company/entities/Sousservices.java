/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.entities;

/**
 *
 * @author HP
 */
public class Sousservices {

    private float id;
    private float prix;
    private String nom;
    private String description;
    private int note;
    private Personne pers;
    private Services c;
    private EventCateg cev;
    public Sousservices(float id, float prix, String nom, String description, int note, Personne pers, Services c,EventCateg ev) {
        this.id = id;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.note = note;
        this.pers = pers;
        this.c = c;
        this.cev = ev;
    }
     public Sousservices( float prix, String nom, String description, int note) {
  
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.note = note;
   
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Personne getPers() {
        return pers;
    }

    public void setPers(Personne pers) {
        this.pers = pers;
    }

    public Services getC() {
        return c;
    }

    public void setC(Services c) {
        this.c = c;
    }

    public EventCateg getCev() {
        return cev;
    }

    public void setCev(EventCateg cev) {
        this.cev = cev;
    }

    public Sousservices() {
    }

   

    
}