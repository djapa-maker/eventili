/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.Devis;

import entities.Event;
import entities.ServiceReservation;
import gui.frontOffice.transaction.TransactionController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import services.EventService;
import services.ServiceReservationService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class PDFGeneratorController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Button telech;
    @FXML
    private Button Payer;
    private PDDocument pdDocument;
    private int idev;
    private Button Annuler;
    @FXML
    private Button annulerbtn;
    EventService es = new EventService();
    ServiceReservationService srs = new ServiceReservationService();

    public ImageView getPdfImageView() {
        return img;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setIdEvent(int id) {
        idev = id;
    }

    public void setPdDocument(PDDocument pdDocument) {
        this.pdDocument = pdDocument;
    }

    @FXML
    private void download(ActionEvent event) throws COSVisitorException {
        if (pdDocument != null) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Télécharger le devis");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(telech.getScene().getWindow());
                if (file != null) {
                    pdDocument.save(file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void Payer(ActionEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../transaction/transaction.fxml"));
        Parent Root = addLoader.load();
        TransactionController list = addLoader.getController();
        list.setIdEvent(idev);
        Stage stage = (Stage) Payer.getScene().getWindow();
        stage.setScene(new Scene(Root));
    }

    @FXML
    private void Annuler(ActionEvent event) {
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
                Event t = es.findEventById(idev);
                es.supprimer(t);
                ServiceReservation rs = srs.findByIdEvent(idev);
                srs.supprimer(rs);
            } else if (response == revenirButton) {
                alert.close();
            }
        });
    }



}
