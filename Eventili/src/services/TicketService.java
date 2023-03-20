/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Event;
import entities.Ticket;
import entities.Transactions;
import java.sql.Connection;
import interfaces.InterfaceService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author islem
 */
public class TicketService implements InterfaceService<Ticket> {

    Connection cnx;
    PreparedStatement ste;

    public TicketService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    //------------------------------------------------------------------------------    
    @Override
    public void ajouter(Ticket t) {
        try {
            String sql = "insert into ticket (nb_tick,prix_tick,status,id_tran,idEvent) values (?,?,?,?,?)";

            PreparedStatement ste = cnx.prepareStatement(sql);

            // Vérifie si l'événement existe avant d'ajouter le ticket
            if (evenementExiste(t.getTev().getId_ev())) {
                ste.setInt(1, t.getNb_tick());
                ste.setFloat(2, t.getPrice());
                ste.setString(3, t.isStatus());
                ste.setInt(4, t.getTrans().getId_trans());
                ste.setInt(5, t.getTev().getId_ev());

                ste.executeUpdate();

                System.out.println("Ticket ajouté");
            } else {
                System.out.println("L'événement correspondant n'existe pas");
            }
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

        }

    }

    // Vérifie si l'événement correspondant existe
    private boolean evenementExiste(int id_ev) {
        String sql = "SELECT COUNT(*) FROM evenement WHERE id_ev = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id_ev);
            ResultSet result = ste.executeQuery();
            if (result.next() && result.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    //------------------------------------------------------------------------------
    public void supprimer(Ticket t) {
        String sql = "delete from ticket where id_tick=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId_tick());
            ste.executeUpdate();
            System.out.println("Ticket supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void supprimerById(int id) {
        String sql = "delete from ticket where id_tick=?";
        try {
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
            System.out.println("ticket supprimé");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//-----------------------------------------------------------------------------------   

    public void modifierTicket(int id_tick, Ticket t) {
        String sql = "update ticket set  nb_tick=? , status=?   where id_tick=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getNb_tick());
            ste.setString(2, t.isStatus());
            ste.setInt(3, t.getId_tick());
            ste.executeUpdate();
            System.out.println("Ticket modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //------------------------------------------------------------------------------   
    @Override
    public List<Ticket> getAll() {
        TransactionsService tran = new TransactionsService();
        EventService ev = new EventService();
        List<Ticket> ticket = new ArrayList<>();
        try {
            String sql = "select * from ticket";

            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                System.out.println(s.getInt(1));
                System.out.println(s.getInt(2));
                System.out.println(s.getFloat(3));
                System.out.println(s.getString(4));
                System.out.println(s.getInt(5));
                System.out.println(s.getInt(6));

                Transactions trans = tran.findById(s.getInt(5));

                Event event = ev.findEventById(s.getInt(6));

                Ticket t = new Ticket(s.getInt(1), s.getInt(2), s.getFloat(3), s.getString(4), trans, event);

                ticket.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ticket;
    }

//------------------------------------------------------------------------------
    public Ticket findById(int id) {
        Ticket t = new Ticket();
        TransactionsService tran = new TransactionsService();
        EventService event = new EventService();

        try {
            String sql = "SELECT * FROM Ticket WHERE id_tick = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet resultSet = ste.executeQuery();
            if (resultSet.next()) {

                Transactions tr = tran.findById(resultSet.getInt(4));
                Event ev = event.findEventById(resultSet.getInt(5));

                int TicketId = resultSet.getInt("id_tick");
                float price = resultSet.getFloat("prix_tick");
                int TicketNb = resultSet.getInt("Nb_tick");
                String Status = resultSet.getString("status");

                t = new Ticket(TicketId, TicketNb, price, Status, tr, ev);
            } else {
                System.out.println("Aucune personne ne possède l'id : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

}
