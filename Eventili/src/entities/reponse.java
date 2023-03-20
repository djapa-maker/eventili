package entities;

import java.sql.Timestamp;

public class reponse {
    private int id_rep;
    private Personne sender;
    private reclamation rec;
    private String message;
    private Timestamp timeStamp;

    public reponse() {
    }
    
    
    
    public reponse(int id_rep, Personne sender,reclamation rec,Timestamp timeStamp, String message){
        this.id_rep = id_rep;
        this.sender = sender;
        this.rec = rec;
        this.message = message;
        this.timeStamp = timeStamp;
    }
    public reponse(int id_rep, Personne sender, reclamation rec, String message){
        this.id_rep = id_rep;
        this.sender = sender;
        this.rec = rec;
        this.message = message;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }
    public int getIdRep(){
        return this.id_rep;
    }
   
    public Timestamp getTimeStamp(){
        return this.timeStamp;
    }
    public String getMessage(){
        return this.message;
    }
    public void setIdRep(int id_rep){
        this.id_rep = id_rep;
    }
   
    public void setMessage(String message){
        this.message = message;
    }
    public void setTimeStamp(Timestamp timestamp){
        this.timeStamp = timestamp;
    }

    public int getId_rep() {
        return id_rep;
    }

    public Personne getSender() {
        return sender;
    }

    public reclamation getRec() {
        return rec;
    }

    @Override
    public String toString() {
        return "reponse{" + "id_rep=" + id_rep + ", sender=" + sender + ", rec=" + rec + ", message=" + message + ", timeStamp=" + timeStamp + '}';
    }
    
    
    
}
