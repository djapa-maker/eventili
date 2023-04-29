/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Ticket;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Service;
import entities.Ticket;
import gui.backOffice.Service.creerService.CreerServiceController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.TicketService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class TicketController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Button serachButton;
    @FXML
    private HBox hboxicon;
    @FXML
    private Label nbServ;
    @FXML
    private Button add;
    @FXML
    private GridPane Grid;
    TicketService s=new TicketService();
  private ArrayList<Ticket> listServ;
    private String nom;
    private int column = 0;
    private int row = 1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      try {
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));
           
            listServ = (ArrayList<Ticket>) s.getAll();
            nbServ.setText("" + listServ.size());
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }  
    
    
public void LoadData() throws SQLException, IOException {
    
     //  nbServ.setText("" + listServ.size());
        column = 0;
        row = 1;
        for (Ticket v : listServ) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemS.fxml"));
            Pane pane = fxmlLoader.load();
            ItemSController CardController = fxmlLoader.getController();
            CardController.SetData(v);
            if (column == 1) {
                column = 0;
                ++row;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }

    }
    @FXML
    private void search(ActionEvent event) {
    }

    @FXML
    private void ajouterService(ActionEvent event) throws IOException {
   
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("Add.fxml"));
        Parent Root = addLoader.load();
        //CreerServiceController addController = addLoader.getController();
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.show();    
    }

    
}
