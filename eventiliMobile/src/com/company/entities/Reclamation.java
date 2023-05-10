/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;




/**
 *
 * @author bitri
 */
public class Reclamation {
    private int id;
    private int pers;
    private String description,titre,status;
    public Reclamation(){
        
    }
    public Reclamation(int id,String description,String titre,String status,int pers){
        this.id = id;
        this.description = description;
        this.titre = titre;
        this.status = status;
        this.pers = pers;
    }
    public Reclamation(String description,String titre,int pers){
        this.description = description;
        this.titre = titre;
        this.pers = pers;
    }
    public int getId(){
        return this.id;
    }
    public String getDescription(){
        return this.description;
    }
    public String getTitre(){
        return this.titre;
    }
    public String getStatus(){
        return this.status;
    }
    public int getPers(){
        return this.pers;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setPers(int pers){
        this.pers = pers;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
