/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.SingletonEvent;

import entities.Event;
import entities.Ticket;

/**
 *
 * @author msi
 */
public class SingletonE {
     private static final SingletonE instance=new SingletonE();
    private Event E;
    public Ticket T;
    private SingletonE(){
        
    }
    public static SingletonE getInstance(){
        return instance;
    }
    public Event getE(){
        return E;
    }

    public void setE(Event E) {
        this.E = E;
    }
     public Ticket getT(){
        return T;
    }

    public void setT(Ticket T) {
        this.T = T;
    }
}
