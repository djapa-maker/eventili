/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.backOffice.sponsoring;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Transactions;
 import entities.sponsoring;
import gui.backOffice.sponsoring.ItemCardSControllerController;
import gui.backOffice.users.UserController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import services.EventService;
import services.TransactionsService;
import services.sponsoringservice;

/**
 * FXML Controller class
 *
 * @author sel3a
 */
public class SponsoringControllerController implements Initializable {
 Connection cnx;
    @FXML
    private TextField search;
    @FXML
    private Button serachButton;
    @FXML
    private HBox hboxicon;
    @FXML
    private Button add;
    @FXML
    private GridPane Grid;
    TransactionsService ts = new TransactionsService();
    sponsoringservice ss=new sponsoringservice();
    EventService es=new EventService();
    private ArrayList<sponsoring> listspon;
    private String nom;
    private int column = 0;
    private int row = 1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               SearchDynamic();
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));
            listspon = (ArrayList<sponsoring>) ss.getAll();
     try {
        LoadData();
    } catch (IOException ex) {
         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SQLException ex) {
         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    

    @FXML
    private void addf(ActionEvent event) {
    }
        void LoadData() throws IOException, SQLException {
      
        column = 0;
        row = 1;
//yesmine.guesmi@esprit.tn
        for (sponsoring v : listspon) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemCardSController.fxml"));
            Pane pane = fxmlLoader.load();
            ItemCardSControllerController CardController = fxmlLoader.getController();
            System.out.println(v.getE());
            System.out.println(v.getE().getTitle());
            CardController.SetData(v);
          

            if (column == 1) {
                column = 0;
                ++row;
            }

            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
       }
        
    }
          private void SearchDynamic() {
     
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
            listspon = (ArrayList<sponsoring>) ss.findByName(newValue);
            System.out.println("listspon:"+listspon);
                    System.out.println("newValue :"+newValue);
            Grid.getChildren().clear();  
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    });
}
}
