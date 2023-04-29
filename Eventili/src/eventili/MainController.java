/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventili;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class MainController implements Initializable {
@FXML
    private Label label;
@FXML
    private VBox vbox;
private Parent fxml;
    @FXML
    private Button btnConnecter;
    @FXML
    private Button btnInscrire;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       TranslateTransition t=new TranslateTransition(Duration.seconds(1), vbox);
    t.setToX(vbox.getLayoutX()*21);
    t.play();
    t.setOnFinished((e)->{
        try {
            fxml =FXMLLoader.load(getClass().getResource("../gui/frontOffice/signin/signin.fxml"));
        
            vbox.getChildren().removeAll();
             vbox.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
        }
    });
    }    

    @FXML
    private void open_signin(ActionEvent event){
         TranslateTransition t=new TranslateTransition(Duration.seconds(1), vbox);
    t.setToX(vbox.getLayoutX()*21);
    t.play();
    t.setOnFinished((e)->{
        try {
            fxml =FXMLLoader.load(getClass().getResource("../gui/frontOffice/signin/signin.fxml"));
        
            vbox.getChildren().removeAll();
             vbox.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
        }
    });
    }
    @FXML
    private void open_login(ActionEvent event){
         TranslateTransition t=new TranslateTransition(Duration.seconds(1), vbox);
    t.setToX(0);
    t.play();
    t.setOnFinished((e)->{
        try {
            fxml =FXMLLoader.load(getClass().getResource("../gui/frontOffice/login/login.fxml"));
        
            vbox.getChildren().removeAll();
             vbox.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
        }
    });
    }
    @FXML
    private void connecter(MouseEvent event) {
    }
    
}
