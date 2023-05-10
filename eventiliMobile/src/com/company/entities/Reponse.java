/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.entities;


/**
 *
 * @author bitri
 */
public class Reponse {
    private int id;
    private String message;
    private int pers;
    private int rec;
    public Reponse(){
        
    }
    public Reponse(int id,String message,int pers,int rec){
        this.id = id;
        this.message = message;
        this.pers = pers;
        this.rec = rec;
    }
    public Reponse(String message,int pers, int rec){
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
    public int getPers(){
        return this.pers;
    }
    public int getRec(){
        return this.rec;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setPers(int pers){
        this.pers = pers;
    }
    public void setRec(int rec){
        this.rec = rec;
    }
}