/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.users;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Personne;
import entities.Service;
import entities.imagepers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.PersonneService;
import services.ServiceService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class UserController implements Initializable {
@FXML
    private TextField search;
    @FXML
    private Button serachButton;
    @FXML
    private HBox hboxicon;
    @FXML
    private Button add;
    PersonneService v1 = new PersonneService();
    PersonneService v = new PersonneService();
    private ArrayList<Personne> listServ;
    private ArrayList<imagepers> listI;
    private String nom;
    private int column = 0;
    private int row = 1;
    @FXML
    private GridPane Grid;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));
           listServ = (ArrayList<Personne>) v.getAll();
    try {
        LoadData();
    } catch (IOException ex) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    }
    SearchDynamic();
    }    


    
        

    @FXML
    private void addf(ActionEvent event) throws IOException {
         FXMLLoader addLoader = new FXMLLoader(getClass().getResource("ajout.fxml"));
        Parent loader = addLoader.load();
        AjoutController modifController = addLoader.getController();
        modifController.getController(this);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }

   

    void LoadData() throws IOException, SQLException {
      
        
        column = 0;
        row = 1;
//yesmine.guesmi@esprit.tn
        for (Personne v : listServ) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemCard.fxml"));
            Pane pane = fxmlLoader.load();
           ItemCardController CardController = fxmlLoader.getController();
           imagepers i=v1.findByIdI(v.getId_pers());
            CardController.SetData(v,i);
           CardController.getController(this);
            if (column == 1) {
                column = 0;
                ++row;
            }

            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
       }
        
    }

public void Refresh() throws IOException, SQLException{
    Grid.getChildren().clear();
     listServ = (ArrayList<Personne>) v.getAll();
      LoadData();
}
    
    private void SearchDynamic() {
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
             listServ= (ArrayList<Personne>) v.findListByName(newValue);
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
