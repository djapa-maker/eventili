/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.EventCateg;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.EventCateg;
import gui.backOffice.GestionEvent.EventCateg.Item.ItemController;
import gui.backOffice.GestionEvent.EventCateg.creerEventCateg.CreerEventCategController;
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
import services.EventCategService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class CategController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private HBox hboxicon;
    @FXML
    private Button add;
    @FXML
    private GridPane Grid;
    @FXML
    private Label nbCateg;

    private EventCateg cat = new EventCateg();
    EventCategService ecs = new EventCategService();
    private ArrayList<EventCateg> listCateg;
    private int column = 0;
    private int row = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));

            listCateg = (ArrayList<EventCateg>) ecs.getAll();
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        SearchDynamic();
    }

    @FXML
    private void ajouterEventCateg(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("creerEventCateg/CreerEventCateg.fxml"));
        Parent Root = addLoader.load();
        CreerEventCategController addController = addLoader.getController();
        addController.getController(this);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.show();
    }

    public void LoadData() throws IOException, SQLException {
        nbCateg.setText("" + listCateg.size());
        column = 0;
        row = 1;
        for (EventCateg v : listCateg) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item/Item.fxml"));
            Pane pane = fxmlLoader.load();
            ItemController CardController = fxmlLoader.getController();
            CardController.SetData(v);
            CardController.getController(this);
            if (column == 1) {
                column = 0;
                ++row;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }

    public void Refresh() throws IOException, SQLException {
        Grid.getChildren().clear();
        listCateg = (ArrayList<EventCateg>) ecs.getAll();
        LoadData();
    }

    private void SearchDynamic() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                listCateg = (ArrayList<EventCateg>) ecs.findCategByName(newValue);
                System.out.println(listCateg);
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
