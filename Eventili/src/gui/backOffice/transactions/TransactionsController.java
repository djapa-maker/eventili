/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.transactions;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Personne;
import entities.Service;
import entities.Transactions;
import gui.backOffice.users.AjoutController;
import gui.backOffice.users.ItemCardController;
import gui.backOffice.users.UserController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.PersonneService;
import services.TransactionsService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
 
public class TransactionsController implements Initializable {
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
    private ArrayList<Transactions> listtran;
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
            listtran = (ArrayList<Transactions>) ts.getAll();
     try {
        LoadData();
    } catch (IOException ex) {
         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SQLException ex) {
         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    


    
        

    @FXML
    private void addf(ActionEvent event) throws IOException {
         FXMLLoader addLoader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        Parent loader = addLoader.load();
        AjoutController modifController = addLoader.getController();

        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }

   

    void LoadData() throws IOException, SQLException {
      
        column = 0;
        row = 1;
//yesmine.guesmi@esprit.tn
        for (Transactions v : listtran) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemCardT.fxml"));
            Pane pane = fxmlLoader.load();
            ItemCardTController CardController = fxmlLoader.getController();
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
            listtran = (ArrayList<Transactions>) ts.findByName(newValue);
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
