package gui.backOffice.Service.ListerSousService.detailItem;

import entities.Event;
import entities.EventCateg;
import entities.Personne;
import entities.Service;
import entities.ServiceReservation;
import entities.SousServices;
import entities.imageSS;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.EventCategService;
import services.ServiceReservationService;
import services.SousServiceService;
import services.imageSService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class DetailItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label nomPres;
    private Button close;
    @FXML
    private ImageView imagess;
    @FXML
    private Text descss;
    @FXML
    private Label nomss;
    @FXML
    private Text price;
    @FXML
    private Label note;
    @FXML
    private Button resbtn;
    @FXML
    private GridPane grid;
    @FXML
    private Button before;
    @FXML
    private Button after;
    private ArrayList<SousServices> ListeS = new ArrayList<>();
    private ServiceReservationService rs = new ServiceReservationService();
    private ServiceReservation SR;
    private ArrayList<Integer> sousServicesIds = new ArrayList<>();
    private SousServiceService ss = new SousServiceService();
    private EventCategService ev = new EventCategService();
    private Service s1 = new Service();
    private Personne p = new Personne();
    private Event e;
    private boolean isReserved = false;
    private SousServices s = new SousServices();
    private List<ServiceReservation> LSR;
    private ArrayList<SousServices> List = new ArrayList<SousServices>();
    private ArrayList<EventCateg> event = new ArrayList<>();
    private SousServices s3;
    private int in;
    private String i;
    private ArrayList<imageSS> imaage = new ArrayList<>();
    private imageSService im = new imageSService();
    private int next = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
//------------------------------------------------------------------------------

    @FXML
    private void end(ActionEvent event) {
        Stage stage = (Stage) resbtn.getScene().getWindow();
        stage.close();
    }
//------------------------------------------------------------------------------    

    public void setData(SousServices s) throws FileNotFoundException {
        descss.setText(s.getDescription_serv());
        nomss.setText(s.getNom_serv());
        price.setText(s.getPrix_serv() + " DT");
        imaage = (ArrayList<imageSS>) im.findImageByIdSS(s.getId_sousServ());
        i = imaage.get(0).getImg();
//        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
        //FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
           Image img = new Image("http://localhost/img/"+i);
       
        imagess.setImage(img);
        note.setText(Float.toString(s.getNote()));
        nomPres.setText(s.getPers().getNom_pers() + " " + s.getPers().getPrenom_pers());
        event = ss.findServiceById(s.getId_sousServ()).getEc();
        int row = 0;
        int column = 1;
        for (EventCateg item : event) {
            Label l = new Label(item.getType() + ",");
            l.setStyle("-fx-font-size: 18px;");
            grid.add(l, column, 0);
            column++;
        }
    }

    @FXML
    private void before(ActionEvent event) throws FileNotFoundException {

        if (next > 0) {
            next = next - 1;
            i = imaage.get(next).getImg();
//            FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + i);
              Image img1 = new Image("http://localhost/img/"+i);
            imagess.setImage(img1);
        } else {
            if (next == 0) {
                next = imaage.size() - 1;
                i = imaage.get(next).getImg();
//                FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
                   Image img1 = new Image("http://localhost/img/"+i);
                imagess.setImage(img1);
            }
        }

    }

    @FXML
    private void after(ActionEvent event) throws FileNotFoundException {
        next++;
        if (next < imaage.size()) {
            i = imaage.get(next).getImg();
//            FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
               Image img1 = new Image("http://localhost/img/"+i);
               imagess.setImage(img1);
        } else {
            if (next == imaage.size()) {
                next = 0;
                i = imaage.get(next).getImg();
               // FileInputStream inputstream = new FileInputStream("C:/xamp2/htdocs/img/" + i);
                Image img = new Image("http://localhost/img/"+i);
                imagess.setImage(img);
            }
        }
    }

}
