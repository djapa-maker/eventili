/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.AvisClient.mdfAV;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Avis;
import entities.Personne;
import entities.Service;
import entities.ServiceReservation;
import entities.SousServices;
import gui.backOffice.Service.ListerService.ListerServiceController;
import gui.frontOffice.AvisClient.ListerAvisClientController;
import gui.sigleton.singleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class MdfAVController implements Initializable {

    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    @FXML
    private TextField comment;
    @FXML
    private Rating rating;
    @FXML
    private FontAwesomeIconView arrow;
    @FXML
    private Button annuler;
    AvisService as = new AvisService();
    ListerAvisClientController lst = new ListerAvisClientController();

    private SousServices ser;
    private boolean isReserved = false;
    private SousServices s = new SousServices();
    private List<ServiceReservation> LSR;
    ArrayList<SousServices> List = new ArrayList<SousServices>();
    ArrayList<Avis> avis = new ArrayList<Avis>();
    SousServices s3;
    PersonneService pr = new PersonneService();
    Personne p;
    int in;
    private float r;
    private String c;
    private Avis b;
    private int id;
    int id_av;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void modifierData(Avis a, int id) throws SQLException {
        id_av = id;
        c = a.getComment();
        r = a.getRating();
        s = a.getS();
        comment.setText(c);
        rating.setRating(r);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        b = new Avis(id_av, c, r, s, p1, timestamp);
        System.out.println(id_av);
        System.out.println(b);
    }

    @FXML
    private void send(MouseEvent event) throws SQLException, IOException {
        System.out.println("sdfghjlkqwsxdcfvgbhnj,k;sxdgcfhvb");
        String com = comment.getText();
        float rat = (float) rating.getRating();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Avis g = new Avis(id_av, com, rat, s, p1, timestamp);
        if (!com.isEmpty() && !(rat == 0.0)) {
            as.modifierAvis(id_av, g);
            lst.refresh();
            //popup lors de la modification
            Alert b = new Alert(Alert.AlertType.INFORMATION);
            b.setTitle("Bravo");
            b.setContentText(" Votre avis est modifié");
            b.setHeaderText("Félicitation !");
            b.showAndWait();
            Stage stage = (Stage) arrow.getScene().getWindow();
            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Merci de remplir le champ commentaire et de noter ce sous service");
            a.setHeaderText("Attention !");
            a.showAndWait();
        }
    }

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

    public void getController(ListerAvisClientController l) {
        lst = l;
    }
}
