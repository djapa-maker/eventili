package gui.backOffice.Service.modifierService;

import entities.Service;
import gui.backOffice.Service.ListerService.ItemServiceController;
import gui.backOffice.Service.ListerService.ListerServiceController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ModifierServiceController implements Initializable {

    @FXML
    private AnchorPane screencateg;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private TextField nom;
    @FXML
    private Button annuler;
    @FXML
    private Button enregistrer;

    ServiceService ss = new ServiceService();
    Service a = new Service();

    private int id_ss;
    private String n;
    ListerServiceController lst=new ListerServiceController();
//------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    nom.setTextFormatter(textFormatter);}
//------------------------------------------------------------------------------
     TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() > 20) {
            return null;
        }
        return change;
    });
//------------------------------------------------------------------------------
    @FXML
    private void close(ActionEvent event) {        
     Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Etes-vous sûr de vouloir éffectuer cette action ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) annuler.getScene().getWindow();
            stage.close();
        }
    }
//------------------------------------------------------------------------------
    public void modifierData(Service s, int id) throws SQLException {
        id_ss = id;
        n = s.getNom_service();
        nom.setText(n);
        a = new Service(n);
        System.out.println(id_ss);
    }
//------------------------------------------------------------------------------
    @FXML
    private void modifierService(ActionEvent event) throws SQLException, IOException {
//        String name = nom.getText();
//        a = new Service(name);
//        if(!name.isEmpty())
//        {
//        ss.modifier(id_ss, a);
//        lst.refresh();
//        //popup lors de la modification
//        Alert b = new Alert(Alert.AlertType.INFORMATION);
//            b.setTitle("Bravo");
//            b.setContentText(" Votre service est modifié");
//            b.setHeaderText("Félicitation !");
//            b.showAndWait();
//        Stage stage = (Stage) enregistrer.getScene().getWindow();
//        stage.close();
//        }
//        else{
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setTitle("Erreur");
//            a.setContentText("Merci de remplir le champ nom de service");
//            a.setHeaderText("Attention !");
//            a.showAndWait();
//        }
        
        String n;
        n = nom.getText();
        ArrayList<Service> al=(ArrayList<Service>) ss.getAll();
        boolean x = false;
        if (!n.isEmpty()) {
            for (Service v : al) {
                if (v.getNom_service().toUpperCase().compareTo(n.toUpperCase()) == 0) {
                    x = true;
                }
            }
            if (x) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("ce service existe déja!");
                a.setHeaderText("Attention !");
                a.showAndWait();
            } else {
                 Service se= new Service(n);
                ss.modifier(id_ss, se);
                lst.refresh();
                Stage stage = (Stage) enregistrer.getScene().getWindow();
                stage.close();
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir le nom du service");
            a.setHeaderText("Attention !");
            a.showAndWait();
        }
    }
    
    public void getController(ListerServiceController l){
        lst=l;
    }
}
