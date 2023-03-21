package gui.frontOffice.client.DetailSousService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Avis;
import entities.Event;
import entities.Personne;
import entities.Service;
import entities.ServiceReservation;
import entities.SousServices;
import entities.imageSS;
import gui.backOffice.Service.ListerSousService.ItemSousServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.AvisService;
import services.PersonneService;
import services.ServiceReservationService;
import services.SousServiceService;
import services.imageSService;

/**
 * FXML Controller class
 *
 * @author chaima
 */
public class DetailSousServiceController implements Initializable {

    @FXML
    private Label nomPres;
    @FXML
    private Button resbtn;
    @FXML
    private ImageView imagess;
    @FXML
    private Text descss;
    @FXML
    private Label nomss;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Text price;
    @FXML
    private GridPane grid;
    ArrayList<SousServices> ListeS = new ArrayList<>();
    ServiceReservationService rs = new ServiceReservationService();
    ServiceReservation SR;
    ArrayList<Integer> sousServicesIds = new ArrayList<>();
    SousServiceService ss = new SousServiceService();
    Service s1 = new Service();
    Personne p = new Personne();
    PersonneService pr = new PersonneService();
    Event e;
    AvisService as = new AvisService();
    private boolean isReserved = false;
    private SousServices s = new SousServices();
    private List<ServiceReservation> LSR;
    ArrayList<SousServices> List = new ArrayList<SousServices>();
    ArrayList<Avis> avis = new ArrayList<Avis>();
    private ArrayList<imageSS> imaage = new ArrayList<>();
    private imageSService im = new imageSService();

    SousServices s3;
    int in;
    private float r;
    private String c;
    private Avis a;
    private int id;
    @FXML
    private ScrollPane spane;
    private float vr = 0;
    @FXML
    private Label btnote;
    @FXML
    private Button before;
    @FXML
    private Button after;
    private int next = 0;
    private String i;
//------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //rs.ajouterRes(r, sousServicesIds);
        //Resultat();
        AnchorPane.setBottomAnchor(resbtn, 20.0);
        AnchorPane.setRightAnchor(resbtn, 20.0);
        AnchorPane.setTopAnchor(resbtn, null);
        AnchorPane.setLeftAnchor(resbtn, null);
        resbtn.setPrefWidth(Button.USE_COMPUTED_SIZE);
        resbtn.setPrefHeight(Button.USE_COMPUTED_SIZE);
//        avis = (ArrayList<Avis>) as.findByIdSousService(s3.getId_sousServ());
        System.out.println();
//        try {
//            setAvis(id);
//        } catch (IOException ex) {
//            Logger.getLogger(DetailSousServiceController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DetailSousServiceController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void setAvis(int id) throws IOException, SQLException {
        int column = 0;
        int row = 1;
        avis = (ArrayList<Avis>) as.findByIdSousService(id);
        for (Avis v : avis) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemAvis.fxml"));
            Pane pane = fxmlLoader.load();
            ItemAvisController itemController = fxmlLoader.getController();
            itemController.SetData(v);
            //itemController.dataController(this);
            if (column == 1) {
                column = 0;
                ++row;
            }
            grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }

//------------------------------------------------------------------------------
    public void setDataEvent(Event ev) {
        this.e = ev;
    }
