/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.EventCateg.creerEventCateg;

import entities.EventCateg;
import gui.backOffice.GestionEvent.EventCateg.CategController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.EventCategService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class CreerEventCategController implements Initializable {

    @FXML
    private AnchorPane screencateg;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private TextField nom;
    @FXML
    private Button annuler;
    @FXML
    private Button enregistrer;

    private EventCateg cat;
    EventCategService ecs = new EventCategService();
    CategController cc = new CategController();
    private ArrayList<EventCateg> listCateg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }

    public void getController(CategController c) {
        cc = c;
    }

    @FXML
    private void creerEvent(ActionEvent event) throws IOException, SQLException {
        boolean nameExists = false;
        String n;
        if (!nom.getText().isEmpty()) {
            n = nom.getText();
            listCateg = (ArrayList<EventCateg>) ecs.getAll();
            for (EventCateg v : listCateg) {
                if (v.getType().equals(n)) {
                    nameExists = true;
                }
            }
            if (!nameExists) {
                cat = new EventCateg(n);
                ecs.ajouter(cat);
                cc.Refresh();
                Stage stage = (Stage) enregistrer.getScene().getWindow();
                stage.close();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Cette catégorie existe déja!");
                a.setHeaderText("Attention !");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir le nom de la catégorie");
            a.setHeaderText("Attention !");
            a.showAndWait();
        }
    }
}
