/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author islem
 */

public class Ticket {
    private int id_tick ;
    private int nb_tick ;
    private String status;
   private float price;
    
    private Transactions trans;
    private Event  tev;
    
    
     public Ticket() {
    }
     
      public Ticket(int nb_tick, float price, String status,Transactions trans, Event tev) {
      
        this.nb_tick = nb_tick;
        this.status = status;
        this.price=price;
        this.trans = trans;
        this.tev = tev;
    }
      
      public Ticket(int nb_tick, float price, String status, Event tev) {
      
        this.nb_tick = nb_tick;
        this.status = status;
        this.price=price;
       
        this.tev = tev;
    }
     
     

    public Ticket(int id_tick,int nb_tick, float price, String status, Transactions trans, Event tev) {
        this.id_tick = id_tick;
        this.nb_tick = nb_tick;
        this.status = status;
        this.price=price;
        this.trans = trans;
        this.tev = tev;
    }
    
    

    public Ticket(int nb_tick, String status) {
        this.nb_tick = nb_tick;
        this.status = status;
    }

    public Ticket(Ticket t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Ticket(String nbr_ticket, float x, String stat, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

   
   
    
     /***************************************************************************/

    public String getStatus() {
        return status;
    }

    public int getId_tick() {
        return id_tick;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNb_tick() {
        return nb_tick;
    }

    public String isStatus() {
        return status;
    }

    public Transactions getTrans() {
        return trans;
    }

    public Event getTev() {
        return tev;
    }

    
    /***************************************************************************/
    
    
    public void setId_tick(int id_tick) {
        this.id_tick = id_tick;
    }

    public void setNb_tick(int nb_tick) {
        this.nb_tick = nb_tick;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrans(Transactions trans) {
        this.trans = trans;
    }

    public void setTev(Event tev) {
        this.tev = tev;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id_tick=" + id_tick + ", nb_tick=" + nb_tick + ", status=" + status + ", price=" + price + ", trans=" + trans + ", tev=" + tev + '}';
    }

 
    




    
    

   
    
    
}
