/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author msi
 */
public class imagepers {
       private int id_imp,id_pers;
    private String imageP,last;

    public imagepers(){
        
    }
    public imagepers(String imageP,String last,int id_pers) {
        this.id_pers = id_pers;
        this.imageP = imageP;
        this.last=last;
    }
    public imagepers(int id_imp, String imageP,String last, int id_pers) {
        this.id_imp = id_imp;
        this.id_pers = id_pers;
        this.imageP = imageP;
    }

    @Override
    public String toString() {
        return "imagepers{" + "id_imp=" + id_imp + ", id_pers=" + id_pers + ", imageP=" + imageP + ", last=" + last +'}';
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getId_imp() {
        return id_imp;
    }

    public void setId_imp(int id_imp) {
        this.id_imp = id_imp;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }

    public String getImageP() {
        return imageP;
    }

    public void setImageP(String imageP) {
        this.imageP = imageP;
    }
    
}
