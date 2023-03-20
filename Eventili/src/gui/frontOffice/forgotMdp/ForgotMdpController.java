/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.forgotMdp;

import entities.CurrentUser;
import entities.SendMail;
import services.PersonneService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ForgotMdpController implements Initializable {
CurrentUser cu;
    Stage Stage2;
    @FXML
    private TextField txt_usr;
    @FXML
    private TextField txt_email;
    @FXML
    private Button Reset;
    @FXML
    private Label labelemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txt_email.setDisable(true);
        Reset.setDisable(true);
    }    

    @FXML
    private void sendcode(ActionEvent event) throws SQLException {
        txt_usr.setStyle("-fx-border-color:WHITE");
         cu = CurrentUser.CurrentUser();
            PersonneService us = new PersonneService();
            if (us.verifierEmailBd(txt_usr.getText())) {
                
                txt_email.setDisable(false);
                Reset.setDisable(false);
                labelemail.setText("");
                labelemail.setText("un code a été envoyé a votre Email, retapez-le");
 labelemail.setTextFill(Color.DARKBLUE);
                String code = us.getAlphaNumericString(8);
                cu.targetId = us.geIdbyemail(txt_usr.getText());
                cu.code = code;
                String email = txt_usr.getText();
                
                
                System.out.println(cu.code);
                System.out.println(cu.targetId);
                
                us.updateCode(cu.code,  cu.targetId);
                
                String cn = "Saissisez ce code pour réinitialiser votre mot de passe : " + cu.code;

                String sb = "Mot de passe oublié";
                SendMail.sendMail(email, sb, cn);
            } else {
                txt_usr.setStyle("-fx-border-color:RED");
//                labelemail.setText("email inexistant");
//                labelemail.setTextFill(Color.RED);
            }
    }

    @FXML
    private void Reset_onclick(ActionEvent event) {
         System.out.println(cu);
        if (cu.code.equals(txt_email.getText())) {
            try {
            Parent   root = FXMLLoader.load(getClass().getResource("reset.fxml"));
            Stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
                        root.setOnMousePressed(pressEvent -> {
                        root.setOnMouseDragged(dragEvent -> {
        Stage2.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
        Stage2.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
    });
});
                        Scene  scene = new Scene(root);
                        Stage2.setScene(scene);
                        Stage2.show();
            } catch (IOException ex) {
                 System.out.println(ex.getMessage());
            }
        } else {
            labelemail.setText("code incorrect");
            labelemail.setTextFill(Color.RED);
        }
           
    }


    
}
