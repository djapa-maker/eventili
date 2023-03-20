/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.servicePrestataire;

import entities.Avis;
import entities.Event;
import entities.EventCateg;
import entities.Personne;
import entities.SousServices;
import entities.imageSS;
import gui.backOffice.Service.ListerSousService.ListerSousServiceController;
import gui.backOffice.Service.modifierSousService.ModifierSousServiceController;
import gui.frontOffice.client.DetailSousService.DetailSousServiceController;
import gui.frontOffice.servicePrestataire.modifierSousServicePrestataire.ModifierSSPrestataireController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.AvisService;
import services.PersonneService;
import services.SousServiceService;
import services.imageSService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemServicePrestataireController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Label prix;
    @FXML
    private Label note;
    @FXML
    private Label nomSS;
    @FXML
    private Text desc;
    @FXML
    private Button sup;
    @FXML
    private Button modif;

    ListerServicePrestataireController lst = new ListerServicePrestataireController();
    SousServices sev = new SousServices();
    SousServiceService e = new SousServiceService();
    PersonneService se = new PersonneService();
    ArrayList<EventCateg> eventCat = new ArrayList<>();
    AvisService as = new AvisService();
    private float vr = 0;
    private float not;
    private String i;
    private String n;
    private String p;
    private String d;
    private String per;
    //private String cat;
    private int id_ss;
    private String ssName;
    private SousServices s3;
    private int id_serv;
    private Event e1;
 private ArrayList<imageSS> imaage = new ArrayList<>();
private imageSService im= new imageSService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void SetData(SousServices s) throws SQLException, FileNotFoundException {
        s3 = s;
        String D = "";
        id_ss = s.getId_sousServ();
//        System.out.println(id_ss);
        imaage=(ArrayList<imageSS>) im.findImageByIdSS(id_ss);
        i=imaage.get(0).getImg();
        //FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
//        FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
        //Image img1 = new Image(inputstream);
        Image img1 = new Image("http://localhost/img/"+i);
        img.setImage(img1);
//        i = s.getIcon();
        img.setImage(img1);
        System.out.println(i);
        ssName = s.getNom_serv();
        d = s.getDescription_serv();
        not = s.getNote();
        //per = se.findById(s.getPers().getId_pers()).getNom_pers();
//        System.out.println(per);
        p = Float.toString(s.getPrix_serv());
        n = s.getS().getNom_service();
        Personne perss = s.getPers();
        float p1 = s.getPrix_serv();
        eventCat = e.findServiceById(id_ss).getEc();
        if (ssName.length() > 20) {
            D = ssName.substring(0, Math.min(ssName.length(), 20));
            nomSS.setText(D + "...");
        } else {
            nomSS.setText(ssName);
            //
        }
        nomSS.setText(ssName);
        nomSS.setTextFill(Color.DARKSLATEBLUE);
        prix.setText(p);
        prix.setTextFill(Color.DARKSLATEBLUE);
        desc.setText(d);
        note.setText(Float.toString(not));
        System.out.println(s.getPers());
        System.out.println(s.getS());
        s3 = new SousServices(id_ss, p1, ssName, d, i, not, s.getPers(), s.getS(), eventCat);
        Resultat();
    }

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

    @FXML
    private void modifier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("modifierSousServicePrestataire/modifierSSPrestataire.fxml"));
        Parent loader = addLoader.load();
        ModifierSSPrestataireController modifController = addLoader.getController();
        modifController.modifierData(sev, id_ss);
        modifController.Controller(lst);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }

    public void getController(ListerServicePrestataireController l) {
        lst = l;
    }

    public Float Resultat() {
        ArrayList<Avis> f = new ArrayList<>();
        f = as.findByIdSousService(s3.getId_sousServ());
        if (f.size() == 0) {
            note.setText("?" + "/5");
            note.setTextFill(Color.DARKSLATEBLUE);
        } else {
            for (Avis q : f) {
                DecimalFormat df = new DecimalFormat("#.#"); // Créer un objet DecimalFormat pour afficher 2 décimales
                vr += q.getRating() / f.size();
                String formatted = df.format(vr);
                System.out.println(formatted);
                note.setText(formatted + "/5");
                note.setTextFill(Color.DARKSLATEBLUE);
            }
        }
        return vr;
    }
    public void setDataEvent(Event ev) {
        this.e1 = ev;
    }
    //------------------------------------------------------------------------------

    public void handlePaneClick(MouseEvent event) {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../DetailSousService/DetailSousService.fxml"));
        Parent Root;
        try {
            Root = addLoader.load();
            DetailSousServiceController detail = addLoader.getController();
            detail.setDataEvent(e1);
            detail.setData(s3);
            try {
                detail.setAvis(s3.getId_sousServ());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            Stage Stage = new Stage();
            Stage.setScene(new Scene(Root));
            Stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
