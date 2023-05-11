/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.sidebar;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class SideBarController implements Initializable {

    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    @FXML
    private Button btnhome;
    @FXML
    private FontAwesomeIconView iconhome;
    @FXML
    private Button btnEvent;
    @FXML
    private FontAwesomeIconView iconEvent;
    @FXML
    private FontAwesomeIconView iconSquare;
    @FXML
    private Circle ProfileCircle;
    @FXML
    private BorderPane BorderPane;
    @FXML
    private Button btndeconnexion;
    @FXML
    private FontAwesomeIconView iconComment;
    @FXML
    private Button buttonServ;
    @FXML
    private Button buttonReclam;
    @FXML
    private FontAwesomeIconView iconReclam;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            if(!p1.getRole().equals("partenaire"))
            {
                buttonServ.setVisible(false);
            }
            GetProfileImg();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainPage();
    }

    private void GetProfileImg() throws FileNotFoundException {
       
        String Image = data.getImage().getLast();
        FileInputStream inputstream = new FileInputStream("C:\\xampp\\htdocs\\img\\" + Image);
        Image image = new Image(inputstream);
        ProfileCircle.setStroke(javafx.scene.paint.Color.ORANGE);
        ProfileCircle.setFill(new ImagePattern(image));
        
    }

    private void MainPage() {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../home/ho.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(255, 134, 0));
            iconEvent.setFill(Color.rgb(58, 50, 137));
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconComment.setFill(Color.rgb(58, 50, 137));
            iconReclam.setFill(Color.rgb(58, 50, 137));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btnhomeF(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../home/ho.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconEvent.setFill(Color.rgb(58, 50, 137));
            iconhome.setFill(Color.rgb(255, 134, 0));
            iconComment.setFill(Color.rgb(58, 50, 137));
            iconReclam.setFill(Color.rgb(58, 50, 137));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void btnReclamF(ActionEvent event){
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../reclamations/reclamations.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconEvent.setFill(Color.rgb(58, 50, 137));
            iconComment.setFill(Color.rgb(58, 50, 137));
            iconReclam.setFill(Color.rgb(255, 134, 0));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void btnEventF(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../organisationev/MesEvenements.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconEvent.setFill(Color.rgb(255, 134, 0));
            iconComment.setFill(Color.rgb(58, 50, 137));
            iconReclam.setFill(Color.rgb(58, 50, 137));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void service(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../servicePrestataire/ListerServicePrestataire.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconEvent.setFill(Color.rgb(58, 50, 137));
            iconSquare.setFill(Color.rgb(255, 134, 0));
            iconComment.setFill(Color.rgb(58, 50, 137));
            iconReclam.setFill(Color.rgb(58, 50, 137));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Avis(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../AvisClient/ListerAvisClient.fxml"));
            BorderPane.setCenter(view);
            iconhome.setFill(Color.rgb(58, 50, 137));
            iconEvent.setFill(Color.rgb(58, 50, 137));
            iconSquare.setFill(Color.rgb(58, 50, 137));
            iconComment.setFill(Color.rgb(255, 134, 0));
            iconReclam.setFill(Color.rgb(58, 50, 137));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void modifierprofil(MouseEvent event) throws IOException {

        /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modif/modif.fxml"));
         Parent root = (Parent) fxmlLoader.load();
            
         Stage stg=(Stage) ProfileCircle.getScene().getWindow();
         Scene s=new Scene(root);
         stg.setTitle("Eventili");
         stg.setScene(s);
         stg.show();
         */
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../../backOffice/modifierU/modifier.fxml"));
            BorderPane.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void deconnexion(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../eventili/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stg = (Stage) ProfileCircle.getScene().getWindow();
        Scene s = new Scene(root);
        stg.setTitle("Eventili");
        stg.setScene(s);
        stg.show();
    }

}
