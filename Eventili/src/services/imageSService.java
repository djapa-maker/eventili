/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Service;
import entities.SousServices;
import entities.imageSS;
import interfaces.InterfaceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author HP
 */
public class imageSService implements InterfaceService<imageSS> {
    
    
    Connection cnx;
    PreparedStatement ste;
//------------------------------------------------------------------------------
    public imageSService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(imageSS t) {
        try {
            String sql = "insert into imagess(img,sous_service)values(?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getImg());
            ste.setInt(2, t.getS().getId_sousServ());
            ste.executeUpdate();
            System.out.println("image crée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @Override
    public List<imageSS> getAll() {
        List<imageSS> list = new ArrayList<>();
        SousServiceService is= new SousServiceService();
        SousServices ids;
        try {
            String sql = "select * from imagess ";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            ids=is.findServiceById(res.getInt(3));
            while (res.next()) {
                imageSS s = new imageSS(res.getInt(1), res.getString("img"),ids);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------
    @Override
    public void supprimer(imageSS p) {
        String sql = "delete from imagess where idimgss=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getIdimgss());
            ste.executeUpdate();
            System.out.println("image supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------      
    public void supprimerById(int id) {
        String sql = "delete from imagess where idimgss=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            System.out.println("image supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public void modifier(int id, imageSS t) {
        String sql = "update imagess set img=?  where  idimgss=?";
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getImg());
            ste.setInt(2, id);
            ste.executeUpdate();
            System.out.println("image modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public imageSS findById(int id) {
        imageSS s = new imageSS();
        SousServiceService is= new SousServiceService();
        SousServices ids;
        try {
            String sql = "select * from imagess where idimgss=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            ids=is.findServiceById(res.getInt(3));
            while (res.next()) {
                s = new imageSS(id, res.getString("img"),ids);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

//------------------------------------------------------------------------------
        public List<imageSS> findImageByIdSS(int id_ss) {
           
        imageSS ss = new imageSS();
        SousServiceService s = new SousServiceService();
        List<imageSS> list = new ArrayList<>();
        SousServices ids;
        try {
            String sql = "select * from imagess where sous_service =" + id_ss;
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
             ids=s.findServiceById(id_ss);
              System.out.println("dfffffffffffffffsjddkjdkjdskjsdkjdskjdskj"+ids);
            while (res.next()) {
                //SousServices serv = s.findServiceById(res.getInt(1));
                ss = new imageSS(res.getInt(1),res.getString("img"), ids);
                list.add(ss);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }   

  

}
