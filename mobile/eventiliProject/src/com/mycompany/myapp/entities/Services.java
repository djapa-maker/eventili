/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author HP
 */
public class Services {
    private int idcateg_service ;
    private String nom_service;
    //private String icon_service;

    public Services() {
    }

    public Services(int idcateg_service, String nom_service) {
        this.idcateg_service = idcateg_service;
        this.nom_service = nom_service;
       // this.icon_service = icon_service;
    }

    public Services(String nom_service) {
        this.nom_service = nom_service;
        //this.icon_service = icon_service;
    }
    
    

    public int getIdcateg_service() {
        return idcateg_service;
    }

    public String getNom_service() {
        return nom_service;
    }

    public void setNom_service(String nom_service) {
        this.nom_service = nom_service;
    }


    @Override
    public String toString() {
        return "CategService{" + "idcateg_service=" + idcateg_service + ", nom_service=" + nom_service + '}';
    }
    
    
    
    
    
}
