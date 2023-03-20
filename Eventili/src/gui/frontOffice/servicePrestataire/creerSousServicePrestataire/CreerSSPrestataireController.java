package gui.frontOffice.servicePrestataire.creerSousServicePrestataire;

import entities.EventCateg;
import entities.Personne;
import entities.Service;
import entities.SousServices;
import entities.imageSS;
import gui.frontOffice.servicePrestataire.ListerServicePrestataireController;
import gui.sigleton.singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.ComboBox;
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
public class CreerSSPrestataireController implements Initializable {
    singleton data= singleton.getInstance();
    Personne p1=data.getUser();
    @FXML
    private AnchorPane screen5;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
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
    private ComboBox<String> mychoicebox;
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
    private ListerServicePrestataireController lst= new ListerServicePrestataireController();
    private SousServiceService ss = new SousServiceService();
    private ServiceService service = new ServiceService();
    private EventCategService ec = new EventCategService();
    private ArrayList<EventCateg> le = new ArrayList<EventCateg>();
    private String i;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int id_service;
    private String nom;
    private List<File> selectedFile = null;
    private imageSService is = new imageSService();
    private ArrayList<SousServices> lp = new ArrayList<SousServices>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listerService();
        listerEventCateg();
    }    
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
        @FXML
    private void addSousService(ActionEvent event) throws SQLException, IOException {
        //SousServices serv = ss.findByName(nom);
        CheckBox checkBox = new CheckBox();
        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> M = new ArrayList<>();
        String desc = description.getText();
        String n = nomSousService.getText();
        String priText = price.getText();
        Float prix = null;
        String path = selectedFile.get(0).getName();
        Personne p = new Personne();
        p.setId_pers(p1.getId_pers());//l'id de la parsonne connecter
        Service s1 = null;
        if (mychoicebox == null || mychoicebox.getValue() == null || mychoicebox.getValue().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez sélectionner un service");
            a.setHeaderText("Attention !");
            a.showAndWait();
            return;
        } else {
            s1 = service.findByNames(mychoicebox.getValue());
        }
        for (Node node : grid.getChildren()) {
            if (node instanceof CheckBox) {
                checkBox = (CheckBox) node;
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
                && (s1 != null)
                && (!desc.isEmpty())
                && (!n.isEmpty())
                && (!path.isEmpty())
                && (!L.isEmpty())) {
            if (priText.matches("[+-]?([0-9]*[.])?[0-9]+")) {
                prix = Float.parseFloat(priText);
                SousServices sous = new SousServices(prix, n, desc, path, 0, p, s1);
                ss.ajouterTry(sous, L);
                System.out.println("hjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                lp = (ArrayList<SousServices>) ss.getAllByPrestataire(p1.getId_pers());
                for (SousServices p2 : lp) {
                    System.out.println("hmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                    if (p2.getDescription_serv().equals(sous.getDescription_serv())
                            && p2.getIcon().equals(sous.getIcon())
                            && p2.getNom_serv().equals(sous.getNom_serv())
                            && p2.getNote() == sous.getNote()
                            //                            && p2.getPers().equals(sous.getPers())
                            && p2.getPrix_serv() == sous.getPrix_serv() //                            && p2.getEc().equals(sous.getEc())
                            //                            && p2.getS().equals(sous.getS())) {
                            ) {
                        System.out.println("hlllllllllllllllllllllllllll");
                        imageSS im = new imageSS();
                        for (int i1 = 0; i1 < selectedFile.size(); i1++) {
                            String i = selectedFile.get(i1).getName();
                            im.setImg(i);
                            im.setS(p2);
                            is.ajouter(im);
                        }

                    } else {
                        System.out.println("noooooooooooooooooo");
                    }
                }
                //popup lors de la création
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Bravo");
                b.setContentText(" Votre sous service est crée");
                b.setHeaderText("Félicitation !");
                b.showAndWait();
                lst.refresh();
                Stage stage = (Stage) suivant.getScene().getWindow();
                stage.close();
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
    public void listerService() {
        ServiceService c = new ServiceService();
        ArrayList<Service> list = (ArrayList<Service>) c.getAll();
        for (int i = 0; i < list.size(); i++) {
            nom = list.get(i).getNom_service();
            mychoicebox.getItems().add(nom);
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
            GridPane.setMargin(checkBox, new Insets(10));
            checkBox.setSelected(false);
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
    private void importIcone(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
       
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png"));
        selectedFile = fc.showOpenMultipleDialog(null);
        if (selectedFile != null) {
            list.getItems().clear();
            for (int i = 0; i < selectedFile.size(); i++) {

                list.getItems().add(selectedFile.get(i).getName());
                try {
                    Path sourcePath = Paths.get(selectedFile.get(i).getAbsolutePath());
                   
                    Path targetPath = Paths.get("C:/xamp2/htdocs/img/" + selectedFile.get(i).getName());
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        } else {
            System.out.println("Not valid file");
        }
    }
//------------------------------------------------------------------------------    
     public void loadController(ListerServicePrestataireController l){
        lst=l;
    }
    
}
