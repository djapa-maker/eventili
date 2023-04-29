/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.forgotMdp;
import entities.CurrentUser;
import entities.Personne;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ResetController implements Initializable {

    @FXML
    private Button modifier;
    @FXML
    private TextField nv_pass;
    @FXML
    private TextField conv_pass;
Stage app_stage;
    @FXML
    private Label labelmdp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void resetpass(ActionEvent event) {
         CurrentUser cu = CurrentUser.CurrentUser();
if(!nv_pass.getText().isEmpty()&& !conv_pass.getText().isEmpty())
        {
         if(test()){
            if (nv_pass.getText().equals(conv_pass.getText()))
            {
                PersonneService us = new PersonneService();
                
                Personne u = us.findById(cu.targetId);
                System.out.println(u);
                String hashed = BCrypt.hashpw(nv_pass.getText(), BCrypt.gensalt());
                u.setMdp(hashed);
                us.modifier(cu.targetId,u); 
                
                Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("../../../eventili/login.fxml"));
                //Creates a Parent called loader and assign it as ScReen2.FXML
                              loader.setOnMousePressed(pressEvent -> {
                        loader.setOnMouseDragged(dragEvent -> {
        app_stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
        app_stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
    });
});

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                 app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                app_stage.setScene(scene); //This sets the scene as scene

                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
            }
            }
            else{
            labelmdp.setText("Les deux mots de passe ne sont pas identiques");
            labelmdp.setTextFill(Color.RED);
            }
        }
            }
else{
     labelmdp.setText("veuillez remplir les deux champs");
              labelmdp.setTextFill(Color.rgb(210, 39, 30));
}
    }

    private boolean test() {
       int x=0;
       if(nv_pass.getText().length()<8){
           labelmdp.setText("veuillez saisir un mdp long");
              labelmdp.setTextFill(Color.rgb(210, 39, 30));
              x++;
       }else if(!nv_pass.getText().matches("(?=.*[0-9]).*")){
        labelmdp.setText("au moins un chiffre");
              labelmdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else if(!nv_pass.getText().matches("(?=.*[a-zA-Z]).*")){
        labelmdp.setText("au moins une lettre");
              labelmdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
               else{
                   labelmdp.setText("");
              }
      if(x==0)
                  return true;
              else
                  return false;
    }
    
    
}
