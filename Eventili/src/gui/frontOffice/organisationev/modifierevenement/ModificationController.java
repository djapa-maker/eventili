/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.organisationev.modifierevenement;

import entities.Event;
import entities.EventCateg;
import entities.Personne;
import gui.sigleton.singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class ModificationController implements Initializable {
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
    private Spinner<Integer> nbtickets;
    int currentValue;
    @FXML
    private TextField prix;
    @FXML
    private ImageView image;

    EventCategService ecs = new EventCategService();
    private ArrayList<EventCateg> categories;
    ArrayList<String> nc = new ArrayList();
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
    String url;
    int heured;
    int heuref;
    int LP;
    float p;
    int idev;
    @FXML
    private Button insertimg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeevbox.getItems().addAll(type);
        heuredeb.getItems().addAll(heure);
        mindeb.getItems().addAll(min);
        pmamdeb.getItems().addAll(ampm);
        heurefin.getItems().addAll(heure);
        minfin.getItems().addAll(min);
        pmamfin.getItems().addAll(ampm);
        categories = (ArrayList<EventCateg>) ecs.getAll();
        for (EventCateg c : categories) {
            nc.add(c.getType());
        }
        categorie.getItems().addAll(nc);
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(10);
        nbtickets.setValueFactory(valueFactory);

        titre.setTextFormatter(textFormatter);
        description.setTextFormatter(textFormatter2);

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
            FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+selectedFile.getName()); 
        Image img = new Image(inputstream); 
            image.setImage(img);
            url = selectedFile.getName();
        } else {
            url = "C:/xampp/htdocs/img/placeholder.png";
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

    public void modifierData(Event e, int id) throws FileNotFoundException {
        idev=id;
        int hd = e.getDate_debut().getHour();
        int hf = e.getDate_fin().getHour();
        titre.setText(e.getTitle());
        description.setText(e.getDescription());
        typeevbox.setValue(e.getType());
        if (e.getType().equals("Payant")) {
            SpinnerValueFactory<Integer> valueFactory
                    = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
            valueFactory.setValue(e.getParticipantLimit());
            nbtickets.setValueFactory(valueFactory);
            prix.setText(Float.toString(e.getPrice()));
        }
        datepicker.setValue(e.getDate_debut().toLocalDate());
        if ((hd > 12)) {
            hd -= 12;
            pmamdeb.setValue("PM");
            heuredeb.setValue("0" + Integer.toString(hd));
        } else if (hd == 0) {
            hd += 12;
            pmamdeb.setValue("PM");
            heuredeb.setValue("0" + Integer.toString(hd));
        } else {
            pmamdeb.setValue("AM");
            heuredeb.setValue("0" + Integer.toString(hd));
        }

        if ((hf > 12)) {
            hf -= 12;
            pmamfin.setValue("PM");
            heurefin.setValue("0" + Integer.toString(hf));
        } else if (hf == 0) {
            hf += 12;
            heurefin.setValue("0" + Integer.toString(hf));
            pmamfin.setValue("PM");
        } else {
            pmamfin.setValue("AM");
            heurefin.setValue("0" + Integer.toString(hf));
        }
        System.out.println(heurefin.getValue());

        if (e.getDate_debut().getMinute() == 0) {
            mindeb.setValue("00");
        }
        if (e.getDate_fin().getMinute() == 0) {
            minfin.setValue("00");
        }

        categorie.setValue(e.getC().getType());
        url = e.getImage();
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+url); 
        Image img = new Image(inputstream); 
        image.setImage(img);

    }

    @FXML
    private void update(ActionEvent event) {
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
            // Update Event
            String title = titre.getText();
            String desc = description.getText();
            String typ = typeevbox.getValue();
            System.out.println(typ);
            if (typ.equals("Payant")) {
                LP = nbtickets.getValue();
                p = Float.parseFloat(prix.getText());
            } else {
                LP = 0;
                p = 0;
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
            System.out.println(desc);
            System.out.println(heuref);
            EventCateg c = ecs.findByName(categorie.getValue());
            EventService es = new EventService();
            Event e = new Event(LP, p, title, desc, url, typ, "Privé", LocalDateTime.of(year, Month, Day, heuref, mind), LocalDateTime.of(year, Month, Day, heured, minf), c, p1);
            es.modifier(idev, e);
            Stage stage = (Stage) btnenr.getScene().getWindow();
            stage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Evénement modifié !");
            alert.showAndWait();

        }
    }

}
