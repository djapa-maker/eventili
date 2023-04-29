package gui.backOffice.Service.ListerService;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Service;
import gui.backOffice.Service.modifierService.ModifierServiceController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemServiceController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Text sous_service;
    @FXML
    private Button supp;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif;
    @FXML
    private HBox hboxmodif;

    private Service serv=new Service();
    private String n;
    private int id_s;
   // ListerServiceController li;
    ServiceService ss= new ServiceService();
    ListerServiceController lst=new ListerServiceController();
//------------------------------------------------------------------------------    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                                "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                                "25px"));
    } 
//------------------------------------------------------------------------------    
     public void SetData(Service s) throws SQLException {
         System.out.println(s);
        //affichage des services
        id_s = s.getId_service();
        n = s.getNom_service();
        sous_service.setText(n);
        serv = new Service(n);
    }

//------------------------------------------------------------------------------
    @FXML
    private void Supprimer(ActionEvent event) throws SQLException, IOException {
         Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Est-vous sure de supprimer ce service ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            ss.supprimerById(id_s);
            lst.refresh();
        }
    }
//------------------------------------------------------------------------------
    @FXML
    private void Modifier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/Service/modifierService/modifierService.fxml"));
        Parent loader = addLoader.load();
        ModifierServiceController serviceController = addLoader.getController();
        serviceController.modifierData(serv, id_s);
        serviceController.getController(lst);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        //--refresh
        Stage.showAndWait();
      //  li.refreshData();
    } 
     public void getController(ListerServiceController l){
        lst=l;
    }
}
