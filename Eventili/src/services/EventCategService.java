package services;

import interfaces.InterfaceService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.EventCateg;
import tools.MyConnection;

public class EventCategService implements InterfaceService<EventCateg> {

    Connection cnx;

    public EventCategService() {
        cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(EventCateg t) {
        try {
            String sql = "insert into categ_event(type) values (?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getType());
            ste.executeUpdate();
            System.out.println("Categorie ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public void modifier(String type, EventCateg t) {
        String sql = "update categ_event set type=? where id_categ=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, type);
            ste.setInt(2, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @Override
    public void supprimer(EventCateg t) {
        String sql = "delete from categ_event where id_categ=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @Override
    public List<EventCateg> getAll() {
        List<EventCateg> categories = new ArrayList<>();
        try {
            String sql = "select * from categ_event";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                EventCateg c = new EventCateg(s.getInt("id_categ"), s.getString("type"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
//------------------------------------------------------------------------------
    public EventCateg findById(int id) {

        EventCateg c = new EventCateg();
        try {
            String sql = "select * from categ_event where id_categ=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                c = new EventCateg(id, res.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
    //------------------------------------------------------------------------------
    public EventCateg findByName(String name) {

        EventCateg c = new EventCateg();
        try {
            String sql = "select * from categ_event where type='" + name + "'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                c = new EventCateg(res.getInt("id_categ"), name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
    //------------------------------------------------------------------------------
    public List<EventCateg> findCategByName(String name) {
        EventCateg c = new EventCateg();
        List<EventCateg> categs = new ArrayList<>();
        try {
            String sql = "select * from categ_event where type like '%" + name + "%'";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                c = new EventCateg(s.getInt("id_categ"), s.getString("type"));
                categs.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categs;

    }

}
