/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.singleton;
import entities.reclamation;
/**
 *
 * @author bitri
 */
public class SingletonReclam {
    private static final SingletonReclam instance=new SingletonReclam();
    private reclamation R;
    private SingletonReclam(){
        
    }

    public static SingletonReclam getInstance(){
        return instance;
    }
    public reclamation getReclam(){
        return this.R;
    }

    public void setReclam(reclamation R) {
        this.R = R;
    }
}
