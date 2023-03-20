/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author alolo
 */
public class sponsoring {

    private int id_sponso, nombre_impression;
    private LocalDateTime date_debut, date_fin;
    Event e;
    Transactions t;

    public sponsoring() {
    }

    public sponsoring(int id_sponso, LocalDateTime date_debut, LocalDateTime date_fin, int nombre_impression, Event e, Transactions t) {
        this.id_sponso = id_sponso;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.nombre_impression = nombre_impression;
        this.e = e;
        this.t = t;
    }

    public int getId_sponso() {
        return id_sponso;
    }

    public int getNombre_impression() {
        return nombre_impression;
    }

    public void setNombre_impression(int nombre_impression) {
        this.nombre_impression = nombre_impression;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public Event getE() {
        return e;
    }

    public Transactions getT() {
        return t;
    }

    @Override
    public String toString() {
        return "sponsoring{" + "id_sponso=" + id_sponso + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", nombre_impression=" + nombre_impression + ", id_trans=" + this.getE().getId_ev() + ", id_event=" + this.getT().getId_trans() + '}';
    }

}
