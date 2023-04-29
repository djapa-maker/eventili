package services;

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

/**
 *
 * @author HP
 */
public class ServiceService implements InterfaceService<Service> {

    Connection cnx;
    PreparedStatement ste;
//------------------------------------------------------------------------------
    public ServiceService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(Service t) {
        try {
            String sql = "insert into service(nom)values(?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getNom_service());
            ste.executeUpdate();
            System.out.println("service crée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @Override
    public List<Service> getAll() {
        List<Service> list = new ArrayList<>();
        try {
            String sql = "select * from service ";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service s = new Service(res.getInt(1), res.getString("nom"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------
    @Override
    public void supprimer(Service p) {
        String sql = "delete from service where id_service=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId_service());
            ste.executeUpdate();
            System.out.println("service supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------      
    public void supprimerById(int id) {
        String sql = "delete from service where id_service=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            System.out.println("service supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public void modifier(int id, Service t) {
        String sql = "update service set nom=?  where  id_service=?";
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getNom_service());
            ste.setInt(2, id);
            ste.executeUpdate();
            System.out.println("service modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public Service findById(int id) {
        Service s = new Service();
        try {
            String sql = "select * from service where id_service=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                s = new Service(id, res.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }
//------------------------------------------------------------------------------
    public List<Service> findByName(String nom) {
        List<Service> s = new ArrayList<>();
        try {
            String sql = "select * from service where nom like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service c = new Service(res.getInt(1), res.getString(2));
                s.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }
//------------------------------------------------------------------------------
    public Service findByNames(String nom) {
        Service s = new Service();
        try {
            String sql = "select * from service where nom like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                s = new Service(res.getInt(1), nom);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }
    
      public Service findName(String nom) {
        Service s = new Service();
        try {
            String sql = "select * from service where nom ='%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                s = new Service(res.getInt(1), nom);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

}
