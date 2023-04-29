/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.singleton;

 

/**
 *
 * @author sel3a
 */
 
public class transeng {
   private static final transeng instance=new transeng();
    private int type,idev;
    private String ammount;
    public static transeng getInstance(){
        return instance;
    }

    public int getIdev() {
        return idev;
    }

    public void setIdev(int idev) {
        this.idev = idev;
    }
    
 public transeng(){};
    public transeng(int type, int idev, String ammount) {
        this.type = type;
        this.idev = idev;
        this.ammount = ammount;
    }
 
 

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }
       
}
