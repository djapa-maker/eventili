package gui.backOffice.Service.ListerSousService;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Service;
import entities.SousServices;
import gui.backOffice.Service.creerSousService.CreerSousServiceController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ServiceService;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerSousServiceController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Button serachButton;
    @FXML
    private HBox hboxicon;
    @FXML
    private Label nbServ;
    @FXML
    private ComboBox<String> mychoicebox;
    @FXML
    private Button add;
    @FXML
    private GridPane Grid;
    @FXML
    private ScrollPane scrollbar1;
    SousServiceService v = new SousServiceService();
    private ArrayList<SousServices> listServ;
    private String nom;
    private int column = 0;
    private int row = 1;
    private int size=0;
    
//------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SearchDynamic();
        try {
          //scrollbar1.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
            hboxicon.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, "20px"));
            listerCategory();
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                //affichage par categorie
                public void handle(ActionEvent event) {
                    String s = mychoicebox.getValue();            
                    if (!s.isEmpty()) {
                        try {
                            listServ = (ArrayList<SousServices>) v.getAllByService(s);
                            Grid.getChildren().clear();
                            LoadData();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        try {
                            LoadData();
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            };
            mychoicebox.setOnAction(event);
            listServ = (ArrayList<SousServices>) v.getAll();
            nbServ.setText("" + listServ.size());
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
//------------------------------------------------------------------------------
    public void LoadData() throws SQLException, IOException {
        //Grid.getChildren().clear();
        nbServ.setText("" + listServ.size());
        column = 0;
        row = 1;
        for (SousServices v : listServ) {
//            System.out.println(v);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemSousService.fxml"));
            Pane pane = fxmlLoader.load();
            ItemSousServiceController itemController = fxmlLoader.getController();
            itemController.SetData(v);
            itemController.dataController(this);
            if (column == 1) {
                column = 0;
                ++row;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }
//------------------------------------------------------------------------------
        public void refresh() throws SQLException, IOException {
        Grid.getChildren().clear();
        listServ = (ArrayList<SousServices>) v.getAll();
        size+=listServ.size();
        nbServ.setText("" + size);
        column = 0;
        row = 1;
        for (SousServices v : listServ) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemSousService.fxml"));
            Pane pane = fxmlLoader.load();
            ItemSousServiceController CardController = fxmlLoader.getController();
            CardController.SetData(v);
            CardController.dataController(this);
            if (column == 1) {
                column = 0;
                ++row;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }
//------------------------------------------------------------------------------
    public void listerCategory() {
        ServiceService c = new ServiceService();
        ArrayList<Service> list = (ArrayList<Service>) c.getAll();
        for (int i = 0; i < list.size(); i++) {
            nom = list.get(i).getNom_service();
            mychoicebox.getItems().add(nom);
        }
    }
//------------------------------------------------------------------------------
    private void SearchDynamic() {
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
            listServ= (ArrayList<SousServices>) v.findListByName(newValue);
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
    private void ajouterSousService(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/Service/creerSousService/creerSousService.fxml"));
        Parent Root = addLoader.load();
        CreerSousServiceController crt=addLoader.getController();
        crt.loadController(this);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.show();
    }
}
