/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Personne;
import entities.imagepers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tools.MyConnection;
import interfaces.InterfaceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
/**
 *
 * @author msi
 */
public class PersonneService implements InterfaceService<Personne>{
    Connection cnx;

    public PersonneService() {
         cnx = MyConnection.getInstance().getCnx();
    }
    /*saye*/ 
   public Personne findById(int id) {
        Personne p = new Personne();
        imagepers i=new imagepers();
        try {
            //String sql = "SELECT * FROM personne WHERE id_pers = ?";
String sql = "SELECT * FROM personne WHERE id_pers ="+ id + "";
            Statement ste = cnx.prepareStatement(sql);
            ResultSet resultSet = ste.executeQuery(sql);
            
            if (resultSet.next()) {
                //int personneId = resultSet.getInt("id_pers");
                String nom = resultSet.getString("nom_pers");
                String prenom = resultSet.getString("prenom_pers");
                String num = resultSet.getString("num_tel");
                String email = resultSet.getString("email");
                String mdp = resultSet.getString("mdp");
                String adresse = resultSet.getString("adresse");
                String rib = resultSet.getString("rib"); 
                String role = resultSet.getString("role");
                String token = resultSet.getString("token");
                boolean verified = resultSet.getBoolean("verified");
                p = new Personne(id, nom, prenom, num, email,mdp, adresse, rib,role,token,verified,resultSet.getTimestamp("date").toLocalDateTime());
               
            } else {
                p=null;
                System.out.println("Aucune personne ne possède l'id : " + id);
            }
        } catch (SQLException e) {
            p=null;
            System.out.println(e.getMessage());
        }
        return p;
    }
   
   
   
   
   
   
   public imagepers findByIdI(int id) {
        imagepers i=new imagepers();
        try {
            //String sql = "SELECT * FROM personne WHERE id_pers = ?";
String sql = "SELECT * FROM imagepers WHERE id_pers ="+ id + "";
            Statement ste = cnx.prepareStatement(sql);
            ResultSet resultSet = ste.executeQuery(sql);
            
            if (resultSet.next()) {
                //int personneId = resultSet.getInt("id_pers");
                String im = resultSet.getString("imageP");
                String la = resultSet.getString("last");
                i = new imagepers(im,la,id);
               
            } else {
                i=null;
                System.out.println("Aucune image ne possède l'id : " + id);
            }
        } catch (SQLException e) {
            i=null;
            System.out.println(e.getMessage());
        }
        return i;
    }
   
   
   
