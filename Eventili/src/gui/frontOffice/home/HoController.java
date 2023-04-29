/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.home;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Event;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class HoController implements Initializable {
  EventService v = new EventService();
    private ArrayList<Event> listServ;
    @FXML
    private Text nbrevent;
    @FXML
    private TextField searchbar;
    @FXML
    private Button createbtn;
    @FXML
    private VBox eventCard;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
DateTimeFormatter datedebF = DateTimeFormatter.ofPattern("EEE de HH:mm");
    DateTimeFormatter datefinF = DateTimeFormatter.ofPattern("à HH:mm");

    EventService es = new EventService();
    private ArrayList<Event> events;
    int column = 0;
    int row = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            events = (ArrayList<Event>) es.getAll();
           LoadData();
            //nbrevent.setText("(" + Integer.toString(events.size()) + ")");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       // SearchDynamic();
    }    


    @FXML
    private void add(ActionEvent event) {
    }
private void SearchDynamic() {
    searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
             listServ= (ArrayList<Event>) v.findEventByName(newValue);
             System.out.println(listServ);
            grid.getChildren().clear();
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    });
     }
    private void LoadData() throws SQLException, IOException{
      column = 0;
        row = 1;
        for (Event e : events) {
             if (e.getPrice()> 0 && e.getVisibilite().compareTo("Public")==0)  {
       
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Homeeventlist.fxml"));
            Pane pane = loader.load();
            HomeeventlistController eventsListController = loader.getController();
            eventsListController.setData(e);

            if (column == 3) {
                column = 0;
                ++row;
            }
          

            grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(20));
        }
             
                }
    }





    
}
