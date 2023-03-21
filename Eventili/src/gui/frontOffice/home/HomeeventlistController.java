/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.home;

import entities.Event;
import gui.SingletonEvent.SingletonE;
import gui.frontOffice.Tickets.TicketController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class HomeeventlistController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Label day;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label EventName;
    @FXML
    private Text desc;
    @FXML
    private Label prix;
    @FXML
    private Button btparticiper;
    private Event e1;
    private int idev;
EventService es = new EventService();
    DateTimeFormatter dayF = DateTimeFormatter.ofPattern("EEE");
    DateTimeFormatter dateF = DateTimeFormatter.ofPattern("dd");
    DateTimeFormatter timeF = DateTimeFormatter.ofPattern("HH:mm");
     SingletonE data= SingletonE.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setData(Event e) throws FileNotFoundException {
        if (e.getParticipantLimit()==0)
             {
                 btparticiper.setDisable(true);
             }
        String Image=es.findFirstImageByEvent(e).getImg();
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+Image); 
        Image image = new Image(inputstream); 
        
        
        img.setImage(image);
        e1 = e;
        idev=e.getId_ev();
        Rectangle clip = new Rectangle(img.getFitWidth(), img.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        clip.setStroke(Color.BLACK);
        img.setClip(clip);

        EventName.setText(e.getTitle());
        day.setText(e.getDate_debut().format(dayF));
        date.setText(e.getDate_debut().format(dateF));
        time.setText(e.getDate_debut().format(timeF) + " - " + e.getDate_fin().format(timeF));
        desc.setText(e.getDescription());
        if (e.getPrice() == 0) {
            prix.setText("Gratuit");
        } else {
            prix.setText(Float.toString(e.getPrice()) + "DT");
        }

    }


  

    @FXML
    private void afficherEvt(ActionEvent event) throws IOException {
        
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../Tickets/ticket.fxml"));
        Parent Root = addLoader.load();
        TicketController ticketController = addLoader.getController();
        String evtName = e1.getTitle();
        String evtDate = date.getText();
        int evtId = e1.getId_ev();
        String price = Float.toString(e1.getPrice());
        ticketController.loadData(evtId,evtName,evtDate,price);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();
    }
    
}
