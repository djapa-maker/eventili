
package gui.backOffice.Service.ListerAvis.creerAvis;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Avis;
import entities.Personne;
import entities.ServiceReservation;
import entities.SousServices;
import gui.sigleton.singleton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.AvisService;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CreerAvisController implements Initializable {
    @FXML
    private TextField comment;
    @FXML
    private Rating rating;
    @FXML
    private FontAwesomeIconView arrow;
singleton data= singleton.getInstance();
       Personne p1=data.getUser();
    
 AvisService as = new AvisService();
 
    private SousServices ser;
    private boolean isReserved = false;
    private SousServices s = new SousServices();
    private List<ServiceReservation> LSR;
    ArrayList<SousServices> List = new ArrayList<SousServices>();
    ArrayList<Avis> avis = new ArrayList<Avis>();
    SousServices s3;
    PersonneService pr=new PersonneService();
    Personne p;
    int in;
    private float r;
    private String c;
    private Avis a;
    private int id;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void send(MouseEvent event) {
    if (!comment.getText().isEmpty()
                    &&(rating.getRating()!=0.0)
                    ) {
            c = comment.getText();
            r=(float) rating.getRating();
            
            a=new Avis(c,r,ser,p1);
            as.ajouter(a);
            //popup lors de la creation
            Alert alt = new Alert(Alert.AlertType.INFORMATION);
            alt.setTitle("Bravo");
            alt.setContentText(" Votre avis est envoyé");
            alt.setHeaderText("Félicitation !");
            Optional<ButtonType> action = alt.showAndWait();
//            if (action.get() == ButtonType.OK) {
//                Stage stage = (Stage) ButtonType.OK.getScene().getWindow();
//                stage.close();
//            }
            //lst.refresh();
            Stage stage = (Stage) arrow.getScene().getWindow();
            stage.close(); 
        } else {
            comment.setStyle("-fx-border-color: red;");
        }
    }
    
}
