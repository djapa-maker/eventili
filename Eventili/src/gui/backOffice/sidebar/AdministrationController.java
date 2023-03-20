/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.sidebar;

import entities.Personne;
import gui.sigleton.singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class AdministrationController implements Initializable {
    singleton data= singleton.getInstance();
    Personne p;
    @FXML
    private Circle ProfileCircle;
    @FXML
    private Button btnServices;
    @FXML
    private Button btnRec;
    @FXML
    private Button btnUsers;
    @FXML
    private Button btnEvent;
    @FXML
    private BorderPane BorderPane;
    @FXML
    private Button btnTran;
    @FXML
    private Button btndeconnexion;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p=data.getUser();
        System.out.println(p.getNom_pers());
        try {
            GetProfileImg();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainPage();
        
    }    

    private void GetProfileImg() throws FileNotFoundException {
        
        String Image=data.getUser().getImage();
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+Image); 
        Image image = new Image(inputstream); 
        ProfileCircle.setStroke(javafx.scene.paint.Color.ORANGE);
        ProfileCircle.setFill(new ImagePattern(image)); 
    }
    
    private void MainPage(){
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../users/user.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void btnUsersF (ActionEvent event){
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../users/user.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @FXML
    private void btnEventsF (ActionEvent event){
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("../GestionEvent/CallingPage.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
    @FXML
    private void btnServicesF (ActionEvent event){
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("../Service/Prestataire.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    
    } 
    
     @FXML
    private void btnRecF (ActionEvent event){
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../reclamations/Reclamations.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    
    } 

    @FXML
    private void btnTranF(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../transactions/Transactions.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) { 
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void modifierprofil(MouseEvent event) throws IOException {
     //        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modifierU/modifier.fxml"));
//          
//            Parent loader = fxmlLoader.load();
//            
//             Stage Stage = new Stage();
//            Stage.setScene(new Scene(loader));
//            Stage.setTitle("Eventili");
//            Stage.show();
           try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../modifierU/modifier.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) { 
            System.out.println(ex.getMessage());
        } 
            

    }

    @FXML
    private void deconnexion(MouseEvent event) throws IOException {
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../eventili/login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage) ProfileCircle.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
    }
    
}
