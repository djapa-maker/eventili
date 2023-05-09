/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.entities;

/**
 *
 * @author HP
 */
public class Services {
    private float idService ;
    private String nom;
    //private String icon_service;

    public Services() {
    }

    public Services(float idService, String nom) {
        this.idService = idService;
        this.nom = nom;
       // this.icon_service = icon_service;
    }

    public Services(String nom) {
        this.nom = nom;
        //this.icon_service = icon_service;
    }
    
    public void setIdService(float idService) {
        this.idService=idService;
    }

    public float getIdService() {
        return idService;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "Service{" + "idService=" + idService + ", nom=" + nom + '}';
    }
    
    
    
    
    
}
