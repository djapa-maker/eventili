/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Ticket;

import entities.Event;
import entities.Ticket;
import entities.Transactions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.EventService;
import services.TicketService;
import services.TransactionsService;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class AddController implements Initializable {

    @FXML
    private Button btnajouter;
    @FXML
    private TextField nbrticket;
    @FXML
    private ChoiceBox<String> nomevent;
    @FXML
    private ChoiceBox<String> status;
    private TextField catego;
    @FXML
    private TextField prixticket;
    
    private ArrayList <Event> Liste;
    
    
    TransactionsService tr = new TransactionsService();
    EventService sr = new EventService();
    @FXML
    private Label m1;
    @FXML
    private Label m2;
    @FXML
    private Label m3;
    @FXML
    private Label m4;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.getItems().add("active");
        status.getItems().add("Non Active");
        
        //for (Event v : Liste){
        nomevent.getItems().add("David guetta");
    //}
    }    


    
    boolean test(){
      
    
    String statu = status.getValue();
    int x=0;
       if (nbrticket.getText().compareTo("")==0){
                  m1.setText("veuillez saisir un nombre de Tiket");
              m1.setTextFill(Color.rgb(210, 39, 30));
              x++;
              }else if (nbrticket.getText().matches("^[^0-9]*$")){
                  m1.setText("Nombre incorrecte");
              m1.setTextFill(Color.rgb(210, 39,30));
                      x++;}
              
              else if (nbrticket.getText().length()>10){
                  m1.setText("Nombre trops grand");
              m1.setTextFill(Color.rgb(210, 39,30));
                      x++;}
              
              else{
                  m1.setText("");
              }
                      
    if(x==0)
                  return true;
              else
                  return false;
    }
    
    
    
    
      private boolean validateFields() {
        if (catego.getText().isEmpty()) {
            showAlert("Error", "categorie field is required.");
            return false;
        }

        if (prixticket.getText().isEmpty()) {
            showAlert("Error", "price field is required.");
            return false;
        }

        if (nomevent.getValue().isEmpty()) {
            showAlert("Error", "name  event field is required.");
            return false;
        }

        if (status.getValue().isEmpty()) {
            showAlert("Error", "status field is required.");
            return false;
        }

        if (nbrticket.getText().isEmpty()) {
            showAlert("Error", "Number of tickets field is required.");
            return false;
        }
        return true;
    }
         private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
          @FXML
          
          
 
    
    private void creerTicket(ActionEvent event) throws SQLException, IOException {
        
      TicketService tt = new TicketService();
        if ((!nomevent.getValue().isEmpty()) || 
                (!nbrticket.getText().isEmpty()) || 
                (!status.getValue().isEmpty()) ||
                (!prixticket.getText().isEmpty())){
            
        
        String nom_event = nomevent.getValue();
        
        int nbr_ticket = Integer.parseInt(nbrticket.getText()); 
        float x;
        x =  Float.valueOf(prixticket.getText());
          
        String stat = status.getValue() ;
        
        
        Transactions trans =  tr.findById(1);
        Event ev =sr.findEventById(34);
        
        Ticket t = new Ticket(nbr_ticket, x, stat, trans ,ev);
        if (test()){
     
        tt.ajouter(t);
        
      
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Ajout");
        a.setContentText("Ticket ajoutée avec succès");
        a.setHeaderText("Ajout réussie");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
           Stage stage1 = (Stage) btnajouter.getScene().getWindow();
            stage1.close();
            
               }
        
}  
      
        }
    }
}
         



    

