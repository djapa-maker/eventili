package services;

import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import entities.reclamation;
import interfaces.InterfaceService;
import java.sql.Date;
import tools.MyConnection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReclamationService implements InterfaceService<reclamation> {

    Connection cnx;

    public ReclamationService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(reclamation t) {
        try {
            String sql = "insert into reclamation(description,titre,userId) values(?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getDescription());
            ste.setString(2, t.getTitre());
            ste.setInt(3, t.getP().getId_pers());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(reclamation t) {
        try {
            String sql = "DELETE FROM reclamation WHERE id_rec = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public reclamation findById(int id) {
        reclamation reclamations = new reclamation();
        PersonneService p = new PersonneService();
        try {
            String sql = "SELECT * FROM reclamation WHERE id_rec = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Personne pers = p.findById(res.getInt(4));
                reclamations = new reclamation(id, res.getString("description"), res.getString("titre"), pers, res.getString("status"));

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reclamations;
    }

    public void modifier(reclamation t) {
        try {
            String sql = "update reclamation set description = ?, titre = ?, status = ? where id_rec = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getDescription());
            ste.setString(2, t.getTitre());
            ste.setString(3, t.getStatus());
            ste.setInt(4, t.getId());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public List<reclamation> getAll() {
        List<reclamation> reclamations = new ArrayList<>();
        PersonneService ps = new PersonneService();
        ReclamationService rec = new ReclamationService();
        try {
            String sql = "SELECT * FROM reclamation";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = ps.findById(res.getInt("userId"));
                reclamation r = new reclamation(res.getInt("id_rec"), res.getTimestamp("dateheure"), res.getString("description"), res.getString("titre"), pers, res.getString("status"));
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reclamations;
    }

}
