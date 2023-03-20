/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.sigleton;

import entities.Personne;

/**
 *
 * @author msi
 */
public class singleton {
    private static final singleton instance=new singleton();
    private Personne user;
    private singleton(){
        
    }
    public static singleton getInstance(){
        return instance;
    }
    public Personne getUser(){
        return user;
    }

    public void setUser(Personne user) {
        this.user = user;
    }
    
}
