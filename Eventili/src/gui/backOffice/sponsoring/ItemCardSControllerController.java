/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.backOffice.sponsoring;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.sponsoring;
import gui.backOffice.users.UserController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sel3a
 */
public class ItemCardSControllerController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private ImageView icon;
    @FXML
    private Text nbimpreession;
    @FXML
    private Text datedebut;
    @FXML
    private Text nomevent;
    @FXML
    private Text datefin;
    @FXML
    private Button supp1;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif1;
    @FXML
    private HBox hboxmodif;
UserController us=new UserController();
 sponsoring sp=new sponsoring();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                                "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                                "25px"));
        
        // TODO
    } 
       public void getController(UserController u){
        us=u;
    }
 public void SetData(sponsoring s) throws SQLException, FileNotFoundException {
 nbimpreession.setText(String.valueOf(s.getNombre_impression()));
 nomevent.setText(s.getE().getTitle());
 datedebut.setText(s.getDate_debut().toString());
 datefin.setText(s.getDate_fin().toString());

    }     

    @FXML
    private void supprimer(MouseEvent event) {
    }

    @FXML
    private void modifier(MouseEvent event) throws SQLException, FileNotFoundException, IOException {
                FXMLLoader addLoader = new FXMLLoader(getClass().getResource("modifersponsor.fxml"));
        Parent loader = addLoader.load();
        ModifersponsorController modifController = addLoader.getController();
       modifController.modifierData(sp);
       modifController.getController(us);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
}
