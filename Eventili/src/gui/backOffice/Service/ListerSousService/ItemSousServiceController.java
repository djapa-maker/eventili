package gui.backOffice.Service.ListerSousService;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.EventCateg;
import entities.SousServices;
import entities.imageSS;
import gui.backOffice.Service.ListerSousService.detailItem.DetailItemController;
import gui.backOffice.Service.modifierSousService.ModifierSousServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.PersonneService;
import services.SousServiceService;
import services.imageSService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemSousServiceController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Text note;
    @FXML
    private Text prix;
    @FXML
    private Text desc;
    @FXML
    private Text nom;
    @FXML
    private Text pers;
    @FXML
    private Button supp;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif;
    @FXML
    private HBox hboxmodif;
    @FXML
    private GridPane gridCateg;
    @FXML
    private ScrollPane scrollbox;
    @FXML
    private Text nomSousService;
    private ListerSousServiceController lst=new ListerSousServiceController();
    private SousServices sev = new SousServices();
    private SousServiceService e = new SousServiceService();
    private PersonneService se = new PersonneService();
    private ArrayList<EventCateg> eventCat = new ArrayList<>();
    private ArrayList<imageSS> imaage = new ArrayList<>();
//    private imageSService im= new imageSService();
    private float not;
//    private String i;
    private String n;
    private String p;
    private String d;
    private String per;
    private String cat;
    private int id_ss;
    private String ssName;
//------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL, "25px"));
        //ellimination des carreaux en bas à droite
        scrollbox.setFitToHeight(true);
        scrollbox.setFitToWidth(true);
    }
//------------------------------------------------------------------------------

    public void SetData(SousServices s) throws SQLException, FileNotFoundException {
        String D = "";
        id_ss = s.getId_sousServ();
        //imaage=(ArrayList<imageSS>) im.findImageByIdSS(id_ss);
       // i=imaage.get(0).getImg();
//        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
//        FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
////        Image img = new Image(inputstream);
//           Image img = new Image("http://localhost/img/"+i);
//        icon.setImage(img);
        //System.out.println(i);
        ssName = s.getNom_serv();
        d = s.getDescription_serv();
        not = s.getNote();
        per = se.findById(s.getPers().getId_pers()).getPrenom_pers();
        p = Float.toString(s.getPrix_serv());
        n = s.getS().getNom_service();
        float p1 = s.getPrix_serv();
        eventCat = e.findServiceById(id_ss).getEc();
        gridCateg.setVgap(10); // Set the vertical gap between rows
        // Set the height of each row to a fixed value
        for (int i = 0; i < eventCat.size(); i++) {
            RowConstraints row = new RowConstraints(10); // Set the row height to 30 pixels
            gridCateg.getRowConstraints().add(row);
        }
        int row = 0;
        for (EventCateg item : eventCat) {
            Label l = new Label(item.getType());
//            System.out.println("fgvhbjnk,l" + item.getType());
            l.setStyle("-fx-font-size: 18px;");
            gridCateg.add(l, 0, row);
            row++;
        }
        //System.out.println(eventCat);
        if (per.length() > 6) {
            D = per.substring(0, Math.min(per.length(), 6));
            pers.setText(D + "...");
        } else {
            pers.setText(per);
        }
        if (n.length() > 17) {
            D = n.substring(0, Math.min(n.length(), 17));
            nom.setText(D + "...");
        } else {
            nom.setText(n);
        }
        if (ssName.length() > 11) {
            D = ssName.substring(0, Math.min(ssName.length(), 11));
            nomSousService.setText(D + "...");
        } else {
            nomSousService.setText(ssName);
        }
        prix.setText(p);
        if (d.length() > 25) {
            D = d.substring(0, Math.min(d.length(), 25));
            desc.setText(D + "...");
        } else {
            desc.setText(d);
        }
        note.setText(Float.toString(not));
        System.out.println(s.getPers());
        System.out.println(s.getS());
        sev = new SousServices(id_ss, p1, ssName, d,  not, s.getPers(), s.getS(), eventCat);
    }
//------------------------------------------------------------------------------

    @FXML
    private void supprimer(ActionEvent event) throws SQLException, IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Voulez vous vraiment supprimer ce sous service ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            e.supprimerById(id_ss);
            lst.refresh();
        }
    }
//------------------------------------------------------------------------------
    @FXML
    private void modifier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../modifierSousService/modifierSousService.fxml"));
        Parent loader = addLoader.load();
        ModifierSousServiceController modifController = addLoader.getController();
        modifController.modifierData(sev, id_ss);
        modifController.Controller(lst);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
//------------------------------------------------------------------------------    
    public void dataController(ListerSousServiceController l){
        lst=l;
    }
//------------------------------------------------------------------------------    
    @FXML
    private void show(MouseEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("detailItem/DetailItem.fxml"));
        Parent loader = addLoader.load();
        DetailItemController Controller = addLoader.getController();
        Controller.setData(sev);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
}
