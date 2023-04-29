/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.organisationev.DetailEvent;

import entities.Event;
import entities.ServiceReservation;
import entities.SousServices;
import gui.frontOffice.client.ItemSousService.ItemSousServiceController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.EventService;
import services.ServiceReservationService;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class EventDetailsController implements Initializable {

    @FXML
    private Text evname;
    @FXML
    private GridPane Grid;
    @FXML
    private ScrollPane scroll1;
    private int id;
    ServiceReservationService srs = new ServiceReservationService();
    ServiceReservation sr ;
    EventService es = new EventService();
    Event e;  
    ArrayList<SousServices> listSS = new ArrayList<>();
    SousServiceService ss = new SousServiceService();

    

    public void setEventId(int idev) throws SQLException, IOException {
        id = idev;
        sr = srs.findByIdEvent(id);
        e = es.findEventById(id);
        evname.setText(e.getTitle());
        LoadData();
        
    }

        public void LoadData() throws SQLException, IOException {
        scroll1.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-background-color: transparent;");
        listSS = (ArrayList<SousServices>) sr.getList();
        int column = 0;
        int row = 1;
        Grid.getChildren().clear();
        for (SousServices v : listSS) {
            //  System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
            Pane pane = fxmlLoader.load();
            ItemController itemController = fxmlLoader.getController();
            itemController.setData(v);
            if (e != null) {
                itemController.setDataEvent(e);
            }
            if (column == 4) {
                column = 0;
                row++;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

}
