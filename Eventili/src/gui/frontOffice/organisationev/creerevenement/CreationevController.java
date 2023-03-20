package gui.frontOffice.organisationev.creerevenement;

import entities.Event;
import entities.EventCateg;
import entities.Personne;
import entities.ServiceReservation;
import entities.SousServices;
import gui.frontOffice.client.ListerServiceController;
import gui.sigleton.singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EventCategService;
import services.EventService;
import services.ServiceReservationService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class CreationevController implements Initializable {
    singleton data= singleton.getInstance();
    Personne p1=data.getUser();
    @FXML
    private ChoiceBox<String> typeevbox;
    private String[] type = {"Gratuit", "Payant"};
    @FXML
    private DatePicker datepicker;
    @FXML
    private ChoiceBox<String> heuredeb;
    @FXML
    private ChoiceBox<String> mindeb;
    @FXML
    private ChoiceBox<String> pmamdeb;
    private String[] heure = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] min = {"00", "15", "30", "45"};
    private String[] ampm = {"AM", "PM"};
    @FXML
    private ChoiceBox<String> heurefin;
    @FXML
    private ChoiceBox<String> minfin;
    @FXML
    private ChoiceBox<String> pmamfin;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private Button insertimg;
    @FXML
    private Spinner<Integer> nbtickets;
    int currentValue;
    @FXML
    private TextField prix;
    @FXML
    private ImageView image;
    EventCategService ecs = new EventCategService();
    private ArrayList<EventCateg> categories;
    ArrayList<String> nc = new ArrayList();
    ServiceReservationService res = new ServiceReservationService();
    @FXML
    private ChoiceBox<String> categorie;
    @FXML
    private Label L1;
    @FXML
    private Label L2;
    @FXML
    private Label L3;
    @FXML
    private Button btnenr;
    @FXML
    private Button annulerbtn;
    String url = "placeholder.png";
    Event e = new Event();
    Event e1 = new Event();
    ArrayList<SousServices> sousS = new ArrayList<>();
    ArrayList<Integer> sousId = new ArrayList<>();
    //ServiceReservation sr=new ServiceReservation(e, false, sousS);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeevbox.getItems().addAll(type);
        typeevbox.setValue("Gratuit");

        heuredeb.getItems().addAll(heure);
        heuredeb.setValue("09");

        mindeb.getItems().addAll(min);
        mindeb.setValue("00");

        pmamdeb.getItems().addAll(ampm);
        pmamdeb.setValue("AM");

        heurefin.getItems().addAll(heure);
        heurefin.setValue("06");

        minfin.getItems().addAll(min);
        minfin.setValue("00");

        pmamfin.getItems().addAll(ampm);
        pmamfin.setValue("PM");

        categories = (ArrayList<EventCateg>) ecs.getAll();
        for (EventCateg c : categories) {
            nc.add(c.getType());
        }
        categorie.getItems().addAll(nc);
        categorie.setValue(categories.get(0).getType());

        titre.setTextFormatter(textFormatter);
        description.setTextFormatter(textFormatter2);

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(10);
        nbtickets.setValueFactory(valueFactory);
        currentValue = nbtickets.getValue();

        prix.setVisible(false);
        nbtickets.setVisible(false);
        L1.setVisible(false);
        L2.setVisible(false);
        L3.setVisible(false);
        typeevbox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateVisibility();
        });

        prix.setTextFormatter(formatter);

    }

    private void updateVisibility() {
        if (typeevbox.getValue() != null && typeevbox.getValue().equals("Payant")) {
            nbtickets.setVisible(true);
            prix.setVisible(true);
            L1.setVisible(true);
            L2.setVisible(true);
            L3.setVisible(true);
        } else {
            nbtickets.setVisible(false);
            prix.setVisible(false);
            L1.setVisible(false);
            L2.setVisible(false);
            L3.setVisible(false);
        }
    }

    TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() > 35) {
            return null;
        }
        return change;
    });

    TextFormatter<String> textFormatter2 = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() > 200) {
            return null;
        }
        return change;
    });

    TextFormatter<Double> formatter = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*(\\.\\d{0,3})?")) {
            return change;
        } else {
            return null;
        }
    });

    @FXML
    private void checkdate(ActionEvent event) {
        LocalDate myDate = datepicker.getValue();
        LocalDate today = LocalDate.now();
        if (myDate.isBefore(today) || myDate.equals(today)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Date invalide");
            alert.setHeaderText("Action non autorisée");
            alert.setContentText("Merci de sélectionner une date ultérieure à aujourd'hui.");
            alert.showAndWait();
            datepicker.setValue(today.plusDays(1));
        }
    }

    @FXML
    private void importImage(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:/xampp/htdocs/img/"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            //System.out.println(selectedFile.getName());
            FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+selectedFile.getName());
            Image img = new Image(inputstream);
            image.setImage(img);
            url = selectedFile.getName();
            System.out.println(url);
        }
    }

    @FXML
    private void Annulercreation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler la création");
        alert.setHeaderText("les données que vous avez entrer seront perdues");
        alert.setContentText("Cliquer sur OK pour continuer ou bien Revenir pour continuer la création");

        ButtonType okButton = new ButtonType("OK");
        ButtonType revenirButton = new ButtonType("Revenir");
        alert.getButtonTypes().setAll(okButton, revenirButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                Stage stage = (Stage) annulerbtn.getScene().getWindow();
                stage.close();
            } else if (response == revenirButton) {
                alert.close();
            }
        });
    }

    @FXML
    private void create(ActionEvent event) throws IOException, SQLException {
        int heured;
        int heuref;
        int LP;
        float p;
        LocalDate myDate = datepicker.getValue();
        titre.setStyle("");
        description.setStyle("");
        prix.setStyle("");
        datepicker.setStyle("");
        if (myDate == null) {
            datepicker.setStyle("-fx-border-color: red;");
        } else if (titre.getText().trim().isEmpty()) {
            titre.setStyle("-fx-border-color: red ;");
        } else if (description.getText().trim().isEmpty()) {
            description.setStyle("-fx-border-color: red ;");
        } else if ((prix.isVisible() == true) && (prix.getText().trim().isEmpty())) {
            prix.setStyle("-fx-border-color: red ;");

        } else {
            // Reset the border style
            titre.setStyle("");
            description.setStyle("");
            prix.setStyle("");
            // Create Event
            String title = titre.getText();
            String desc = description.getText();
            String typ = typeevbox.getValue();
            System.out.println(typ);
            if (typ.equals("Payant")) {
                LP = nbtickets.getValue();
                p = Float.parseFloat(prix.getText());
                System.out.println(p + "" + LP);
            } else {
                LP = 0;
                p = 0;
                System.out.println(p + "" + LP);
            }
            int year = myDate.getYear();
            int Month = myDate.getMonthValue();
            int Day = myDate.getDayOfMonth();
            if (pmamdeb.getValue().equals("PM")) {
                if (heuredeb.getValue().equals("12")) {
                    heured = 0;
                } else {
                    heured = Integer.parseInt(heuredeb.getValue()) + 12;
                }

            } else {
                heured = Integer.parseInt(heuredeb.getValue());
            }
            if (pmamfin.getValue().equals("PM")) {
                if (heurefin.getValue().equals("12")) {
                    heuref = 0;
                } else {
                    heuref = Integer.parseInt(heurefin.getValue()) + 12;
                }
            } else {
                heuref = Integer.parseInt(heurefin.getValue());
            }
            int mind = Integer.parseInt(mindeb.getValue());
            int minf = Integer.parseInt(minfin.getValue());
            EventCateg c = ecs.findByName(categorie.getValue());
            EventService es = new EventService();

            e = new Event(LP, p, title, desc, url, typ, "Privé", LocalDateTime.of(year, Month, Day, heured, mind), LocalDateTime.of(year, Month, Day, heuref, minf), c, p1);
            //System.out.println(e);
            es.ajouter(e);
            List<Event> e2 = es.getAll();
            //System.out.println(e2);
            e1 = e2.get(e2.size() - 1);
            FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../../client/listerService.fxml"));
            Parent Root = addLoader.load();
            ListerServiceController list = addLoader.getController();

            list.Data(e1);

            //System.out.println("crea"+e1);
            Stage stage = (Stage) btnenr.getScene().getWindow();
          //  stage.close();
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Succès");
//            alert.setHeaderText(null);
//            alert.setContentText("Evénement créé !");
//            alert.showAndWait();

//            Stage Stage = new Stage();
            stage.setScene(new Scene(Root));
            //stage.showAndWait();

        }
    }

}
