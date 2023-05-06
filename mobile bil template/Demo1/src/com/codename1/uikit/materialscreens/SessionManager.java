/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import com.codename1.io.Preferences;

/**
 *
 * @author msi
 */
public class SessionManager {
  public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int idPers ; 
    private static String email; 
    private static String mdp ;
 private static String photo;
    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getIdPers() {
        return pref.get("idPers",idPers);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setIdPers(int idPers) {
        pref.set("idPers",idPers);//nsajl id user connecté  w na3tiha identifiant "id";
    }


    public static String getEmail() {
        return pref.get("email",email);
    }

    public static String getPhoto() {
        return photo;
    }

    public static void setPhoto(String photo) {
        SessionManager.photo = photo;
    }

    public static void setEmail(String email) {
         pref.set("email",email);
    }

    public static String getMdp() {
        return pref.get("mdp",mdp);
    }

    public static void setMdp(String mdp) {
         pref.set("mdp",mdp);
    }

   
    
    
    
    
    
    
}