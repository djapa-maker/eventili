/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class Avis {

    private int id_av;
    private String comment;
    private float rating;
    private SousServices s;
    private Personne p;
    private Timestamp d;
   

    public Avis() {
    }

    public Avis(String comment, float rating, SousServices s, Personne p) {
        this.comment = comment;
        this.rating = rating;
        this.s = s;
        this.p = p;
    }

    public Avis(int id_av, String comment, float rating, SousServices s, Personne p) {
        this.id_av = id_av;
        this.comment = comment;
        this.rating = rating;
        this.s = s;
        this.p = p;
    }
    
    
  public Avis(String comment, float rating, SousServices s, Personne p,Timestamp d) {
        this.comment = comment;
        this.rating = rating;
        this.s = s;
        this.p = p;
        this.d=d;
    }
    public Avis(int id_av,String comment, float rating, SousServices s, Personne p,Timestamp d) {
        this.id_av=id_av;
        this.comment = comment;
        this.rating = rating;
        this.s = s;
        this.p = p;
        this.d=d;
    }
    public int getId_av() {
        return id_av;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public SousServices getS() {
        return s;
    }

    public void setS(SousServices s) {
        this.s = s;
    }

    public Personne getP() {
        return p;
    }

    public void setP(Personne p) {
        this.p = p;
    }

    public Timestamp getD() {
        return d;
    }

    public void setD(Timestamp d) {
        this.d = d;
    }

    
    @Override
    public String toString() {
        return "Avis{" + "id_av=" + id_av + ", comment=" + comment + ", rating=" + rating + ", s=" + s + ", p=" + p + '}';
    }
   
}