//------------------------------------------------------------------------------    

    @FXML
    public void reserver(ActionEvent event) {
        isReserved = !isReserved;
        if (isReserved) {
            //addtodb
            LSR = rs.getAll();
            boolean exist = false;
            for (ServiceReservation r : LSR) {
                if (r.getE().getId_ev() != e.getId_ev()) {
                    exist = false;
                } else {
                    exist = true;
                }
            }
            if (!exist) {
                //add
                System.out.println("Adding to DB");
                ArrayList<Integer> LIds = new ArrayList<>();
                ArrayList<SousServices> l = new ArrayList<>();
                LIds.add(s3.getId_sousServ());
                SR = new ServiceReservation(e, false);
                rs.ajouterRes(SR, LIds);
            } else {
                //update
                System.out.println("Updating DB");
                ArrayList<Integer> LIds = new ArrayList<>();
                SR = rs.findByIdEvent(e.getId_ev());
                ArrayList<SousServices> Lss = SR.getList();
                for (SousServices s : Lss) {
                    LIds.add(s.getId_sousServ());
                }
                LIds.add(s3.getId_sousServ());
                rs.modifierReservationService(SR.getId_res(), SR, LIds);
            }
            resbtn.setText("Annuler réservation");
        } else {
            System.out.println("Deleting from DB");
            ArrayList<Integer> LIds = new ArrayList<>();
            SR = rs.findByIdEvent(e.getId_ev());
            ArrayList<SousServices> Lss = SR.getList();
            for (SousServices s : Lss) {
                LIds.add(s.getId_sousServ());
            }
            for (Integer i : LIds) {
                if (i == s3.getId_sousServ()) {
                    in = LIds.indexOf(i);
                }
            }
            if (LIds.size() == 1) {
                rs.supprimer(SR);
            } else {
            LIds.remove(in);
            rs.modifierReservationService(SR.getId_res(), SR, LIds);
            }
            resbtn.setText("Réserver");
        }
        // Resize the button to fit the new label text
        AnchorPane.setBottomAnchor(resbtn, 20.0);
        AnchorPane.setRightAnchor(resbtn, 20.0);
        AnchorPane.setTopAnchor(resbtn, null);
        AnchorPane.setLeftAnchor(resbtn, null);
        //close
        Stage stage = (Stage) resbtn.getScene().getWindow();
        stage.close();
    }
//------------------------------------------------------------------------------    

    public void setData(SousServices s) throws FileNotFoundException {
        s3 = s;
        nomss.setText("Réserver le service: " + s.getNom_serv());
        descss.setText(s.getDescription_serv());
        imaage = (ArrayList<imageSS>) im.findImageByIdSS(s.getId_sousServ());
        i = imaage.get(0).getImg();
        // FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
        Image img1 = new Image("http://localhost/img/" + i);
        imagess.setImage(img1);
        price.setText(s.getPrix_serv() + " DT");
        Rectangle clip = new Rectangle(imagess.getFitWidth(), imagess.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        clip.setStroke(Color.BLACK);
        imagess.setClip(clip);
        nomPres.setText(s.getPers().getNom_pers() + " " + s.getPers().getPrenom_pers());
        LSR = rs.getAll();
        for (ServiceReservation r : LSR) {
            if (r.getE().getId_ev() == e.getId_ev()) {
                List = r.getList();
                if (!List.contains(s3)) {
                    resbtn.setText("Réserver");
                    isReserved = false;
                } else {
                    resbtn.setText("Annuler réservation");
                    isReserved = true;
                }
            }
        }
        Resultat();
    }

    public Float Resultat() {
        ArrayList<Avis> f = new ArrayList<>();
        f = as.findByIdSousService(s3.getId_sousServ());
        for (Avis q : f) {
            DecimalFormat df = new DecimalFormat("#.##"); // Créer un objet DecimalFormat pour afficher 2 décimales
            vr += q.getRating() / f.size();
            String formatted = df.format(vr);
            System.out.println(formatted);
            btnote.setText(String.valueOf(formatted) + "/5");
        }
        return vr;
    }

    @FXML
    private void before(ActionEvent event) throws FileNotFoundException {

        if (next > 0) {
            next = next - 1;
            i = imaage.get(next).getImg();
            //FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
            Image img1 = new Image("http://localhost/img/" + i);
            imagess.setImage(img1);
        } else {
            if (next == 0) {
                next = imaage.size() - 1;
                i = imaage.get(next).getImg();
                // FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
                Image img1 = new Image("http://localhost/img/" + i);
                imagess.setImage(img1);
            }
        }

    }

    @FXML
    private void after(ActionEvent event) throws FileNotFoundException {
        next++;
        if (next < imaage.size()) {
            i = imaage.get(next).getImg();
            Image img1 = new Image("http://localhost/img/" + i);
            imagess.setImage(img1);
        } else {
            if (next == imaage.size()) {
                next = 0;
                i = imaage.get(next).getImg();
                Image img1 = new Image("http://localhost/img/" + i);
                imagess.setImage(img1);
            }
        }
    }

}
