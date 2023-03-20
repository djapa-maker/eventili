/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Ticket;

import entities.Event;
import entities.Ticket;
import entities.Transactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.EventService;
import services.TicketService;
import services.TransactionsService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifierTicketController implements Initializable {
    
    @FXML
    private AnchorPane screencateg;
    
    @FXML
    private Text text1;
   
    @FXML
    private TextField qte;
    
    @FXML
    private TextField prix;
    
    @FXML
    private ChoiceBox<String> status;
    
     @FXML
    private Button annuler;
    @FXML
    private Button enregistrer;
    
    
    TransactionsService tr = new TransactionsService();
    EventService sr = new EventService();

 
  TicketService tt = new TicketService();
    Ticket ti = new Ticket();
    
    private int id_tick;
    private int n1;
    private float n2;
    private String n3;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //status.getItems().add("active");
        //status.getItems().add("Non Active");
        // TODO
    }    
    
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
    
     
     public void modifierData(Ticket ts, int id) throws SQLException {
        /*id_tick = id;
       n1 = ts.getNb_tick();
       n2 = ts.getPrice();
       n3 = ts.getStatus();
       
       
       Transactions trans =  tr.findById(1);
        Event ev =sr.findEventById(35);
        
      qte.setText( String.valueOf(n1) );
       prix.setText( String.valueOf(n2) );
       status.setValue(n3);
       ti = new Ticket(n1,n2,n3,trans ,ev );
       
       System.out.println(id_tick);*/
       
       

    }
}
