/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.entities;
import java.util.Date;
/**
 *
 * @author msi
 */
public class Personne {
    
    private int id_pers;
    private String nom_pers, prenom_pers, num_tel, email,mdp, adresse, rib,role,token;
    private boolean is_verified;
    private String date;

   

    public Personne(String nom_pers, String prenom_pers, String num_tel, String email, String mdp, String adresse, String rib, String role, String token, boolean is_verified, String date) {
       this.nom_pers = nom_pers;
        this.prenom_pers = prenom_pers;
        this.num_tel = num_tel;
        this.email = email;
        this.mdp = mdp;
        this.adresse = adresse;
        this.rib = rib;
        this.role = role;
        this.token = token;
        this.is_verified = is_verified;
        this.date = date;
    }
  public Personne(int id_pers, String nom_pers, String prenom_pers, String num_tel, String email, String mdp, String adresse, String rib, String role, String token, boolean is_verified, String date) {
      this.id_pers = id_pers;
      this.nom_pers = nom_pers;
        this.prenom_pers = prenom_pers;
        this.num_tel = num_tel;
        this.email = email;
        this.mdp = mdp;
        this.adresse = adresse;
        this.rib = rib;
        this.role = role;
        this.token = token;
        this.is_verified = is_verified;
        this.date = date;
    }
    public Personne() {
    }

      public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

    public String getNom_pers() {
        return nom_pers;
    }

    public void setNom_pers(String nom_pers) {
        this.nom_pers = nom_pers;
    }

    public String getPrenom_pers() {
        return prenom_pers;
    }

    public void setPrenom_pers(String prenom_pers) {
        this.prenom_pers = prenom_pers;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Personne{" + "id_pers=" + id_pers + ", nom_pers=" + nom_pers + ", prenom_pers=" + prenom_pers + ", num_tel=" + num_tel + ", email=" + email + ", mdp=" + mdp + ", adresse=" + adresse + ", rib=" + rib + ", role=" + role + ", token=" + token + ", is_verified=" + is_verified + ", date=" + date + '}';
    }

}
