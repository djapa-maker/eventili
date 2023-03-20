/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.backOffice.sponsoring;
import services.sponsoringservice;
  import entities.sponsoring;
import gui.backOffice.users.UserController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author sel3a
 */
 public class ModifersponsorController implements Initializable {
     sponsoring p=new sponsoring();
UserController us=new UserController();
     String nom;
    LocalDateTime datedd;LocalDateTime dateff;int numimprres;
    @FXML
    private Button btnenregistrer;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField nbimpre;
    @FXML
    private DatePicker datef;
    @FXML
    private DatePicker dated;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 txtNom.setEditable(false);
    }    

    @FXML
    private void ajouter(MouseEvent event) {
        sponsoringservice ss=new sponsoringservice();
        LocalDate dateda=dated.getValue();
        LocalDate datedf=datef.getValue();
  
        p=new sponsoring(numimprres, datedd, dateff , numimprres, null, null);
        
        if(true)
        
        {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Etes-vous sur de vouloir modifier cette sponsorisation ");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
         //  ss.modifier(p);
       // us.Refresh();
         Stage stage = (Stage) btnenregistrer.getScene().getWindow();
          stage.close();
        }
        }        

    }
     public void modifierData(sponsoring s) throws SQLException, FileNotFoundException {
   
   datedd=s.getDate_debut();
   dateff=s.getDate_fin();
   numimprres=s.getNombre_impression();
   nom=s.getE().getTitle();
         
this.dated.setValue(datedd.toLocalDate());      
  this.datef.setValue(dateff.toLocalDate());       
    this.nbimpre.setText(String.valueOf(numimprres) );
    this.txtNom.setText(nom);
  p=new sponsoring(0, datedd, dateff, numimprres, s.getE(), s.getT());
     
     
     }
     public  void getController(UserController u){
    us=u;
}
    
}
