/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Avis;
import entities.Personne;
import entities.Service;
import interfaces.InterfaceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;
import entities.SousServices;

/**
 *
 * @author HP
 */
public class AvisService implements InterfaceService<Avis> {

    Connection cnx;
    PreparedStatement ste;

    public AvisService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }

//------------------------------------------------------------------------------
    @Override
    public void ajouter(Avis t) {
        try {
            String sql = "insert into avis(rating,comment,id_service,pers)values(?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setFloat(1, t.getRating());
            ste.setString(2, t.getComment());
            ste.setInt(3, t.getS().getId_sousServ());
            ste.setInt(4, t.getP().getId_pers());
            ste.executeUpdate();
            System.out.println("avis crée");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------

    @Override
    public List<Avis> getAll() {
        PersonneService p = new PersonneService();
        SousServiceService ss = new SousServiceService();
        List<Avis> list = new ArrayList<>();
        try {
            String sql = "select * from avis";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = p.findById(res.getInt(5));
                SousServices serv = ss.findServiceById(res.getInt(4));
                Avis s = new Avis(res.getInt(1), res.getString("comment"), res.getFloat("rating"), serv, pers, res.getTimestamp("Date"));
                list.add(s);
            }
            System.out.println("listtttttt" + list);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------

    @Override
    public void supprimer(Avis a) {
        String sql = "delete from avis where id_av=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, a.getId_av());
            ste.executeUpdate();
            System.out.println("avis supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerById(int id) {
        String sql = "delete from avis where id_av=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            System.out.println("avis supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------

    public void modifierAvis(int id, Avis t) {
        String sql = "update avis set rating=?,comment=? where id_av=?";
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            ste.setFloat(1, t.getRating());
            ste.setString(2, t.getComment());
            ste.setInt(3, id);
            ste.executeUpdate();
            System.out.println("avis modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------

    public Avis findById(int id) {
        Avis s = new Avis();
        PersonneService p = new PersonneService();
        SousServiceService ss = new SousServiceService();
        try {
            String sql = "select * from avis where id_av=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = p.findById(res.getInt(5));
                SousServices serv = ss.findServiceById(res.getInt(4));
                s = new Avis(id, res.getString("comment"), res.getFloat("rating"), serv, pers, res.getTimestamp("Date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public ArrayList<Avis> findByName(String name) {
        ArrayList<Avis> avv = new ArrayList<>();
        Avis s = new Avis();
        PersonneService p = new PersonneService();
        SousServiceService ss = new SousServiceService();
        try {
            String sql = "select * from avis where pers = (select id_pers from personne where prenom_pers like'%" + name + "%')";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = p.findById(res.getInt(5));
                SousServices serv = ss.findServiceById(res.getInt(4));
                s = new Avis(res.getInt(1), res.getString("comment"), res.getFloat("rating"), serv, pers, res.getTimestamp("Date"));
                avv.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avv;
    }

    public ArrayList<Avis> findByIdSousService(int id) {
        ArrayList<Avis> aaa = new ArrayList<>();
        Avis s = new Avis();
        PersonneService p = new PersonneService();
        SousServiceService ss = new SousServiceService();
        try {
            String sql = "select * from avis where id_service=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = p.findById(res.getInt(5));
                SousServices serv = ss.findServiceById(res.getInt(4));
                s = new Avis(id, res.getString("comment"), res.getFloat("rating"), serv, pers, res.getTimestamp("Date"));
                aaa.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return aaa;
    }

    public ArrayList<Avis> findByIdP(int id) {
        ArrayList<Avis> aaa = new ArrayList<>();
        Avis s = new Avis();
        PersonneService p = new PersonneService();
        SousServiceService ss = new SousServiceService();
        try {
            String sql = "select * from avis where pers=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Personne pers = p.findById(res.getInt(5));
                SousServices serv = ss.findServiceById(res.getInt(4));
                s = new Avis(id, res.getString("comment"), res.getFloat("rating"), serv, pers, res.getTimestamp("Date"));
                aaa.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return aaa;
    }

}
