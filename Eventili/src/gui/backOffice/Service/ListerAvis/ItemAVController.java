package gui.backOffice.Service.ListerAvis;

import entities.Avis;
import entities.EventCateg;
import entities.Personne;
import entities.SousServices;
import gui.backOffice.Service.ListerSousService.ListerSousServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import services.AvisService;
import services.PersonneService;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemAVController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ImageView imgPers;
    @FXML
    private Text note;
    @FXML
    private Text comment;
    @FXML
    private Text nomSS;
    @FXML
    private Button supp;
    @FXML
    private Text nomPers;
    @FXML
    private Text date;

ListerAVController lst=new ListerAVController();
    AvisService av = new AvisService();
    SousServiceService ss = new SousServiceService();
    PersonneService ps = new PersonneService();
    Avis a = new Avis();
    // ArrayList<EventCateg> eventCat = new ArrayList<>();
    private float not;
    private String i;
    private String p;
    private String c;
    private Timestamp d;
    private Float r;
    private String per;
    private String sous;
    private int id_av;
    private String np;

    //------------------------------------------------------------------------------
    public void SetData(Avis aa) throws SQLException, FileNotFoundException {
        System.out.println("avissssssss"+aa);
        String D = "";
        id_av = aa.getId_av();
        
        i = ps.findByIdI(aa.getP().getId_pers()).getLast();
        np = aa.getP().getPrenom_pers();
        c = aa.getComment();
        sous = aa.getS().getNom_serv();
        not = aa.getRating();
        d=aa.getD();
        d = new Timestamp(System.currentTimeMillis());
        LocalDate dat = d.toLocalDateTime().toLocalDate();
        //fonction de calcule
      
        if (np.length() > 17) {
            D = np.substring(0, Math.min(np.length(), 17));
            nomPers.setText(D + "...");
        } else {
            nomPers.setText(np);
        }
        if (c.length() > 30) {
            D = c.substring(0, Math.min(c.length(), 30));
            comment.setText(D + "...");
        } else {
            comment.setText(c);
        }
        if (sous.length() > 15) {
            D = sous.substring(0, Math.min(sous.length(), 15));
            nomSS.setText(D + "...");
        } else {
            nomSS.setText(sous);
        }
        note.setText(Float.toString(not));
        date.setText(dat.toString());
         FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
        Image img = new Image(inputstream);
        imgPers.setImage(img);
       // a = new Avis(c, not, aa.getS(), aa.getP(), d);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException, IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Voulez vous vraiment supprimer cet avis ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
          av.supprimerById(id_av);
          lst.refresh();
        }
    }

public void dataController(ListerAVController l){
    lst=l;
}
 
}
