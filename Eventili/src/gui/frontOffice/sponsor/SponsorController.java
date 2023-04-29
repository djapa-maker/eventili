/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.frontOffice.sponsor;

import entities.Event;
import entities.Personne;
import entities.Transactions;
import entities.sponsoring;
import gui.frontOffice.transaction.TransactionController;
import gui.sigleton.singleton;
import gui.singleton.transeng;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.EventService;
import services.PersonneService;
import services.TransactionsService;
import services.sponsoringservice;

/**
 * FXML Controller class
 *
 * @author sel3a
 */
public class SponsorController implements Initializable {

    private TransactionsService ts = new TransactionsService();
    private EventService es = new EventService();
    private PersonneService ps = new PersonneService();
    singleton data = singleton.getInstance();
     transeng info=transeng.getInstance();
    Personne pa = data.getUser();
    String ammount = ""; //integration caluclue ammount
    @FXML
    private TextField idnbjour;
    @FXML
    private TextField idnombreimpression;
    private ComboBox<String> iddevise;
    private TextField idmontanttotale;
    @FXML
    private DatePicker iddatadebut;
    @FXML
    private DatePicker iddatefin;
    @FXML
    private ComboBox<String> devise;
    @FXML
    private TextField totale;
    @FXML
    private ComboBox<String> mode;
    @FXML
    private Button annulerbtn;

    int ide=219;
    @FXML
    private Button sponso;

    private void updateNumberOfDaysLabel(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            // calculate the number of days between the start and end dates
            long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

            // update the label text
            idnbjour.setText(Long.toString(numberOfDays));
        } else {
            // clear the label text
            idnbjour.setText("");
        }
    }

    public void setEventId(int id) {
        ide = id;
         System.out.println("welcome"+ide);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         idnombreimpression.textProperty().addListener((observable, oldValue, newValue) -> {
            int nombre_impression = Integer.parseInt(newValue);
            int total = nombre_impression * 10;
            totale.setText(Integer.toString(total));
            ammount = String.valueOf(total);
        });
        mode.setItems(FXCollections.observableArrayList(ts.get_modepayment()));
        devise.setItems(FXCollections.observableArrayList(ts.get_devise()));
        totale.setEditable(false);
        devise.setValue("EUR");
        mode.setValue("Square");
        devise.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                     // Ajouter le code que vous souhaitez exécuter lorsque l'option est sélectionnée

                String apiKey = "fwRBmaxRmRvD8xcUFFfAY4CERrYvRnMb";
                String baseUrl = "https://api.apilayer.com/fixer/convert";
                String toCurrency = newValue;
                String fromCurrency = oldValue;
                System.out.println(oldValue + ammount + newValue);
                String urll = String.format("%s?apikey=%s&amount=%s&from=%s&to=%s", baseUrl, apiKey, ammount, fromCurrency, toCurrency);
                Request request = new Request.Builder()
                        .url(urll)
                        .method("GET", null)
                        .build();

                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Response response;

                response = client.newCall(request).execute();

// Convertir la réponse en objet JSON
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.body().string());

// Extraire la valeur de la clé "result"
                double result = (double) jsonObject.get("result");
                totale.setText(String.valueOf(result));
            } catch (IOException ex) {
                Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        idnombreimpression.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idnombreimpression.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

// add a listener to the start date picker
        iddatadebut.valueProperty().addListener((observable, oldValue, newValue) -> {
            // check that the start date is after the current date
            if (newValue != null && newValue.isBefore(LocalDate.now())) {
                // show a warning message
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Invalid start date");
                alert.setContentText("The start date must be after the current date.");
                alert.showAndWait();
            } else {
                // update the number of days label
                updateNumberOfDaysLabel(iddatadebut.getValue(), iddatefin.getValue());
            }
        });
  // add a listener to the end date picker
        iddatefin.valueProperty().addListener((observable, oldValue, newValue) -> {
            // check that the end date is after the start date
            LocalDate startDate = iddatadebut.getValue();
            if (newValue != null && startDate != null && newValue.isBefore(startDate)) {
                // show a warning message
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Invalid end date");
                alert.setContentText("The end date must be after the start date.");
                alert.showAndWait();
            } else {
                // update the number of days label
                updateNumberOfDaysLabel(iddatadebut.getValue(), iddatefin.getValue());
            }
        });

    }

    @FXML

private void ajoutersponsor_client(MouseEvent event) {
    try {
                       info.setType(1);
        info.setAmmount(totale.getText());
        // Load the transaction scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../transaction/transaction.fxml"));
        Parent root = loader.load();
                TransactionController transactionController = loader.getController();
                       loader.getController();
 transactionController.settype(0,totale.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        
        // Get the transaction ID from the transaction scene
         int transactionId = transactionController.getTransactionId();
        
        // Use the transaction ID to create the sponsoring
        sponsoringservice ps = new sponsoringservice();
        TransactionsService ts = new TransactionsService();
        EventService es = new EventService();
        Event eve = es.findEventById(ide); //  
        String date_debut = iddatadebut.getValue().toString();
        String date_fin = iddatefin.getValue().toString();
        int nombre_impression = Integer.parseInt(idnombreimpression.getText());
       
          Transactions tr = ts.findById(transactionId);
        LocalDate myDate = iddatadebut.getValue();
        int year = myDate.getYear();
        int Month = myDate.getMonthValue();
        int Day = myDate.getDayOfMonth();
        LocalDate myDatee = iddatefin.getValue();
        int yeaer = myDatee.getYear();
        int Monthe = myDatee.getMonthValue();
        int Daye = myDatee.getDayOfMonth();
        sponsoring sp = new sponsoring(0, LocalDateTime.of(year, Month, Day, 0, 0), LocalDateTime.of(yeaer, Monthe, Daye, 0, 0), nombre_impression, eve, tr);
        ps.ajouter(sp);
        
        // Reset the sponsor form
      reset_sponsor( );
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void checkdate(ActionEvent event) {
    }

    @FXML
    private void annuler(MouseEvent event) {
        //redirection
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

    private void reset_sponsor() {
        iddatadebut.setValue(null);
        iddatefin.setValue(null);
        idnombreimpression.clear();
        idnbjour.clear();
        devise.setValue("EUR");
        totale.clear();
        mode.setValue("Square");
    }

}
