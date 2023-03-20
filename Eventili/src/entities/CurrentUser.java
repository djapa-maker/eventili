/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author win10
 */
public class CurrentUser {
    
    private static CurrentUser single_instance=null; 
     public int id;
    private Statement st;
    private ResultSet rs;
     private String email;
     public int targetId;
     public String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

     private CurrentUser(){
         
         //id=0;
         
         email="";
         targetId=0;
         code="";
     }
     public int connex(Personne p) {
        int l = 7;
        int id=0;
        try {

            String sql = "SELECT * FROM Utilisateur WHERE username ='" + p.getEmail()+ "' AND mdp ='" + p.getMdp()+ "'";
            rs = st.executeQuery(sql);

            if (rs.next()) {
               id=rs.getInt("id");
                       
                if (rs.getInt("role")==0) {
                    l = 0;
                }
                if (rs.getInt("role")==1) {
                    l = 1;
                }
                if (rs.getInt("role")==2) {
                    l = 2;
                }
                System.out.println("succes");

            } else {
                System.out.println("not connected ");

            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }
     
     public static CurrentUser CurrentUser() 
	{ 
		// To ensure only one instance is created 
		if (single_instance == null) 
		{ 
			single_instance = new CurrentUser(); 
		} 
		return single_instance; 
	} 
     
}
