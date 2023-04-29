package gui.backOffice.Service.ListerAvis;

import entities.Avis;
import entities.SousServices;
import gui.backOffice.Service.ListerSousService.ItemSousServiceController;
import gui.backOffice.Service.creerSousService.CreerSousServiceController;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.AvisService;
import services.SousServiceService;


/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerAVController implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private Button serachButton;
    @FXML
    private HBox hboxicon;
    private Label nbAv;
    @FXML
    private ScrollPane scrollbar1;
    @FXML
    private GridPane Grid;

   //Avis v = new Avis();
    AvisService v1 = new AvisService();
    private ArrayList<Avis> listAv=new ArrayList<>();
    private String nom;
    private int column = 0;
    private int row = 1;
    private int size=0;
    @FXML
    private Label nbServ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SearchDynamic();
        try {
            listAv = (ArrayList<Avis>) v1.getAll();
            LoadData();
        } catch (SQLException ex) {
            ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
         System.out.println("aaaaaaaaaaaaaaaaaaaaaa"+listAv);
    }    
//------------------------------------------------------------------------------
    public void LoadData() throws SQLException, IOException {
        //Grid.getChildren().clear();
        nbServ.setText("" + listAv.size());
        column = 0;
        row = 1;
        System.out.println(listAv.size());
        for (Avis b : listAv) {
            System.out.println("before");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemAV.fxml"));
            Pane pane = fxmlLoader.load();
            ItemAVController itemController = fxmlLoader.getController();
            itemController.SetData(b);
             System.out.println("after");
            itemController.dataController(this);
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
        listAv = (ArrayList<Avis>) v1.getAll();
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh"+listAv);
         nbServ.setText("" + listAv.size());
        column = 0;
        row = 1;
        for (Avis v : listAv) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemAV.fxml"));
            Pane pane = fxmlLoader.load();
            ItemAVController itemController = fxmlLoader.getController();
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
    private void SearchDynamic() {
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
            listAv= (ArrayList<Avis>) v1.findByName(newValue);
            Grid.getChildren().clear();
            LoadData();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    });
}


//    private void ajouterAvis(ActionEvent event) throws IOException {
//        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/Service/ListerAvis/creerAvis/creerAvis.fxml"));
//        Parent Root = addLoader.load();
//      //  CreerAvisController crt=addLoader.getController();
//        //crt.loadController(this);
//        Stage Stage = new Stage();
//        Stage.setScene(new Scene(Root));
//        Stage.show();
//    }
    
}
