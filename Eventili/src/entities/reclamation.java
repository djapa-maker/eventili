package entities;

import java.sql.Timestamp;

public class reclamation {
    private int id;
    private String description,titre;
    private Timestamp timeStamp;
    private Personne p;

    public reclamation() {
    }
    
    
   
    public reclamation(int id, Timestamp timeStamp,String description, String titre,Personne p){
        this.id = id;
        this.p=p;
        this.description = description;
        this.titre = titre;
        this.timeStamp = timeStamp;
    }
    public reclamation(int id,String description, String titre,Personne p){
        this.id = id;
        this.p=p;
        this.description = description;
        this.titre = titre;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }
    public int getId(){
        return this.id;
    }
  
    public Timestamp getTimeStamp(){
        return this.timeStamp;
    }
    public String getDescription(){
        return this.description;
    }
    public String getTitre(){
        return this.titre;
    }
    public void setTimeStamp(Timestamp timeStamp){
        this.timeStamp = timeStamp;
    }
    public void setDescriptipn(String description){
        this.description = description;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }

    public Personne getP() {
        return p;
    }

    @Override
    public String toString() {
        return "reclamation{" + "id=" + id + ", description=" + description + ", titre=" + titre + ", timeStamp=" + timeStamp + ", p=" + p + '}';
    }
    
    
}
