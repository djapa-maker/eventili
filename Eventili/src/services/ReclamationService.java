package services;

import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.reclamation;
import entities.reponse;
import interfaces.InterfaceService;
import tools.MyConnection;

public class ReclamationService implements InterfaceService<reclamation>{
    Connection cnx;
    public ReclamationService(){
       cnx = MyConnection.getInstance().getCnx();
    }
    @Override
    public void ajouter(reclamation t) {
        try{
            String sql = "insert into reclamation(description,titre,userId) values(?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,t.getP().getId_pers());
            ste.setString(2, t.getTitre());
            ste.setString(3, t.getDescription());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public void supprimer(reclamation t) {
        try {    
            String sql = "DELETE FROM reclamation WHERE id_rec = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,t.getId());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
public reclamation findById(int id) {
        reclamation reclamations = new reclamation();
        PersonneService p=new PersonneService();
        try{
            String sql = "SELECT * FROM reclamation WHERE id_rec = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Personne pers=p.findById(res.getInt(4));
                 reclamations = new reclamation(id,res.getString("description"), res.getString("titre"), pers);
                
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return reclamations;
    }
    public List<reponse> getAllAnswers(int id) {
        List <reponse> reponses = new ArrayList<>();
        PersonneService ps=new PersonneService();
        ReclamationService rec=new ReclamationService();
        try{
            String sql = "SELECT * FROM reponse WHERE rec_id = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                 Personne pers = ps.findById(res.getInt(2));
                 reclamation rs = rec.findById(res.getInt(3));
                 reponse r = new reponse(id, pers, rs, res.getString("message"));
                reponses.add(r);
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return reponses;
    }


    


    public void modifier(int id, reclamation t) {
        try{
            String sql = "update reclamation set description = ?, titre = ?, dateheure = ? where id_rec = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,t.getDescription());
            ste.setString(2,t.getTitre());
            ste.setTimestamp(3, t.getTimeStamp());
            ste.setInt(4, t.getId());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public List<reclamation> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
