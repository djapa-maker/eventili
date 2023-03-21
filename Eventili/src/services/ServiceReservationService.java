package services;

import tools.MyConnection;
import entities.Event;
import entities.ServiceReservation;
import entities.SousServices;
import interfaces.InterfaceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceReservationService implements InterfaceService<ServiceReservation> {

    Connection cnx;
    PreparedStatement ste;

    public ServiceReservationService() {
       this.cnx = MyConnection.getInstance().getCnx();
    }


    @Override
    public void ajouter(ServiceReservation t) {

    }
//------------------------------------------------------------------------------

    public void ajouterRes(ServiceReservation t, ArrayList<Integer> sousServicesIds) {
        String ids = sousServicesIds.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println(ids);
        try {
            String sql = "insert into reservation (id_ss,id_ev,status) values (?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1,ids);
            ste.setInt(2, t.getE().getId_ev());
            ste.setBoolean(3, t.isStatus());
            ste.executeUpdate();
            System.out.println("reservation crée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------

    public void modifierReservationService(int id, ServiceReservation t, ArrayList<Integer> sousServicesIds) {
        String ids = sousServicesIds.stream().map(Object::toString).collect(Collectors.joining(","));
        String sql = "update reservation set id_ss=?,id_ev=?,status=? where id_res=?";
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            ste.setString(1, ids);
            ste.setInt(2, t.getE().getId_ev());
            ste.setBoolean(3, t.isStatus());
            ste.setInt(4, id);
            ste.executeUpdate();
            System.out.println("reservation modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
//------------------------------------------------------------------------------
        @Override
    public List<ServiceReservation> getAll() {
        
        SousServiceService sss = new SousServiceService();
        EventService es = new EventService();
        List<ServiceReservation> list = new ArrayList<>();
        
        try {
            String sql = "select * from reservation ";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                ArrayList<SousServices> ss = new ArrayList<>();
                Event ev = es.findEventById(res.getInt(3));
                List<Integer> Ids = Arrays.stream(res.getString("id_ss").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                //System.out.println(Ids);
                for(int i=0;i<Ids.size();i++){
                    //e list de event categ contient les categ d'evenement qui figure dans Ids
                    
                    ss.add(sss.findServiceById(Ids.get(i)));
                    
                }
                ServiceReservation r = new ServiceReservation(res.getInt(1),res.getBoolean(4), ev, ss);
                list.add(r);
                //System.out.println("hhhhhhhhhh");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
//------------------------------------------------------------------------------

    @Override
    public void supprimer(ServiceReservation p) {
        String sql = "delete from reservation where id_res=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId_res());
            ste.executeUpdate();
            System.out.println("reservation supprimée");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//------------------------------------------------------------------------------
    public ServiceReservation findById(int id) {
        SousServiceService sss = new SousServiceService();
        EventService es = new EventService();
        ArrayList<SousServices> ss = new ArrayList<>();
        ServiceReservation r = new ServiceReservation();
        List<ServiceReservation> list = new ArrayList<>();
        try {
            String sql = "select * from reservation where id_res=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Event ev = es.findEventById(s.getInt(3));
                List<Integer> Ids = Arrays.stream(s.getString("id_ss").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for (int i = 0; i < Ids.size(); i++) {
                    ss.add(sss.findServiceById(Ids.get(i)));
                }
                r = new ServiceReservation(s.getInt(1),s.getBoolean(2), ev, ss);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;

    }
    
        public ServiceReservation findByIdEvent(int id) {
        SousServiceService sss = new SousServiceService();
        EventService es = new EventService();
        ArrayList<SousServices> ss = new ArrayList<>();
        ServiceReservation r = new ServiceReservation();
        List<ServiceReservation> list = new ArrayList<>();
        try {
            String sql = "select * from reservation where id_ev=" + id + "";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Event ev = es.findEventById(s.getInt(3));
                List<Integer> Ids = Arrays.stream(s.getString("id_ss").split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for (int i = 0; i < Ids.size(); i++) {
                    ss.add(sss.findServiceById(Ids.get(i)));
                }
                r = new ServiceReservation(s.getInt(1),s.getBoolean(2), ev, ss);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;

    }
}
//------------------------------------------------------------------------------
