/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class CallingPageController implements Initializable {

    @FXML
    private Button ticketsbtn;
    @FXML
    private Button eventbtn;
    @FXML
    private BorderPane CallingPage;
    @FXML
    private Button categbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainPage();
    }

    private void MainPage() {
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("EventCateg/Categ.fxml"));
            CallingPage.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ListerTickets(ActionEvent event) {
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("Ticket/ticket.fxml"));
            CallingPage.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void ListerEvents(ActionEvent event) {
        try {

            BorderPane view = FXMLLoader.load(getClass().getResource("Event/Event.fxml"));
            CallingPage.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void ListerCateg(ActionEvent event) {
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("EventCateg/Categ.fxml"));
            CallingPage.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

}
