/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entities.sponsoring;
import entities.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.Transactions;
import interfaces.InterfaceService;
import java.sql.Date;
import tools.MyConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.Event;
import entities.imagepers;
//stripe 



/**
 *
 * @author Fayechi
 */
public class TransactionsService implements InterfaceService<Transactions> {

    Connection cnx;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TransactionsService() {
        cnx = MyConnection.getInstance().getCnx();
    }
//------------------------------------------------------------------------------
    @Override
    public void ajouter(Transactions t) {
        try {
            String sql = "insert into transaction(id_trans,valeur_trans,devis,userID,date_trans,mode_trans,montant_tot)"
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            String sqlDateTimeD = t.getDate_trans().format(formatter);

            ste.setInt(1, t.getId_trans());
            ste.setFloat(2, t.getValeur_trans());
            ste.setString(3, t.getDevis());
            ste.setInt(4, t.getP().getId_pers());
            ste.setObject(5, sqlDateTimeD);
            ste.setString(6, t.getMode_trans());
            ste.setFloat(7, t.getMontant_tot());

            ste.executeUpdate();
            System.out.println("transaction ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
//------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("empty-statement")
    public List<Transactions> getAll() {
        List<Transactions> trans = new ArrayList<>();
        PersonneService ps=new PersonneService();
        //PersonneService ps = new PersonneService();
        try {
            String sql = "select * from transaction";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);
            while (s.next()) {
                Personne pers = ps.findById(s.getInt(4));
                Transactions p = new Transactions(s.getInt(1), s.getFloat(2), s.getFloat(4), s.getString("devis"), s.getTimestamp("date_trans").toLocalDateTime(), s.getString("mode_trans"), pers);;
                trans.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return trans;
    }
//------------------------------------------------------------------------------
    public Transactions findById(int id) { 
        Transactions p = new Transactions();
        PersonneService ps = new PersonneService();

        try {
            String sql = "SELECT * FROM transaction WHERE id_trans = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet resultSet = ste.executeQuery();

            if (resultSet.next()) {
                Personne pers = ps.findById(resultSet.getInt(4));
                int id_trans = resultSet.getInt("id_trans");
                float valeur_trans = resultSet.getFloat("valeur_trans");
                String devis = resultSet.getString("devis");
                int userID = resultSet.getInt("userID");
                Timestamp date_trans = resultSet.getTimestamp("date_trans");
                String mode_trans = resultSet.getString("mode_trans");
                float montant_tot = resultSet.getFloat("montant_tot");
                p = new Transactions(id_trans, valeur_trans, montant_tot, devis, date_trans.toLocalDateTime(), mode_trans, pers);
            } else {
                System.out.println("Aucune transaction ne possède l'id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;

    }
//------------------------------------------------------------------------------
    public void supprimer(Transactions p) {
        String sql = "delete from transaction where id_trans=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, p.getId_trans());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
//------------------------------------------------------------------------------
    public void modifierTransaction(String devis, Transactions p) {
        String sql = "update transaction set devis=? where id_trans=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, devis);
            ste.setInt(2, p.getId_trans());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
// ------------------------------------------------------------------------------   
public String[] get_modepayment(){
    
  String []values=null;
        try {
 
String sql = "SELECT REPLACE(REPLACE(REPLACE(SUBSTRING(COLUMN_TYPE, 6, LENGTH(COLUMN_TYPE) - 6), \"'\", \"\"), \"enum(\", \"\"), \")\", \"\") AS possible_values\n" +
"FROM INFORMATION_SCHEMA.COLUMNS\n" +
"WHERE TABLE_NAME = 'transaction' AND COLUMN_NAME = 'mode_trans';";
   // Prepare the SQL statement and execute it
            PreparedStatement ste = cnx.prepareStatement(sql); 
            ResultSet resultSet = ste.executeQuery();
        // If the query returns a result, extract the possible values from the result set

            if (resultSet.next()) { 
                      // Get the possible values as a String
            String possibleValuesString = resultSet.getString("possible_values");
                // Split the String into an array of values, removing the quotes and commas
            values = possibleValuesString.split(",");
           
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return the array of possible values
            System.out.println(values);

       return values;
    }
//------------------------------------------------------------------------------   
  public String[] get_devise(){
    
  String []values=null;
        try {
 
String sql = "SELECT REPLACE(REPLACE(REPLACE(SUBSTRING(COLUMN_TYPE, 6, LENGTH(COLUMN_TYPE) - 6), \"'\", \"\"), \"enum(\", \"\"), \")\", \"\") AS possible_values\n" +
"FROM INFORMATION_SCHEMA.COLUMNS\n" +
"WHERE TABLE_NAME = 'transaction' AND COLUMN_NAME = 'devis';";
   // Prepare the SQL statement and execute it
            PreparedStatement ste = cnx.prepareStatement(sql); 
            ResultSet resultSet = ste.executeQuery();
        // If the query returns a result, extract the possible values from the result set

            if (resultSet.next()) { 
                      // Get the possible values as a String
            String possibleValuesString = resultSet.getString("possible_values");
                // Split the String into an array of values, removing the quotes and commas
            values = possibleValuesString.split(",");
           
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return the array of possible values
            System.out.println(values);

       return values;
    }
      //------------------------------------------------------------------------------
     public double calcule_montanttotale(int idev) {
        String sql = "SELECT r.id_ss, SUM(s.prix) as total_price FROM reservation r JOIN sousservice s ON r.id_ss LIKE CONCAT('%', s.id, '%') WHERE r.id_ev = ? GROUP BY r.id_ss";

        double total_price = 0;
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, idev);
            ResultSet s = ste.executeQuery();
            while (s.next()) {
                total_price += s.getDouble("total_price");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        
        return total_price;
    }
           public List<Transactions> findByName(String nom) {

        List<Transactions> st = new ArrayList<>();
                PersonneService ps = new PersonneService();

        try {
            String sql = "SELECT t.*\n" +
"FROM transaction t\n" +
"JOIN personne u ON t.userID = u.id_pers\n" +
"WHERE u.nom_pers like '%" + nom + "%'";
            Statement ste = cnx.createStatement();
            ResultSet resultSet = ste.executeQuery(sql);
            while (resultSet.next()) {
                Personne pers = ps.findById(resultSet.getInt(4));

                Transactions c = new Transactions(resultSet.getInt("id_trans"),  resultSet.getFloat("valeur_trans"), resultSet.getFloat("montant_tot"),  resultSet.getString("devis"),  resultSet.getTimestamp("date_trans").toLocalDateTime(), resultSet.getString("mode_trans"), pers);
               st.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return st;
    }
               public int ajouterget(Transactions t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   int id=-1;
        try {
            String sql = "insert into transaction(valeur_trans,devis,userID,date_trans,mode_trans,montant_tot)"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
 //           ste.setInt(1, t.getId_trans());
            ste.setFloat(1, t.getValeur_trans());
            ste.setString(2, t.getDevis());
            ste.setInt(3, t.getP().getId_pers());
            //java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // convert to java.sql.Date          
            ste.setObject(4, t.getDate_trans().format(formatter));
            ste.setString(5, t.getMode_trans());
            ste.setFloat(6, t.getMontant_tot());

            ste.executeUpdate();
            System.out.println("transaction ajoutée");
       ResultSet rs=ste.getGeneratedKeys();
    
       if(rs.next()){
       id=rs.getInt(1);
       }else 
           throw new SQLException("cannot get generated id");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;

    }
//**************************************find img               
     public String findimgId(int id) { 
        imagepers p = new imagepers();
        PersonneService ps = new PersonneService();
String last="";
        try {
            String sql = "SELECT * FROM imagepers WHERE id_pers = ?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ResultSet resultSet = ste.executeQuery();

            if (resultSet.next()) {
 
                  last = resultSet.getString("last");
                  } else {
                System.out.println("Aucune transaction ne possède l'id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return last;

    }             
 
}
