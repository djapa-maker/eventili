package gui.frontOffice.AvisClient;

import entities.Avis;
import entities.Personne;
import entities.SousServices;
import gui.backOffice.Service.ListerService.ListerServiceController;
import gui.backOffice.Service.modifierSousService.ModifierSousServiceController;
import gui.frontOffice.AvisClient.mdfAV.MdfAVController;
import gui.sigleton.singleton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.AvisService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AvisClientController implements Initializable {
singleton data= singleton.getInstance();
       Personne p1=data.getUser();
    @FXML
    private Pane pane;
    @FXML
    private Text sous_service;
    @FXML
    private Label comment;
    @FXML
    private Label note;
    @FXML
    private ImageView imgSS;
    @FXML
    private Label nomSS;
    @FXML
    private Button modif;

    private Label nomPers;
    private Avis av = new Avis();
    private String c;
    private float r;
    private Personne p;
    private SousServices s;
    private String i;
    private String n;

    AvisService as = new AvisService();
    ListerAvisClientController lst = new ListerAvisClientController();
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void SetData(Avis a) throws SQLException, FileNotFoundException {
        id = a.getId_av();
        System.out.println(id);
        c = a.getComment();
        r = (float) a.getRating();
        s = a.getS();
        i = a.getS().getIcon();
        n = a.getS().getNom_serv();
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+i); 
        Image img1 = new Image(inputstream); 
        imgSS.setImage(img1);
        nomSS.setText(n);
        nomSS.setTextFill(Color.DARKSLATEBLUE);
        note.setText(r + "/5");
        note.setTextFill(Color.DARKSLATEBLUE);
        comment.setText(c);
        comment.setTextFill(Color.DARKSLATEBLUE);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        av = new Avis(id, c, r, s, p1, timestamp);
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException, SQLException {
        System.out.println("hhhhhhhhkkkkkkkkkkkkkkkk");
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("mdfAV/mdfAV.fxml"));
        Parent loader = addLoader.load();
        MdfAVController modifController = addLoader.getController();
        modifController.modifierData(av, id);
        modifController.getController(lst);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
    public void getController(ListerAvisClientController l){
        lst=l;
    }

}
