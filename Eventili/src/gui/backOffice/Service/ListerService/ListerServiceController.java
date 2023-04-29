package gui.backOffice.Service.ListerService;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Service;
import entities.SousServices;
import gui.backOffice.Service.creerService.CreerServiceController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerServiceController implements Initializable {
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
    @FXML
    private ScrollPane scrollbar2;
    ServiceService s = new ServiceService();
    private ArrayList<Service> listServ;
    private String nom;
    private int column = 0;
    private int row = 1;
//------------------------------------------------------------------------------   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SearchDynamic();
        try {
           // scrollbar2.setStyle("-fx-background-color: red; -fx-background: transparent; -fx-border-color: transparent;");
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));   
            listServ = (ArrayList<Service>) s.getAll();
            nbServ.setText("" + listServ.size());
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }  
//------------------------------------------------------------------------------
    private void SearchDynamic() {
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
            listServ= (ArrayList<Service>) s.findByName(newValue);
            Grid.getChildren().clear();
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    });
}
//------------------------------------------------------------------------------    
    @FXML
    private void ajouterService(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/Service/creerService/creerService.fxml"));
        Parent Root = addLoader.load();
        CreerServiceController addController = addLoader.getController();
        addController.getController(this);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();
    }
//------------------------------------------------------------------------------
        public void LoadData() throws SQLException, IOException {
       // Grid.getChildren().clear();
        nbServ.setText("" + listServ.size());
        column = 0;
        row = 1;
        for (Service v : listServ) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemService.fxml"));
            Pane pane = fxmlLoader.load();
            ItemServiceController CardController = fxmlLoader.getController();
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
        
        public void refresh() throws SQLException, IOException {
        Grid.getChildren().clear();
        listServ = (ArrayList<Service>) s.getAll();
        nbServ.setText("" + listServ.size());
        column = 0;
        row = 1;
        for (Service v : listServ) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemService.fxml"));
            Pane pane = fxmlLoader.load();
            ItemServiceController CardController = fxmlLoader.getController();
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
}
