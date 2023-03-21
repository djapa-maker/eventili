package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Event;
import entities.EventCateg;
import interfaces.InterfaceService;
import tools.MyConnection;
import entities.Personne;
import entities.imageEv;
import java.time.format.DateTimeFormatter;

public class EventService implements InterfaceService<Event> {

    Connection cnx;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventService() {
        cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------

    @Override
    public void ajouter(Event t) {
        try {
            String sql = "insert into evenement(titre,date_debut,date_fin,description_ev,type,visibilite,limiteParticipant,prix,id_categ,id_pers) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            String sqlDateTimeD = t.getDate_debut().format(formatter);
            String sqlDateTimeF = t.getDate_fin().format(formatter);
            ste.setString(1, t.getTitle());
            ste.setObject(2, sqlDateTimeD);
            ste.setObject(3, sqlDateTimeF);
            ste.setString(4, t.getDescription());
            ste.setString(5, t.getType());
            ste.setString(6, t.getVisibilite());
            ste.setInt(7, t.getParticipantLimit());
            ste.setFloat(8, t.getPrice());
            ste.setInt(9, t.getC().getId());
            ste.setInt(10, t.getPers().getId_pers());
            ste.executeUpdate();
            System.out.println("Evenement ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void ajouterI(imageEv im) {
        String sql = "insert into imgev(imageE,id_even)"
                + "values (?,?)";

        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, im.getImg());
            ste.setInt(2, im.getEv().getId_ev());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------

    public void modifier(int id, Event t) {
        String sql = "update evenement set titre=?,date_debut=?,date_fin=?,description_ev=?,type=?,visibilite=?,limiteParticipant=?,prix=?,id_categ=?,id_pers=? where id_ev=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            String sqlDateTimeD = t.getDate_debut().format(formatter);
            String sqlDateTimeF = t.getDate_fin().format(formatter);
            ste.setString(1, t.getTitle());
            ste.setObject(2, sqlDateTimeD);
            ste.setObject(3, sqlDateTimeF);
            ste.setString(4, t.getDescription());
            ste.setString(5, t.getType());
            ste.setString(6, t.getVisibilite());
            ste.setInt(7, t.getParticipantLimit());
            ste.setFloat(8, t.getPrice());
            ste.setInt(9, t.getC().getId());
            ste.setInt(10, t.getPers().getId_pers());
            ste.setInt(11, id);
            ste.executeUpdate();
            System.out.println("Evenement modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierI(imageEv t) {
        //List<imageEv> Images = new ArrayList<>();
        EventService es = new EventService();
        String sql = "update imgev set imageE=? where id_imgev=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getImg());
            ste.setInt(2,es.findFirstImageByEvent(t.getEv()).getIdimgev());
            ste.executeUpdate();
            System.out.println("image modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//------------------------------------------------------------------------------
    @Override
    public void supprimer(Event t) {
        String sql = "delete from evenement where id_ev=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId_ev());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //casacade img
//------------------------------------------------------------------------------

    @Override
    public List<Event> getAll() {
        EventCategService c = new EventCategService();
        PersonneService p = new PersonneService();
        List<Event> events = new ArrayList<>();
        try {
            String sql = "select * from evenement";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Event e = new Event(s.getInt("id_ev"), s.getInt("limiteParticipant"), s.getFloat("prix"), s.getString("titre"), s.getString("description_ev"), s.getString("type"), s.getString("visibilite"), s.getTimestamp("date_debut").toLocalDateTime(), s.getTimestamp("date_fin").toLocalDateTime(), c.findById(s.getInt("id_categ")), p.findById(s.getInt("id_pers")));
                events.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }
//------------------------------------------------------------------------------

    public Event findEventById(int id) {
        EventCategService c = new EventCategService();
        PersonneService ps = new PersonneService();
        Event ev = new Event();
        try {
            String sql = "select * from evenement where id_ev=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {

                EventCateg ec = c.findById(s.getInt("id_categ"));
                Personne p = ps.findById(s.getInt("id_pers"));
                ev = new Event(s.getInt("id_ev"), s.getInt("limiteParticipant"), s.getFloat("prix"), s.getString("titre"), s.getString("description_ev"), s.getString("type"), s.getString("visibilite"), s.getTimestamp("date_debut").toLocalDateTime(), s.getTimestamp("date_fin").toLocalDateTime(), ec, p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ev;

    }

    public imageEv findFirstImageByEvent(Event e1) {
        EventService es = new EventService();
        List<imageEv> Images = new ArrayList<>();
        try {
            String sql = "select * from imgev where id_even=" + e1.getId_ev() + "";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                imageEv Iev = new imageEv(s.getInt(1), s.getString(2), es.findEventById(s.getInt(3)));
                Images.add(Iev);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        imageEv i = Images.get(0);
        return i;
    }
//------------------------------------------------------------------------------

    public List<Event> findEventByName(String name) {
        EventCategService c = new EventCategService();
        PersonneService ps = new PersonneService();
        List<Event> events = new ArrayList<>();
        try {
            String sql = "select * from evenement where titre like '%" + name + "%'";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {

                EventCateg ec = c.findById(s.getInt("id_categ"));
                Personne p = ps.findById(s.getInt("id_pers"));
                Event ev = new Event(s.getInt("id_ev"), s.getInt("limiteParticipant"), s.getFloat("prix"), s.getString("titre"), s.getString("description_ev"), s.getString("type"), s.getString("visibilite"), s.getTimestamp("date_debut").toLocalDateTime(), s.getTimestamp("date_fin").toLocalDateTime(), ec, p);
                events.add(ev);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;

    }
}
