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

public class ReponseService implements InterfaceService<reponse> {

    Connection cnx;

    public ReponseService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(reponse t) {
        try {
            String sql = "insert into reponse(senderId,message,rec_id) values(?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getSender().getId_pers());
            ste.setString(2, t.getMessage());
            ste.setInt(3, t.getRec().getId());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(reponse t) {
        try {
            String sql = "DELETE FROM reponse WHERE id_rep = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getIdRep());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public reponse findById(int id) {
        reponse reponses = new reponse();
        PersonneService ps = new PersonneService();
        ReclamationService rec = new ReclamationService();
        try {
            String sql = "SELECT * FROM reponse WHERE id_rep = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Personne p = ps.findById(res.getInt("senderId"));
                reclamation rs = rec.findById(res.getInt("rec_id"));
                reponses = new reponse(res.getInt("id_rep"),p,rs, res.getTimestamp("dateHeure"),res.getString("message"),res.getBoolean("isEdited"));                  
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reponses;
    }

    public void modifier(reponse t) {
        try {
            String sql = "update reponse set message = ?, isEdited = ? where id_rep = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getMessage());
            ste.setBoolean(2, true);
            ste.setInt(3, t.getIdRep());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public List<reponse> getAllAnswers(reclamation rec) {
        List<reponse> reponses = new ArrayList<>();
        PersonneService ps = new PersonneService();
        ReclamationService rs = new ReclamationService();
        try {
            String sql = "SELECT * FROM reponse WHERE rec_id = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, rec.getId());
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Personne pers = ps.findById(res.getInt("senderId"));
                reponse r;
                r = new reponse(res.getInt("id_rep"), pers, rec, res.getTimestamp("timestamp"), res.getString("message"), res.getBoolean("isEdited"));
                reponses.add(r);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reponses;
    }

    @Override
    public List<reponse> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
