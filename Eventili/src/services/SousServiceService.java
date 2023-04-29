package services;

import services.PersonneService;
import entities.Service;
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
import entities.EventCateg;
import entities.SousServices;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author HP
 */
public class SousServiceService implements InterfaceService<SousServices> {

    Connection cnx;
    PreparedStatement ste;
    
    public SousServiceService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }

//------------------------------------------------------------------------------    
    
    public void ajouterTry(SousServices t,ArrayList<Integer> categEventIds) {
        String ids= categEventIds.stream().map(Object::toString).collect(Collectors.joining(","));
        try {
            String sql = "insert into sousservice(prix,nom,description,image,note,id_Pers,id_eventCateg,id_service)values(?,?,?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setFloat(1, t.getPrix_serv());
            ste.setString(2, t.getNom_serv());
            ste.setString(3, t.getDescription_serv());
            ste.setString(4, t.getIcon());
            ste.setFloat(5, t.getNote());
            ste.setInt(6, t.getPers().getId_pers());
            ste.setString(7,ids);
            ste.setInt(8, t.getS().getId_service());
            ste.executeUpdate();
            System.out.println("service crée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------ 
    @Override
    public List<SousServices> getAll() {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice ";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
               SousServices s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
               list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
//------------------------------------------------------------------------------ 
    public List<SousServices> getAllByPrestataire(int id) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice where id_pers="+id;
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
               SousServices s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
               list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------
    @Override
    public void supprimer(SousServices s) {
        String sql = "delete from sousservice where id=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, s.getId_sousServ());
            ste.executeUpdate();
            System.out.println("service supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public void supprimerById(int id) {
        String sql = "delete from sousservice where id=?";
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
    public void modifierService(int id, SousServices t,ArrayList<Integer> categEventIds) {
        String ids= categEventIds.stream().map(Object::toString).collect(Collectors.joining(","));
        String sql = "update sousservice set prix=?,nom=?,description=?,image=?,note=?,id_eventCateg=? where id=?";    
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            ste.setFloat(1, t.getPrix_serv());
            ste.setString(2, t.getNom_serv());
            ste.setString(3, t.getDescription_serv());
            ste.setString(4, t.getIcon());
            ste.setFloat(5, t.getNote());
            ste.setString(6,ids);
            ste.setInt(7,id);
            ste.executeUpdate();
            System.out.println("sous service modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());  
        }       
    }   
//------------------------------------------------------------------------------
    public SousServices findServiceById(int id) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        SousServices s = new SousServices();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice where id=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
                s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }
//------------------------------------------------------------------------------     
    public SousServices findByName(String nom) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        SousServices s = new SousServices();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice where nom like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
                s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);  
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }
//------------------------------------------------------------------------------      
    public List<SousServices> findListByName(String nom) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        SousServices s = new SousServices();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice where nom like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
                s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
               list.add(s);}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------
        public List<SousServices> findSSByIdService(int id_ss) {
        SousServices ss = new SousServices();
        ServiceService s = new ServiceService();
        List<SousServices> list = new ArrayList<>();
        try {
            String sql = "select * from sousservice where id_service=" + id_ss + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = s.findById(res.getInt(1));
                ss = new SousServices(res.getInt(1),res.getString("nom"), res.getString("image"));
                list.add(ss);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }    
    //------------------------------------------------------------------------------
        public List<SousServices> findSSByIdServiceAndName(int id_ss,String name) {
        SousServices ss = new SousServices();
        ServiceService s = new ServiceService();
        List<SousServices> list = new ArrayList<>();
        try {
            String sql = "select * from sousservice where id_service="+ id_ss +" and nom like '%"+name+"%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = s.findById(res.getInt(1));
                ss = new SousServices(res.getInt(1),res.getString("nom"), res.getString("image"));
                list.add(ss);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    } 
//------------------------------------------------------------------------------     
    public List<SousServices> getAllByService(String name) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {            
            String sql = "select * from sousservice where id_service =(SELECT id_service FROM `service` WHERE nom = '"+name+"')";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            System.out.println(res);
            while (res.next()){
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                System.out.println(Ids);
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
               SousServices s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
               list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(SousServices t) {}
//------------------------------------------------------------------------------
    public ArrayList<SousServices> findServiceByPers(int id) {
        PersonneService ps = new PersonneService();
        EventCategService ec = new EventCategService();
        ServiceService ss = new ServiceService();
        SousServices s = new SousServices();
        ArrayList<SousServices> arss= new ArrayList<>();
        List<SousServices> list = new ArrayList<>();
        ArrayList<EventCateg> cat = new ArrayList<>();
        try {
            String sql = "select * from sousservice where id_Pers=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                Service serv = ss.findById(res.getInt(8));
                Personne pers = ps.findById(res.getInt(9));
                List<Integer> Ids = Arrays.stream(res.getString("id_eventCateg").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids 
                    cat.add(ec.findById(Ids.get(i)));
                }
                s = new SousServices(res.getInt(1), res.getFloat(5), res.getString("nom"), res.getString("description"), res.getString("image"), res.getFloat("note"), pers, serv, cat);
                arss.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arss;
    }
}
