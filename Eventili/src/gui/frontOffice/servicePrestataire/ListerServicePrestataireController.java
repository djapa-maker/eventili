package gui.frontOffice.servicePrestataire;

import entities.Personne;
import entities.SousServices;
import gui.frontOffice.servicePrestataire.creerSousServicePrestataire.CreerSSPrestataireController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.PersonneService;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerServicePrestataireController implements Initializable {
    @FXML
    private TextField searchbar;
    @FXML
    private Button createbtn;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Text nbrevent;
    singleton data= singleton.getInstance();
    Personne p1=data.getUser();
    SousServiceService ss = new SousServiceService();
    PersonneService prr = new PersonneService();
    int p=0;
    private ArrayList<SousServices> s;
    int column = 0;
    int row = 1;
    private int size=0;
    ArrayList<SousServices> l= new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        SearchDynamic();
          try {
//            s = (ArrayList<SousServices>) ss.getAllByPrestataire(p);
              s = (ArrayList<SousServices>) ss.getAll();
              l= (ArrayList<SousServices>) ss.findServiceByPers(p1.getId_pers());
              size=l.size();
            LoadData();
            //nbrevent.setText( Integer.toString(l.size()));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }  
    
    public void LoadData() throws SQLException, IOException {
        column = 0;
        row = 1;
        nbrevent.setText("" + size);
        for ( SousServices serv : s) {
            System.out.println("fghjklm"+serv.getPers().getId_pers());
            System.out.println("zzzzzzzzzzzzzzzzz"+p1.getId_pers());
            if(serv.getPers().getId_pers()==p1.getId_pers())
            {
            System.out.println("sdfghjklgfqsdfghjkl");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("itemServicePrestataire.fxml"));
            Pane pane = loader.load();
            ItemServicePrestataireController ItemServiceController = loader.getController();
            ItemServiceController.SetData(serv);
            ItemServiceController.getController(this);
            if (column == 3) {
                column = 0;
                ++row;
            }
            grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(20));
        }
        }
    }
        public void refresh() throws SQLException, IOException {
             l= (ArrayList<SousServices>) ss.findServiceByPers(p1.getId_pers());
              
           size=l.size(); 
           //nbrevent.setText("" + size);
           grid.getChildren().clear();
          // p=prr.getAll().get(1).getId_pers();
           //s = (ArrayList<SousServices>) ss.getAllByPrestataire(p);
           s = (ArrayList<SousServices>) ss.getAll();
           LoadData();
//        column = 0;
//        row = 1;
//        for ( SousServices serv : s) {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("itemServicePrestataire.fxml"));
//            Pane pane = loader.load();
//            ItemServicePrestataireController ItemServiceController = loader.getController();
//            ItemServiceController.SetData(serv);
//            ItemServiceController.getController(this);
//            if (column == 3) {
//                column = 0;
//                ++row;
//            }
//            grid.add(pane, column++, row);
//            GridPane.setMargin(pane, new Insets(20));
//        }
    }
    @FXML
    private void add(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("creerSousServicePrestataire/CreerSSPrestataire.fxml"));
        Parent Root = addLoader.load();
        CreerSSPrestataireController c=addLoader.getController();
        c.loadController(this);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();
    }
//------------------------------------------------------------------------------
    private void SearchDynamic() {
    searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
            s= (ArrayList<SousServices>) ss.findListByName(newValue);
            grid.getChildren().clear();
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    });
}
    
    
    
}
