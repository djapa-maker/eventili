package entities;

import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class SousServices {
    private int id_sousServ;
    private float prix_serv;
    private String nom_serv;
    private String description_serv;
    private float note;
    private Personne pers;
    private Service s;
    private ArrayList<EventCateg> list=new ArrayList<>();

    public SousServices() {}
    
    public SousServices(int id_sousServ, float prix_serv, String nom_serv, String description_serv,  float note, Personne pers, Service c, ArrayList<EventCateg> ec) {
        this.id_sousServ = id_sousServ;
        this.prix_serv = prix_serv;
        this.nom_serv = nom_serv;
        this.description_serv = description_serv;
        this.note = note;
        this.pers = pers;
        this.s = c;
        this.list = ec;
    }

    public SousServices(float prix_serv, String nom_serv, String description_serv,  float note, Personne pers, Service s) {
        this.prix_serv = prix_serv;
        this.nom_serv = nom_serv;
        this.description_serv = description_serv;
       
        this.note = note;
        this.pers = pers;
        this.s = s;
    }

    public SousServices(float prix_serv, String nom_serv, String description_serv, float note) {
        this.prix_serv = prix_serv;
        this.nom_serv = nom_serv;
        this.description_serv = description_serv;
        
        this.note = note;
    }

    public SousServices(int id_sousServ,String nom_serv) {
         this.id_sousServ = id_sousServ;
        this.nom_serv = nom_serv;
   
    }
    
    public int getId_sousServ() {
        return id_sousServ;
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

    public void setDescription_serv(String description_serv) {
        this.description_serv = description_serv;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Personne getPers() {
        return pers;
    }

    public void setPers(Personne pers) {
        this.pers = pers;
    }

    public Service getS() {
        return s;
    }

    public void setS(Service s) {
        this.s = s;
    }

    public ArrayList<EventCateg> getEc() {
        return list;
    }

    public void setEc(ArrayList<EventCateg> ec) {
        this.list = ec;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SousServices other = (SousServices) obj;
        if (this.id_sousServ != other.id_sousServ) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        
        String S= "SousServices{" + "id_sousServ=" + id_sousServ + ", prix_serv=" + prix_serv + ", nom_serv=" + nom_serv + ", description_serv=" + description_serv + ", note=" + note + ", pers=" + pers + ", service=" + s + ", ec=";
        for(int i=0;i<list.size();i++){
           String l=list.get(i).getType();
           S+=" "+l+" ";
        }
        return S;
        
    }
    
}