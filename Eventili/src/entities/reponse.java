package entities;

import java.sql.Timestamp;

public class reponse {

    private int id_rep;
    private Personne sender;
    private reclamation rec;
    private String message;
    private Timestamp dateHeure;
    private boolean isEdited;

    public reponse() {
    }

    public reponse(int id_rep, Personne sender, reclamation rec, Timestamp dateHeure, String message, boolean isEdited) {
        this.id_rep = id_rep;
        this.sender = sender;
        this.rec = rec;
        this.message = message;
        this.dateHeure = dateHeure;
        this.isEdited = isEdited;
    }

    public reponse(Personne sender, reclamation rec, String message) {
        this.sender = sender;
        this.rec = rec;
        this.message = message;
        this.isEdited = false;
        this.dateHeure = new Timestamp(System.currentTimeMillis());
    }

    public int getIdRep() {
        return this.id_rep;
    }

    public Personne getSender() {
        return sender;
    }

    public reclamation getRec() {
        return rec;
    }
    public boolean getIsEdited() {
        return this.isEdited;
    }

    public Timestamp getTimeStamp() {
        return this.dateHeure;
    }

    public String getMessage() {
        return this.message;
    }

    public void setIdRep(int id_rep) {
        this.id_rep = id_rep;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(Timestamp timestamp) {
        this.dateHeure = timestamp;
    }

    public void setIsEdited(boolean isEdited) {
       this.isEdited = isEdited;
    }
    public void setSender(Personne sender) {
        this.sender = sender;
    }
}
