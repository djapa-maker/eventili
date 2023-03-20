/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.EventCateg.modifierEventCateg;

import entities.EventCateg;
import gui.backOffice.GestionEvent.EventCateg.CategController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.EventCategService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class ModifierEventCategController implements Initializable {

    @FXML
    private AnchorPane screencateg;
    @FXML
    private TextField nom;
    @FXML
    private Button annuler;
    @FXML
    private Button enregistrer;
    
    private EventCateg cat;
    private String n;
    private int id_c;
    EventCategService ecs = new EventCategService();
    CategController cc = new CategController();

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
    
    public void modifierData(EventCateg ec, int id) throws SQLException {
        id_c = id;
        n = ec.getType();
        nom.setText(n);
        System.out.println(id_c);

    }
        public void getController (CategController c){
        cc=c;
    }

    @FXML
    private void modifierCateg(ActionEvent event) throws IOException, SQLException {
        String name = nom.getText();
        cat = new EventCateg(name);
        cat = ecs.findById(id_c);
        ecs.modifier(name, cat);
        cc.Refresh();
        Stage stage = (Stage) enregistrer.getScene().getWindow();
        stage.close();
    }
    
}
