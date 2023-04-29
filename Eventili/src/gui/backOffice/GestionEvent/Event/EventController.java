/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Event;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Event;
import gui.backOffice.GestionEvent.Event.Item.ItemevController;
import gui.backOffice.GestionEvent.Event.creerEvent.CreerEventController;
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
import javafx.stage.StageStyle;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class EventController implements Initializable {

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
    @FXML
    private Label nbEvent;
    private ArrayList<Event> listEv;
    EventService es = new EventService();
    private int column = 0;
    private int row = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));

            listEv = (ArrayList<Event>) es.getAll();
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        SearchDynamic();
    }

    public void LoadData() throws IOException, SQLException {
        nbEvent.setText("" + listEv.size());
        column = 0;
        row = 1;
        for (Event v : listEv) {
            System.out.println("before");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item/Itemev.fxml"));
            Pane pane = fxmlLoader.load();
            ItemevController CardController = fxmlLoader.getController();
            CardController.setData(v);
            CardController.getController(this);
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

    public void Refresh() throws IOException, SQLException {
        Grid.getChildren().clear();
        listEv = (ArrayList<Event>) es.getAll();
        LoadData();
    }

    private void SearchDynamic() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                listEv = (ArrayList<Event>) es.findEventByName(newValue);
                Grid.getChildren().clear();
                LoadData();
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    @FXML
    private void ajouterEvent(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/GestionEvent/Event/creerEvent/creerEvent.fxml"));
        Parent Root = addLoader.load();
        CreerEventController addController = addLoader.getController();
        addController.getController(this);
        Stage Stage = new Stage();
        Stage.initStyle(StageStyle.UNDECORATED);
        Stage.setScene(new Scene(Root));
        Stage.show();
        
    }

}