   public int findByEmailMdp(String email,String mdp) {
        Personne p = new Personne();
        try {
            //String sql = "SELECT * FROM personne WHERE id_pers = ?";
String sql = "SELECT * FROM personne WHERE email = '" + email + "' AND mdp = '" + mdp + "'";
            Statement ste = cnx.prepareStatement(sql);
            ResultSet resultSet = ste.executeQuery(sql);
            if (resultSet.next()) {
                int id = resultSet.getInt("id_pers");
                return id;
            } else {
                System.out.println("email ou mot de passe incorrecte" );
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
 public List<Personne> findListByName(String nom) {
        PersonneService ps = new PersonneService();
        List<Personne> s = new ArrayList<Personne>();
        try {
            String sql = "select * from personne where nom_pers like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                 int id = res.getInt("id_pers");
                  String pnom = res.getString("nom_pers");
                String prenom = res.getString("prenom_pers");
                String num = res.getString("num_tel");
                String email = res.getString("email");
                String mdp = res.getString("mdp");
                String adresse = res.getString("adresse");
                String rib = res.getString("rib"); 
                String role = res.getString("role");
                 String token = res.getString("token");
                boolean verified = res.getBoolean("verified");
                
                
                Personne s1 = new Personne(id, pnom, prenom, num, email,mdp, adresse, rib,role,token,verified,res.getTimestamp("date").toLocalDateTime());
                
                s.add(s1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }


  public void ajouterI(imagepers im ){
      String sql = "insert into imagepers(imageP,last,id_pers)"
                    + "values (?,?,?)";
      
      try 
        {
            PreparedStatement ste = cnx.prepareStatement(sql);           
            ste.setString(1, im.getImageP());
             ste.setString(2, im.getLast());
             ste.setInt(3, im.getId_pers());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 }
    @Override
    public void ajouter(Personne t) {
 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
 String sqlDateTimeD = t.getDate().format(formatter);
         
         String sql = "insert into personne(nom_pers,prenom_pers,num_tel,email,mdp,adresse,rib,role,token,verified,date)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
        
         try 
        { 
            PreparedStatement ste = cnx.prepareStatement(sql);           
            ste.setString(1, t.getNom_pers());
             ste.setString(2, t.getPrenom_pers());
              ste.setString(3, t.getNum_tel());
               ste.setString(4, t.getEmail());
               ste.setString(5, t.getMdp());
                ste.setString(6, t.getAdresse());
                  ste.setString(7, t.getRib());
                   ste.setString(8, t.getRole());
                   ste.setString(9, t.getToken());
                   ste.setBoolean(10, t.getVerified());
                   ste.setObject(11, sqlDateTimeD);
            ste.executeUpdate();//jareb
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 }

    @Override
    public List<Personne> getAll() {
        List<Personne> personnes = new ArrayList<>();
        try {
            String sql = "select * from personne";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Personne p = new Personne(s.getInt(1), s.getString(2),s.getString(3),s.getString(4), s.getString(5), s.getString(6), s.getString(7), s.getString(8), s.getString(9),s.getString(10), s.getBoolean(11),s.getTimestamp("date").toLocalDateTime());
                personnes.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personnes;
    }
 public List<imagepers> getAllI() {
        List<imagepers> personnes = new ArrayList<>();
        try {
            String sql = "select * from imagepers";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                imagepers p = new imagepers(s.getInt(1), s.getString(2),s.getString(3),s.getInt(4));
                personnes.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personnes;
    }
    @Override
    public void supprimer(Personne p) {
       String sql = "delete from personne where id_pers=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId_pers());
            ste.executeUpdate();
            System.out.println("cbon");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void modifier(int id_perso, Personne p) {
        String sql = "update personne set nom_pers=?, prenom_pers=?, num_tel=?, email=?,mdp=?, adresse=?, rib=?, role=?,  token=?, verified=?,  date=? where id_pers=?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
 String sqlDateTimeD = p.getDate().format(formatter);
         
        try 
        {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getNom_pers());
             ste.setString(2, p.getPrenom_pers());
              ste.setString(3, p.getNum_tel());
               ste.setString(4, p.getEmail());
               ste.setString(5, p.getMdp());
                ste.setString(6, p.getAdresse());
                  ste.setString(7, p.getRib());
                   ste.setString(8, p.getRole());
                     ste.setString(9, p.getToken());
                   ste.setBoolean(10, p.getVerified());
                   ste.setObject(11, sqlDateTimeD);
            ste.setInt(12, p.getId_pers());
            ste.executeUpdate();
            System.out.println("personne modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
     public ObservableList<Personne> getAllUsers() {
        ObservableList<Personne> personnes = FXCollections.observableArrayList();
        try {
            String sql = "select * from personne";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Personne p = new Personne(s.getInt(1), s.getString(2),s.getString(3),s.getString(4), s.getString(5), s.getString(6), s.getString(7), s.getString(8), s.getString(9),s.getString(10), s.getBoolean(11),s.getTimestamp("date").toLocalDateTime());
                personnes.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personnes;
    }
       public boolean verifierEmailBd(String email) {
	PreparedStatement stmt = null;
	ResultSet rst = null;
	try {
	    String sql = "SELECT * FROM personne WHERE email=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, email);
	    rst = stmt.executeQuery();
	    if (rst.next()) {
		return true;
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return false;
    }
        public String getAlphaNumericString(int n) {

        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
          public int geIdbyemail(String username) throws SQLException {

        /* CurrentUser cu = CurrentUser.CurrentUser(); */
       int id;
        try {
            String sql = "SELECT id_pers FROM personne where email='" + username + "'";
             //System.out.println(username);
            Statement s = cnx.prepareStatement(sql);
            ResultSet result = s.executeQuery(sql);
            if (result.next()) {
               id = result.getInt("id_pers");
               
              
            }
             
            else {
                System.out.println("email ou mot de passe incorrecte" );
                id=0;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            id=0;
        }
        return id;
    }
           public void updateCode(String code, int id) {
        /*CurrentUser cu = CurrentUser.CurrentUser();*/
        String sql = "UPDATE personne SET token='" + code + "' WHERE id_pers=" + id;
        try {
           Statement st = cnx.createStatement();
            st.executeUpdate(sql);
           // System.out.println("token");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
           public Personne findbyemail(String email){
        Personne A1 = null;
        String query = "select * from personne where email=?";
        try {
           PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               A1 = new Personne(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10), rs.getBoolean(11),rs.getTimestamp("date").toLocalDateTime());
                }
        } catch (SQLException ex) {
              System.out.println(ex.getMessage());
        }
        return A1;
    }

    public void modifierI(int intValue, String image) {
        String sql = "update imagepers set last=? where id_pers=?";
        try 
        {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, image);
            ste.setInt(2, intValue);
            ste.executeUpdate();
            System.out.println("image modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
