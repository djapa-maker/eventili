package gui.frontOffice.servicePrestataire.modifierSousServicePrestataire;

import entities.EventCateg;
import entities.Personne;
import entities.SousServices;
import entities.imageSS;
import gui.frontOffice.servicePrestataire.ListerServicePrestataireController;
import gui.sigleton.singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EventCategService;
import services.ServiceService;
import services.SousServiceService;
import services.imageSService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ModifierSSPrestataireController implements Initializable {
    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    @FXML
    private AnchorPane screen5;
    @FXML
    private Text text1;
    @FXML
    private Text text3;
    @FXML
    private TextArea description;
    @FXML
    private Button annuler;
    @FXML
    private Button suivant;
    @FXML
    private Text txtprice;
    @FXML
    private TextField price;
    @FXML
    private Text txtprice1;
    @FXML
    private Text txtprice2;
    @FXML
    private TextField nomSousService;
    @FXML
    private Text text31;
    @FXML
    private Button importer;
    @FXML
    private GridPane grid;
    @FXML
    private ListView list;
    private ListerServicePrestataireController lst=new ListerServicePrestataireController();
    private SousServiceService ss = new SousServiceService();
    private ServiceService service = new ServiceService();
    private imageSService is = new imageSService();
    private EventCategService ec = new EventCategService();
    private ArrayList<EventCateg> le = new ArrayList<EventCateg>();
    private ArrayList<SousServices> lp = new ArrayList<SousServices>();
    private ArrayList<imageSS> imgl = new ArrayList<>();
    private ArrayList<Integer> L = new ArrayList<>();
    private List<File> selectedFile = null;
    private String i;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int id_ss;
    private String d;
    private String p;
    private String nom;
    private String n;
    private Float note;
    private String path;
    private String desc;
    private String priText;
    private Float prix;
    private imageSS im;    
//------------------------------------------------------------------------------    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listerEventCateg();
    }    
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------    
    public void listerEventCateg() {
        le = (ArrayList<EventCateg>) ec.getAll();
        grid.setVgap(20);
        for (int i = 0; i < le.size(); i++) {
            RowConstraints row = new RowConstraints(20); // Set the row height to 30 pixels
            grid.getRowConstraints().add(row);
        }
        int row = 0;
        for (EventCateg item : le) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(false);
            GridPane.setMargin(checkBox, new Insets(10));
            ArrayList<EventCateg> field = ss.findServiceById(id_ss).getEc();
            System.out.println(field);
            System.out.println(id_ss);
            for (EventCateg i : field) {
                if (i.getType().equals(item.getType())) {
                    checkBox.setSelected(true);
                }
            }
            checkBox.getStyleClass().add("my-checkbox");
            Label l = new Label(item.getType());
            l.setStyle("-fx-font-size: 18px;");
            grid.add(l, 0, row);
            grid.add(checkBox, 1, row);
            row++;
        }
    }
//------------------------------------------------------------------------------    

    @FXML
    private void modifierSousService(ActionEvent event) throws SQLException, IOException {
        desc = description.getText();
        priText = price.getText();
        nom = nomSousService.getText();
        prix = null;
        for (Node node : grid.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    //+1 parceque l'indice n'arrive pas à lire le dernier element selected
                    int rowCheck = GridPane.getRowIndex(checkBox) + 1;
                    //*2 : grid tekhedh label w checkbox so row metkawna men 2 components w el -2 bch narj3ou lel component 1 elli howa label
                    Label label = (Label) grid.getChildren().get(rowCheck * 2 - 2);
                    System.out.println("Selected: " + label.getText());
                    L.add(ec.findByName(label.getText()).getId());
                }
            }
        }
        if ((!priText.isEmpty())
                && (!desc.isEmpty())
                && (!nom.isEmpty())
                && (!path.isEmpty())
                && (!L.isEmpty())) {
            if (priText.matches("[+-]?([0-9]*[.])?[0-9]+")) {
                prix = Float.parseFloat(price.getText());
                note = ss.findServiceById(id_ss).getNote();
                SousServices sous = new SousServices(prix, nom, desc, path, note);
                lp = (ArrayList<SousServices>) ss.getAllByPrestataire(p1.getId_pers());
                for (SousServices p2 : lp) {
                    if (p2.getDescription_serv().equals(sous.getDescription_serv())
                            && p2.getIcon().equals(sous.getIcon())
                            && p2.getNom_serv().equals(sous.getNom_serv())
                            && p2.getNote() == sous.getNote()
                            && p2.getPrix_serv() == sous.getPrix_serv()) {
                        im = new imageSS();
                        for (imageSS s : imgl) {
                            is.supprimer(s);
                        }
                        for (int i1 = 0; i1 < selectedFile.size(); i1++) {
                            String i = selectedFile.get(i1).getName();
                            System.out.println("check modif"+i);
                            im.setImg(i);
                            im.setS(p2);
                            is.ajouter(im);
                        }

                    } else {
                        System.out.println("noooooooooooooooooo");
                    }
                }
                lst.refresh();
                //popup lors de la modification
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Bravo");
                b.setContentText(" Votre sous service est modifié");
                b.setHeaderText("Félicitation !");
                b.showAndWait();
                Stage st = (Stage) suivant.getScene().getWindow();
                st.close();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Veuillez insérer un nombre dans le champs prix ");
                a.setHeaderText("Attention !");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Verifier que tout les champs sont remplis");
            a.setHeaderText("Attention !");
            a.showAndWait();
        }
    }
//------------------------------------------------------------------------------     

    @FXML
    private void importIcone(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:/xampp/htdocs/img"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png"));
        selectedFile = fc.showOpenMultipleDialog(null);
        if (selectedFile != null) {
            list.getItems().clear();
            for (int i = 0; i < selectedFile.size(); i++) {
                list.getItems().add(selectedFile.get(i).getName());
            }
        } else {
            System.out.println("Not valid file");
        }
    }
//------------------------------------------------------------------------------    
     public void modifierData(SousServices s, int id) throws SQLException, FileNotFoundException {
        id_ss = id;
        path = ss.findServiceById(id).getIcon();
        n = ss.findServiceById(id).getNom_serv();
        d = ss.findServiceById(id).getDescription_serv();
        p = Float.toString(ss.findServiceById(id).getPrix_serv());
        imgl = (ArrayList<imageSS>) is.findImageByIdSS(id_ss);
        for (imageSS youhu : imgl) {
            list.getItems().add(youhu.getImg());
        }
           Image img1 = new Image("http://localhost/img/"+i);
        nomSousService.setText(n);
        price.setText(p);
        description.setText(d);
        listerEventCateg();
    }
       public void Controller(ListerServicePrestataireController l){
        lst=l;
    }    
}
