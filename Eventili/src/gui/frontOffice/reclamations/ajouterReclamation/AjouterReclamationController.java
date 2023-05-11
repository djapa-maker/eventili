/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.reclamations.ajouterReclamation;

import entities.Personne;
import entities.reclamation;
import gui.sigleton.singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ReclamationService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author othma
 */
public class AjouterReclamationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Envoyer;
    @FXML
    private Button Retour;
    @FXML
    private TextArea DescriptionReclam;
    @FXML
    private TextField Titre;
    private boolean badWordDetected = false;
    private boolean EmptyStringDetected = false;
    ReclamationService service = new ReclamationService();
    private singleton data = singleton.getInstance();
    private Personne P = data.getUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void RetourButtonFunction(ActionEvent event) {
        
            Stage thisStage = (Stage) DescriptionReclam.getScene().getWindow();
            thisStage.close();
       
    }

    @FXML
    private void EnvoyerButtonFuction(ActionEvent event) {
        if (ControlSaisie(Titre.getText().trim())) {
            if (ControlSaisie(DescriptionReclam.getText().trim())) {
                reclamation reclam = new reclamation(DescriptionReclam.getText(), Titre.getText(), P);
                service.ajouter(reclam);
                Notifications.create().title("Eventili").text("Réclamation Envoyé").showInformation();
                Stage thisStage = (Stage) DescriptionReclam.getScene().getWindow();
                thisStage.close();
            } else {
                if (EmptyStringDetected) {
                    Notifications.create().title("Eventili").text("La description du probléme encontré est requise").showError();

                } else if (badWordDetected) {
                    Notifications.create().title("Eventili").text("Un mot innaprorier a été detecter dans le champs Description").showError();

                }
                badWordDetected = false;
                EmptyStringDetected = false;
            }
        } else {
            if (EmptyStringDetected) {
                    Notifications.create().title("Eventili").text("Le titre est requis").showError();
            } else if (badWordDetected) {
                    Notifications.create().title("Eventili").text("Un mot innaprorier a été detecter dans le champs titre").showError();
            }
            badWordDetected = false;
            EmptyStringDetected = false;
        }
    }

    private boolean ControlSaisie(String Message) {
        if (Message.equals("")) {
            EmptyStringDetected = true;
            return false;
        }
        try {
            URL git = new URL("https://raw.githubusercontent.com/bitri12/pidev_3A26_badWords/main/badwords.txt");
            URLConnection connection = git.openConnection();
            connection.setRequestProperty("X-Requested-With", "Curl");
            BufferedReader lecteur = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String comparateur;
            while ((comparateur = lecteur.readLine()) != null) {
                if (Message.toLowerCase().contains(comparateur)) {
                    badWordDetected = true;
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
