/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.users;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Personne;
import entities.imagepers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ItemCardController implements Initializable {
UserController us=new UserController();
    @FXML
    private Pane pane1;
    @FXML
    private Button supp1;
    @FXML
    private Button modif1;
    @FXML
    private ImageView icon;
    @FXML
    private Text adresse;
    @FXML
    private Text num;
    @FXML
    private Text prenom;
    @FXML
    private Text nom;
    @FXML
    private HBox hboxsupp;
    @FXML
    private HBox hboxmodif;
    @FXML
    private Text email;
   private ArrayList<imagepers> listI;
    @FXML
    private Text rib;
    @FXML
    private Text role;
    Personne sev = new Personne();
    imagepers i=new imagepers();
    PersonneService e = new PersonneService();
    private int no;
    private String pnom,pprenom,pnum,pemail,padresse,pimage,prib,prole;
    private int id_pers;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          listI = (ArrayList<imagepers>) e.getAllI();
hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                                "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                                "25px"));
        
        
    }
    public void getController(UserController u){
        us=u;
    }
public void SetData(Personne s,imagepers i) throws SQLException, FileNotFoundException {
        //affichage des services
        id_pers = s.getId_pers();
        pnom=s.getNom_pers();
        pprenom=s.getPrenom_pers();
        pnum=s.getNum_tel();
        pemail=s.getEmail();
        padresse=s.getAdresse();
        pimage=i.getLast();
        prib=s.getRib();
        prole=s.getRole();
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+pimage); 
        Image image = new Image(inputstream); 
        icon.setImage(image);
        
        nom.setText(pnom);
        prenom.setText(pprenom);
        num.setText(pnum);
        email.setText(pemail);
        adresse.setText(padresse);
        rib.setText(prib);
        role.setText(prole);
        sev=s;
        
    }    

    @FXML
    private void supprimer(MouseEvent event) throws IOException, SQLException {
    
    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Etes-vous sur de vouloir supprimer cette personne ");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            e.supprimer(sev);
            us.Refresh();

        }
    }

    @FXML
    private void modifier(MouseEvent event) throws IOException, SQLException {
         FXMLLoader addLoader = new FXMLLoader(getClass().getResource("modifier.fxml"));
        Parent loader = addLoader.load();
        ModifierController modifController = addLoader.getController();
        i=e.findByIdI(id_pers);
       modifController.modifierData(sev, id_pers,i);
       modifController.getController(us);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
    
}
