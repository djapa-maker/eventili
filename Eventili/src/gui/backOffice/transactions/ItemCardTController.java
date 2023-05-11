/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.backOffice.transactions;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Transactions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.TransactionsService;

/**
 * FXML Controller class
 *
 * @author sel3a
 */
public class ItemCardTController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private ImageView icon;
    @FXML
    private Text mode_trans;
    @FXML
    private Text devis; 
    @FXML
    private Text nom; 
    @FXML
    private Text date_trans;
    @FXML
    private Text montant_tot;

 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        
    }    
public void SetData(Transactions s) throws SQLException, FileNotFoundException {
        //affichage des services
       TransactionsService ts=new TransactionsService();
 
       System.out.println(ts.findimgId(s.getP().getId_pers()));
       FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+ts.findimgId(s.getP().getId_pers())); 
        Image image = new Image(inputstream); 
         icon.setImage(image);
        nom.setText(s.getP().getNom_pers());
        devis.setText(s.getDevis());
 mode_trans.setText(s.getMode_trans());
 date_trans.setText(s.getDate_trans().toString());
 montant_tot.setText(String.valueOf(s.getMontant_tot()));
    }   
    
}
 