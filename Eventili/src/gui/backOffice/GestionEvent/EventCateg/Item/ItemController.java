/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.EventCateg.Item;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.EventCateg;
import gui.backOffice.GestionEvent.EventCateg.CategController;
import gui.backOffice.GestionEvent.EventCateg.modifierEventCateg.ModifierEventCategController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.EventCategService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class ItemController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Button supp;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif;
    @FXML
    private HBox hboxmodif;
    @FXML
    private Text categ;

    private EventCateg cat = new EventCateg();
    private String n;
    private int id_c;
    EventCategService ecs = new EventCategService();
    CategController cc = new CategController();

    ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                        "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                        "25px"));
    }

    public void SetData(EventCateg ec) throws SQLException {
        System.out.println(ec);
        //affichage des categories
        id_c = ec.getId();
        n = ec.getType();
        categ.setText(n);
        cat = ecs.findById(id_c);
    }

    public void getController(CategController c) {
        cc = c;
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException, IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Est-vous sure de supprimer cette catégorie ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            ecs.supprimer(cat);
            cc.Refresh();
        }
    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/gui/backOffice/GestionEvent/EventCateg/modifierEventCateg/modifierEventCateg.fxml"));
        Parent loader = addLoader.load();
        ModifierEventCategController categController = addLoader.getController();
        categController.modifierData(cat, id_c);
        categController.getController(cc);
        Stage Stage = new Stage();
        Stage.initStyle(StageStyle.UNDECORATED);
        Stage.setScene(new Scene(loader));
        Stage.showAndWait();
        
    }

}
