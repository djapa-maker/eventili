/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.organisationev;

import entities.Event;
import entities.Personne;
import gui.frontOffice.organisationev.creerevenement.CreationevController;
import gui.sigleton.singleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class MesEvenementsController implements Initializable {

    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Text nbrevent;
    DateTimeFormatter datedebF = DateTimeFormatter.ofPattern("EEE de HH:mm");
    DateTimeFormatter datefinF = DateTimeFormatter.ofPattern("à HH:mm");

    EventService es = new EventService();
    private ArrayList<Event> events;
    int column = 0;
    int row = 1;
    @FXML
    private TextField searchbar;
    @FXML
    private Button searchbtn;
    @FXML
    private Button createbtn;
    @FXML
    private VBox eventCard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            events = (ArrayList<Event>) es.getAll();
            LoadData();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        SearchDynamic();

    }

    public void LoadData() throws SQLException, IOException {
        ArrayList<Event> ev = new ArrayList<>();
        column = 0;
        row = 1;
        for (Event e : events) {
            //System.out.println("before");
            System.out.println("p1" + p1.getId_pers());
            if (e.getPers().getId_pers() == p1.getId_pers()) {
                ev.add(e);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("EventsList.fxml"));
                System.out.println("after");
                Pane pane = loader.load();
                EventsListController eventsListController = loader.getController();
                eventsListController.setData(e);
                eventsListController.getController(this);
                pane.setOnMouseClicked(eventsListController::handlePaneClick);

                if (column == 3) {
                    column = 0;
                    ++row;
                }

                grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));
            }

        }
        nbrevent.setText("(" + Integer.toString(ev.size()) + ")");
    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("creerevenement/creationev.fxml"));
        Parent Root = addLoader.load();
        CreationevController addController = addLoader.getController();
        addController.getController(this);
        Stage Stage = new Stage();
        Stage.initStyle(StageStyle.UNDECORATED);
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();
    }

    public void Refresh() throws IOException, SQLException {
        grid.getChildren().clear();
        events = (ArrayList<Event>) es.getAll();
        LoadData();
    }

    private void SearchDynamic() {
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                events = (ArrayList<Event>) es.findEventByName(newValue);
                grid.getChildren().clear();
                LoadData();
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    @FXML
    private void Search() {

    }

}
