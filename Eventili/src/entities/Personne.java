/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author msi
 */
public class Personne {

    private int id_pers;
    private String nom_pers, prenom_pers, token,email, adresse, rib, num_tel,mdp;
    private boolean verified;
    private String role;
private LocalDateTime date;
    public Personne() {
        id_pers=0;
    }
//jareb
    public Personne(int id_pers, String nom_pers, String prenom_pers, String num_tel, String email,String mdp, String adresse, String rib,String role, String token,boolean verfied, LocalDateTime date) {
        this.id_pers = id_pers;
        this.nom_pers = nom_pers;
        this.prenom_pers = prenom_pers;
        this.num_tel = num_tel;
        this.email = email;
        this.mdp = mdp;
        this.adresse = adresse;
        this.rib = rib;
        this.role=role;
        this.verified=verfied;
        this.token=token;
        this.date=date;
    }

    public Personne(String nom_pers, String prenom_pers, String num_tel,String email,String mdp, String adresse, String rib,String role, String token,boolean verfied, LocalDateTime date) {
        this.nom_pers = nom_pers;
        this.prenom_pers = prenom_pers;
        this.email = email;
        this.mdp = mdp;
        this.adresse = adresse;
        this.rib = rib;
        this.num_tel = num_tel;
        this.role = role;
        this.verified=verfied;
          this.token=token;
        this.date=date;
    }

    public int getId_pers() {
        return id_pers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom_pers() {
        return nom_pers;
    }

    public String getPrenom_pers() {
        return prenom_pers;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    

    public String getRib() {
        return rib;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }
    

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public void setNom_pers(String nom_pers) {
        this.nom_pers = nom_pers;
    }

    public void setPrenom_pers(String prenom_pers) {
        this.prenom_pers = prenom_pers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    
    @Override
    public String toString() {
        return "Personne{" + "id_pers=" + id_pers + ", nom_pers=" + nom_pers + ", prenom_pers=" + prenom_pers +", num_tel=" + num_tel + ", email=" + email + ", mdp=" + mdp + ", adresse=" + adresse + ", rib=" + rib +  ", role=" +this.role+'}';
    }

}
