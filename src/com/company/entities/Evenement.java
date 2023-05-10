package com.company.entities;

import java.util.Date;

public class Evenement {

    private int id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private String descriptionEv;
    private String type;
    private String visibilite;
    private int limiteparticipant;
    private float prix;

    public Evenement() {
    }

    public Evenement(int id, String titre, Date dateDebut, Date dateFin, String descriptionEv, String type, String visibilite, int limiteparticipant, float prix) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descriptionEv = descriptionEv;
        this.type = type;
        this.visibilite = visibilite;
        this.limiteparticipant = limiteparticipant;
        this.prix = prix;
    }

    public Evenement(String titre, Date dateDebut, Date dateFin, String descriptionEv, String type, String visibilite, int limiteparticipant, float prix) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descriptionEv = descriptionEv;
        this.type = type;
        this.visibilite = visibilite;
        this.limiteparticipant = limiteparticipant;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescriptionEv() {
        return descriptionEv;
    }

    public void setDescriptionEv(String descriptionEv) {
        this.descriptionEv = descriptionEv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }

    public int getLimiteparticipant() {
        return limiteparticipant;
    }

    public void setLimiteparticipant(int limiteparticipant) {
        this.limiteparticipant = limiteparticipant;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


}