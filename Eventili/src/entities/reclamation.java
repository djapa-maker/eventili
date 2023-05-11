package entities;

import java.sql.Timestamp;

public class reclamation {
    private int id;
    private String description,titre;
    private Timestamp dateheure;
    private Personne p;
    String status;
    public reclamation() {
    }
    
    
   
    public reclamation(int id, Timestamp dateheure,String description, String titre,Personne p,String status){
        this.id = id;
        this.p=p;
        this.description = description;
        this.titre = titre;
        this.dateheure = dateheure;
        this.status = status;
    }
    public reclamation(int id,String description, String titre,Personne p,String status){
        this.id = id;
        this.p=p;
        this.description = description;
        this.titre = titre;
        this.dateheure = new Timestamp(System.currentTimeMillis());
        this.status = status;
    }
    public reclamation(String description, String titre,Personne p){
        this.p=p;
        this.description = description;
        this.titre = titre;
        this.dateheure = new Timestamp(System.currentTimeMillis());
        this.status = "En Attente";
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String Status){
        this.status = Status;
    }
    public int getId(){
        return this.id;
    }
  
    public Timestamp getTimeStamp(){
        return this.dateheure;
    }
    public String getDescription(){
        return this.description;
    }
    public String getTitre(){
        return this.titre;
    }
    public void setTimeStamp(Timestamp timeStamp){
        this.dateheure = timeStamp;
    }
    public void setDescriptipn(String description){
        this.description = description;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }
    public void setId(int id){
        this.id = id;
    }
    public Personne getP() {
        return p;
    }
}
