/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author HP
 */
public class Sousservices {

    private int id_serv;
    private float prix_serv;
    private String nom_serv;
    private String description_serv;
    private String icon;
    private int note;
    private Personne pers;
    private Services c;

    public Sousservices(int id_serv, float prix_serv, String nom_serv, String description_service, String icon, int note, Personne pers,Services c) {
        this.id_serv = id_serv;
        this.prix_serv = prix_serv;
        this.nom_serv = nom_serv;
        this.description_serv = description_service;
        this.icon = icon;
        this.note = note;
        this.pers = pers;
        this.c=c;
        
        
    }

    public Sousservices(float prix_serv, String nom_serv, String description_serv, String icon, int note, Personne pers, Services c) {
        this.prix_serv = prix_serv;
        this.nom_serv = nom_serv;
        this.description_serv = description_serv;
        this.icon = icon;
        this.note = note;
        this.pers = pers;
        this.c = c;
    }
    
    

    public Sousservices() {
    }

    public int getId_serv() {
        return id_serv;
    }

    public float getPrix_serv() {
        return prix_serv;
    }

    public void setPrix_serv(float prix_serv) {
        this.prix_serv = prix_serv;
    }

    public String getNom_serv() {
        return nom_serv;
    }

    public void setNom_serv(String nom_serv) {
        this.nom_serv = nom_serv;
    }

    public String getDescription_serv() {
        return description_serv;
    }

    public void setDescription_serv(String description_service) {
        this.description_serv = description_service;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Services getC() {
        return c;
    }
    
    

    @Override
    public String toString() {
        return "Services{" + "id_serv=" + id_serv + ", prix_serv=" + prix_serv + ", nom_serv=" + nom_serv + ", description_serv=" + description_serv + ", icon=" + icon + ", note=" + note + ", id_pers=" + this.getPers().getId_pers() +"catégorie="+this.getC().getIdcateg_service()+ '}';
    }

}
