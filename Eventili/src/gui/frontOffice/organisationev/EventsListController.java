/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.organisationev;

import entities.Event;
import gui.frontOffice.organisationev.DetailEvent.EventDetailsController;
import gui.frontOffice.organisationev.modifierevenement.ModificationController;
import gui.frontOffice.sponsor.SponsorController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.EventService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class EventsListController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Label day;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label EventName;
    @FXML
    private Text desc;
    @FXML
    private Label prix;
    @FXML
    private Button sup;
    @FXML
    private Pane PaneEv;
    @FXML
    private Button btnPS;

    private Event e1;
    private int idev;
    EventService es = new EventService();

    DateTimeFormatter dayF = DateTimeFormatter.ofPattern("EEE");
    DateTimeFormatter MonthF = DateTimeFormatter.ofPattern("MMM");
    DateTimeFormatter dateF = DateTimeFormatter.ofPattern("dd");
    DateTimeFormatter timeF = DateTimeFormatter.ofPattern("HH:mm");
    @FXML
    private Label EventSt;

    public void setData(Event e) throws FileNotFoundException {
//        Image image1 = new Image(getClass().getResourceAsStream(e.getImage()));
//        img.setImage(image1);
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/" + e.getImage());
        Image img1 = new Image(inputstream);
        img.setImage(img1);
        e1 = e;
        idev = e.getId_ev();
        System.out.println("setData" + idev);
        Rectangle clip = new Rectangle(img.getFitWidth(), img.getFitHeight());
        clip.setArcHeight(50);
        clip.setArcWidth(50);
        clip.setStroke(Color.BLACK);
        img.setClip(clip);

        EventName.setText(e.getTitle());
        day.setText(e.getDate_debut().format(MonthF));
        date.setText(e.getDate_debut().format(dateF));
        time.setText(e.getDate_debut().format(timeF) + " - " + e.getDate_fin().format(timeF));
        desc.setText(e.getDescription());
        if (e.getPrice() == 0) {
            prix.setText("Gratuit");
        } else {
            prix.setText(Float.toString(e.getPrice()) + "DT");
        }
        System.out.println(e.getVisibilite());
        if (e.getVisibilite().compareTo("Public") == 0) {

            btnPS.setText("Sponsoriser");
        } else {
            btnPS.setText("Publier");
        }

        int resultDD = e.getDate_debut().compareTo(LocalDateTime.now());
        int resultDF = e.getDate_fin().compareTo(LocalDateTime.now());
        if ((resultDD < 0) && (resultDF < 0)) {
            btnPS.setVisible(false);
            EventSt.setVisible(true);
            EventSt.setTextFill(Color.rgb(255, 134, 0));
            EventSt.setText("Terminé");
            EventSt.setUnderline(true);
        } else if ((resultDD > 0) && (resultDF > 0)) {
            System.out.println("");
        } else {
            btnPS.setVisible(false);
            EventSt.setVisible(true);
            EventSt.setTextFill(Color.rgb(67, 124, 27));
            EventSt.setText("En cours");
            EventSt.setUnderline(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void supprimer(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Supprimer événement");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer cet événement?");
        ButtonType okButton = new ButtonType("Supprimer");
        ButtonType cancelButton = new ButtonType("Annuler");
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            es.supprimer(e1);
        } else {
            alert.close();
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("modifierevenement/Modification.fxml"));
        Parent Root = addLoader.load();
        ModificationController modifC = addLoader.getController();
        modifC.modifierData(e1, idev);
        Stage Stage = new Stage();
        Stage.setScene(new Scene(Root));
        Stage.showAndWait();

    }

    public void handlePaneClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailEvent/EventDetails.fxml"));
            Parent root = loader.load();
            EventDetailsController eventDetailsController = loader.getController();
            try {
                eventDetailsController.setEventId(idev);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void PublierSponsoriser(ActionEvent event) throws IOException {

        if (btnPS.getText().equals("Publier")) {
            Event e2 = new Event(e1.getParticipantLimit(), e1.getPrice(), e1.getTitle(), e1.getDescription(), e1.getImage(), e1.getType(), "Public", e1.getDate_debut(), e1.getDate_fin(), e1.getC(), e1.getPers());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Publier événement");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr(e) de vouloir publier cet événement?");
            ButtonType okButton = new ButtonType("Publier");
            ButtonType cancelButton = new ButtonType("Annuler");
            alert.getButtonTypes().setAll(okButton, cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                es.modifier(idev, e2);
                btnPS.setText("Sponsoriser");
            } else {
                alert.close();
            }
        } else if (btnPS.getText().equals("Sponsoriser")) {
            FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../sponsor/sponsor.fxml"));
            Parent loader = addLoader.load();
            SponsorController modifController = addLoader.getController();
            System.out.println("publier" + idev);
            modifController.setEventId(idev);

            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnPS.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
    }
}
