/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Event.Item;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Event;
import gui.backOffice.GestionEvent.Event.EventController;
import gui.backOffice.GestionEvent.Event.modifierEvent.ModifierEventController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class ItemevController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ImageView icon;
    @FXML
    private Button supp;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif;
    @FXML
    private HBox hboxmodif;
    @FXML
    private Text visibilite;
    @FXML
    private Text type;
    @FXML
    private Text hdebhfin;
    @FXML
    private Text Date;
    @FXML
    private Text categ;
    @FXML
    private Text nomEvent;
    @FXML
    private Text pers1;
    private Event e1;
    private int idev;
    EventService es = new EventService();
    EventController ec = new EventController();
    /**
     * Initializes the controller class.
     */
    DateTimeFormatter dateF = DateTimeFormatter.ofPattern("dd MMM YY");
    DateTimeFormatter timeF = DateTimeFormatter.ofPattern("HH:mm");

    public void setData(Event e) throws FileNotFoundException {
        String D = "";
//        Image image1 = new Image(getClass().getResourceAsStream(e.getImage())); 
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + es.findFirstImageByEvent(e).getImg());
        Image img1 = new Image(inputstream);
        icon.setImage(img1);
        e1 = e;
        idev = e.getId_ev();
        Rectangle clip = new Rectangle(icon.getFitWidth(), icon.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        clip.setStroke(Color.BLACK);
        icon.setClip(clip);

        if (e.getTitle().length() > 19) {
            D = e.getTitle().substring(0, Math.min(e.getTitle().length(), 19));
            nomEvent.setText(D + "...");
        } else {
            nomEvent.setText(e.getTitle());
        }
        Date.setText(e.getDate_debut().format(dateF));
        hdebhfin.setText(e.getDate_debut().format(timeF) + "-" + e.getDate_fin().format(timeF));
        categ.setText(e.getC().getType());
        pers1.setText(e.getPers().getNom_pers() + " " + e.getPers().getPrenom_pers());
        type.setText(e.getType());
        visibilite.setText(e.getVisibilite());

    }

    public void getController(EventController e) {
        ec = e;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                        "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                        "25px"));
    }

    @FXML
    private void supprimer(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer événement");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer cet événement?");
        ButtonType okButton = new ButtonType("Supprimer");
        ButtonType cancelButton = new ButtonType("Annuler");
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            es.supprimer(e1);
            ec.Refresh();
        } else {
            alert.close();
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../modifierEvent/modifierEvent.fxml"));
        Parent Root = addLoader.load();
        ModifierEventController modifC = addLoader.getController();
        modifC.modifierData(e1, idev);
        modifC.getController(ec);
        Stage Stage = new Stage();
        Stage.initStyle(StageStyle.UNDECORATED);
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();
        
    }

}
