/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Fayechi
 */
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transactions {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int id_trans;
    private float valeur_trans, montant_tot;
    private String devis, mode_trans;
   private LocalDateTime date_trans; 
    private Personne p;

    public Transactions() {
    }

    public void setId_trans(int id_trans) {
        this.id_trans = id_trans;
    }

    public Transactions(int id_trans, float valeur_trans, float montant_tot, String devis, LocalDateTime date_trans, String mode_trans, Personne p) {
        this.id_trans = id_trans;

        this.valeur_trans = valeur_trans;
        this.montant_tot = montant_tot;
        this.devis = devis;
        this.date_trans = date_trans;
        this.mode_trans = mode_trans;
        this.p = p;
    }

    public int getId_trans() {
        return id_trans;
    }

    public float getValeur_trans() {
        return valeur_trans;
    }

    public void setValeur_trans(float valeur_trans) {
        this.valeur_trans = valeur_trans;
    }

    public float getMontant_tot() {
        return montant_tot;
    }

    public void setMontant_tot(float montant_tot) {
        this.montant_tot = montant_tot;
    }

    public String getDevis() {
        return devis;
    }

    public void setDevis(String devis) {
        this.devis = devis;
    }

    public LocalDateTime getDate_trans() {
        return date_trans;
    }

    public void setDate_trans(LocalDateTime date_trans) {
        this.date_trans = date_trans;
    }

    public String getMode_trans() {
        return mode_trans;
    }

    public void setMode_trans(String mode_trans) {
        this.mode_trans = mode_trans;
    }

    public Personne getP() {
        return p;
    }

    @Override
    public String toString() {
        return "transaction{" + "id_trans=" + id_trans + ", valeur_trans=" + valeur_trans + ", montant_tot=" + montant_tot + ", devis=" + devis + ", date_trans=" + date_trans + ", mode_trans=" + mode_trans + "userId=" + this.getP().getId_pers() + '}';
    }

}
