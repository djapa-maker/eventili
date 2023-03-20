package services;

import entities.Event;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import entities.chat;
import interfaces.InterfaceService;
import tools.MyConnection;

public class ChatService implements InterfaceService<chat> {
    Connection cnx;
    public ChatService(){
        cnx = MyConnection.getInstance().getCnx();
    }
    @Override
    public void ajouter(chat t) {
        try{
            String sql = "insert into chat(message_chat,userId,event_id) values(?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,t.getMessage());
            ste.setInt(2, t.getP().getId_pers());
            ste.setInt(3, t.getE().getId_ev());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public void supprimer(chat t) {
        try {    
            String sql = "DELETE FROM chat WHERE id_chat = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1,t.getId());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }

    

    public chat findById(int id) {
        PersonneService ps = new PersonneService();
        chat chats = new chat();
        EventService ev=new EventService();
        try{
            String sql = "SELECT * FROM chat WHERE id_chat = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Personne pers = ps.findById(res.getInt(3));
                Event e = ev.findEventById(res.getInt(4));
                chats = new chat(res.getInt("id_chat"), res.getTimestamp("dataheure_chat"),res.getString("message_chat"), pers, e);
                
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return chats;
    }
    public chat findbyEvent(int eventId){
       chat chats = new chat();
       PersonneService ps=new PersonneService();
       EventService ev=new EventService();
        try{
            String sql = "SELECT * FROM chat WHERE event_id = ?";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1,eventId);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                 Personne pers = ps.findById(res.getInt(4));
                Event e = ev.findEventById(res.getInt(5));
                 chats = new chat(res.getInt("id_chat"), res.getTimestamp("dataheure_chat"),res.getString("message_chat"), pers, e);
                
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return chats;
    }
   
    public void modifier(int id, chat t) {
        try{
            String sql = "update chat set message = ?,dataheure_chat = ? where id_chat = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1,t.getMessage());
            ste.setTimestamp(2, t.getTimestamp());
            ste.setInt(3, t.getId());
            ste.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
    }

    @Override
    public List<chat> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
