/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;


/**
 *
 * @author bitri
 */
public class Reponse {
    private int id;
    private String message;
    private Personne pers;
    private Reclamation rec;
    public Reponse(){
        
    }
    public Reponse(int id,String message,Personne pers,Reclamation rec){
        this.id = id;
        this.message = message;
        this.pers = pers;
        this.rec = rec;
    }
    public Reponse(String message,Personne pers, Reclamation rec){
        this.message = message;
        this.pers = pers;
        this.rec = rec;
    }
    public int getId(){
        return this.id;
    }
    public String getMessage(){
        return this.message;
    }
    public Personne getPers(){
        return this.pers;
    }
    public Reclamation getRec(){
        return this.rec;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setPers(Personne pers){
        this.pers = pers;
    }
    public void setRec(Reclamation rec){
        this.rec = rec;
    }
}