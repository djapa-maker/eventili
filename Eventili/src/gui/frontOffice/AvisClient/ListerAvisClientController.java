/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.AvisClient;

import entities.Avis;
import entities.Personne;
import gui.backOffice.Service.ListerAvis.ItemAVController;
import gui.backOffice.Service.modifierSousService.ModifierSousServiceController;
import gui.frontOffice.AvisClient.mdfAV.MdfAVController;
import gui.sigleton.singleton;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.AvisService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerAvisClientController implements Initializable {
    
    @FXML
    private TextField searchbar;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    AvisService v1 = new AvisService();
    private ArrayList<Avis> listAv = new ArrayList<>();
    private ArrayList<Avis> LAV = new ArrayList<>();
    private String nom;
    private int column = 0;
    private int row = 1;
    private int size = 0;
    @FXML
    private Text nbravis;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            listAv = (ArrayList<Avis>) v1.getAll();
            LAV = (ArrayList<Avis>) v1.findByIdP(p1.getId_pers());
            LoadData();
        } catch (SQLException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        }
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + listAv);
        
    }
    
    public void LoadData() throws SQLException, IOException {
        //Grid.getChildren().clear();
        nbravis.setText("" + LAV.size());
        column = 0;
        row = 1;
        System.out.println(listAv.size());
        for (Avis b : listAv) {
            if (b.getP().getId_pers() == p1.getId_pers()) {
                System.out.println("before");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("AvisClient.fxml"));
                Pane pane = fxmlLoader.load();
                AvisClientController itemController = fxmlLoader.getController();
                itemController.SetData(b);
                itemController.getController(this);
                System.out.println("after");
                //itemController.dataController(this);
                if (column == 1) {
                    column = 0;
                    ++row;
                }
                grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        }
    }
    
    public void refresh() throws SQLException, IOException {
        grid.getChildren().clear();
        listAv = (ArrayList<Avis>) v1.getAll();
        LoadData();
    }
}
