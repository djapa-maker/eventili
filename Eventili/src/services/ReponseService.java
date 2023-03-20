package services;

import entities.Personne;
import entities.reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import entities.reponse;
import interfaces.InterfaceService;
import tools.MyConnection;

public class ReponseService implements InterfaceService<reponse>{
    Connection cnx;
    public ReponseService(){
        cnx = MyConnection.getInstance().getCnx();
    }
    @Override
    public void ajouter(reponse t) {
        try{
            String sql = "insert into reponse(message,senderId,rec_id) values(?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,t.getMessage());
            ste.setInt(2, t.getSender().getId_pers());
            ste.setInt(3, t.getRec().getId());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(reponse t) {
        try {    
            String sql = "DELETE FROM reponse WHERE id_rep = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,t.getIdRep());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }

 
    public reponse findById(int id) {
        reponse reponses = new reponse();
        PersonneService ps=new PersonneService();
        ReclamationService rec=new ReclamationService();
        try{
            String sql = "SELECT * FROM reponse WHERE id_rep = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Personne p=ps.findById(res.getInt(1));
                reclamation rs= rec.findById(res.getInt(2));
                reponses = new reponse(id, p, rs, res.getString("message"));
                
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return reponses;
    }

 
    public void modifier(int id, reponse t) {
        try{
            String sql = "update reponse set message = ?, rec_id = ?, timestamp = ? where id_rep = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,t.getMessage());
            ste.setInt(2, t.getRec().getId());
            ste.setTimestamp(3, t.getTimeStamp());
            ste.setInt(4,t.getIdRep());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public List<reponse> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
