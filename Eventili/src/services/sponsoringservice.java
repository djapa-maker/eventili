/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Event;
import entities.Transactions;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.sponsoring;
import interfaces.InterfaceService;
import tools.MyConnection;
import entities.Personne;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import services.sponsoringservice;

/**
 *
 * @author alolo
 */
public class sponsoringservice implements InterfaceService<sponsoring> {

    Connection cnx;

    public sponsoringservice() {
        cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(sponsoring t) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String sql = "insert into sponsoring(id_sponso,date_debut,date_fin,nombre_impression,id_trans,id_event)"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
               String sqlDateTimeD = t.getDate_debut().format(formatter);
            String sqlDateTimeF = t.getDate_fin().format(formatter);
            ste.setInt(1, t.getId_sponso());
            ste.setObject(2, sqlDateTimeD);
            ste.setObject(3, sqlDateTimeF);
            ste.setInt(4, t.getNombre_impression());
            ste.setInt(5, t.getT().getId_trans());
            ste.setInt(6, t.getE().getId_ev());

            ste.executeUpdate();
            System.out.println("sponsoring ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
//------------------------------------------------------------------------------
    @Override
    public List<sponsoring> getAll() {
        List<sponsoring> spon = new ArrayList<>();
        EventService ev = new EventService();
        TransactionsService ts = new TransactionsService();
        try {
            String sql = "select * from sponsoring";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
            Event e = ev.findEventById(s.getInt(6));
            Transactions t = ts.findById(s.getInt(5));
            sponsoring sp = new sponsoring(s.getInt(1), s.getTimestamp("date_debut").toLocalDateTime(), s.getTimestamp("date_fin").toLocalDateTime(), s.getInt(4), e, t);
            spon.add(sp);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return spon;
    }
//------------------------------------------------------------------------------
    public sponsoring findById(int id) {

        EventService ev = new EventService();
        sponsoring sp = new sponsoring();
        TransactionsService ts = new TransactionsService();
        try {
            String sql = "SELECT * FROM sponsoring WHERE id_sponso = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet resultSet = ste.executeQuery();

            if (resultSet.next()) {
                Event e = ev.findEventById(resultSet.getInt(5));
                Transactions t = ts.findById(resultSet.getInt(6));
                int id_sponso = resultSet.getInt("id_sponso");
                Timestamp date_debut = resultSet.getTimestamp("date_debut");
                Timestamp date_fin = resultSet.getTimestamp("date_fin");
                int nombre_impression = resultSet.getInt("nombre_impression");
                int id_trans = resultSet.getInt("id_trans");
                int id_event = resultSet.getInt("id_event");
                sp = new sponsoring(id_sponso, date_debut.toLocalDateTime(), date_fin.toLocalDateTime(), nombre_impression, e, t);
            } else {
                System.out.println("No sponsor found with id_sponso " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sp;

    }
//------------------------------------------------------------------------------
    @Override
    public void supprimer(sponsoring p) {
        String sql = "delete from sponsoring where id_sponso=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId_sponso());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
//------------------------------------------------------------------------------
    public void modifierSponsoring(int nb_impre, sponsoring sp) {
        String sql = "update sponsoring set nombre_impression=? where id_sponso=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, nb_impre);
            ste.setInt(2, sp.getId_sponso());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
//------------------------------------------------------------------------------
            public List<sponsoring> findByName(String nom) {

        List<sponsoring> st = new ArrayList<>();
                PersonneService ps = new PersonneService();
sponsoringservice ss=new sponsoringservice();
TransactionsService ts=new TransactionsService();
EventService es=new EventService();
        try {
            String sql = "SELECT t.*\n" +
"FROM sponsoring t\n" +
"JOIN evenement u ON t.id_event = u.id_ev \n" +
"WHERE u.titre like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet resultSet = ste.executeQuery(sql);
            while (resultSet.next()) {
 Transactions c =ts.findById(resultSet.getInt("id_trans"));
Event eve=es.findEventById(resultSet.getInt("id_event"));
sponsoring sp=new sponsoring(resultSet.getInt("id_sponso"), resultSet.getTimestamp("date_debut").toLocalDateTime(),  resultSet.getTimestamp("date_fin").toLocalDateTime(), resultSet.getInt("nombre_impression"), eve, c);
st.add(sp);
                System.out.println("sp :"+sp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return st;
    }   
//------------------------------------------------------------------------------
         public void modifierspeciale(sponsoring sp){
String sql = "update sponsoring set nombre_impression=?,date_debut=?,date_fin=? where id_sponso=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, sp.getNombre_impression());
            ste.setObject(2, sp.getDate_debut());
                    ste.setObject(3, sp.getDate_fin());
                            ste.setInt(4, sp.getId_sponso());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
             
             
         }
}
