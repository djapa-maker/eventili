/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui.frontOffice.transaction;

import entities.Event;
import entities.Personne;
import entities.Transactions;
import gui.sigleton.singleton;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.EventService;
import services.PersonneService;
import services.TransactionsService;
import java.io.*;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import okhttp3.*;

import org.json.simple.JSONObject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
// api
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import com.stripe.param.TokenCreateParams.Card;
import entities.ServiceReservation;
import gui.singleton.transeng;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import services.ServiceReservationService;

/**
 * *
 */
/**
 * FXML Controller class
 *
 * @author sel3a
 */
public class TransactionController implements Initializable {

    private TransactionsService ts;
    private EventService es;
    private PersonneService ps;
    // Twilio API credentials

    private static final String ACCOUNT_SID = "AC23ac3c900ad7d1daac0be7b4fd4848e8";
    private static final String AUTH_TOKEN = "90c789305ea78e014db15435039c55c0";
    // Twilio phone number
    private static final String TWILIO_PHONE_NUMBER = "+12707336802";
    @FXML
    private ChoiceBox<String> devise;
    @FXML
    private ChoiceBox<String> mode;
    @FXML
    private TextField totale;
    @FXML
    private Button annulerbtn;
    @FXML
    private Button payer;
    singleton data = singleton.getInstance();
    Personne pa = data.getUser();
    transeng info = transeng.getInstance();
    @FXML
    private TextField cvv;
    @FXML
    private TextField month;
    @FXML
    private TextField numcard;
    @FXML
    private TextField year;
    @FXML
    private TextField hourr;
    @FXML
    private TextField hourl;
    @FXML
    private TextField minr;
    @FXML
    private TextField minl;
    /**
     * Initializes the controller class.
     */
    private static final String CVV_REGEX = "\\d{3,4}";
    Label errorLabel = new Label();
    @FXML
    private Label datec;
    @FXML
    private Label cvvc;
    @FXML
    private TextField mdpc;
    @FXML
    private Label enter;
    @FXML
    private ImageView card;
    @FXML
    private TextField numordre;
    @FXML
    private TextField totale121;
    @FXML
    private TextField cvvcp;
    @FXML
    private TextField cvvcp1;
    @FXML
    private TextField nompre;
    @FXML
    private TextField numcardcopy;
    private int transactionId = 0;
    private int type = info.getType();
    private String mountinit = info.getAmmount();
    String ammount = info.getAmmount(); //integration caluclue ammount

    ServiceReservationService srs = new ServiceReservationService();
    @FXML
    private Button closeButton;

    public void setOnTransactionClose(EventHandler<ActionEvent> handler) {
        closeButton.setOnAction(handler);
    }

    public TransactionController(String mount, int type) {

        this.type = type;
        ammount = mount;
        mountinit = mount;
    }

    public TransactionController() {

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void settype(int t, String mount) {

        ammount = mount;
        mountinit = mount;
        this.type = t;
    }
// Helper method to get the specified digit of the hour

    private String getHourDigit(LocalTime time, int digit) {
        if (time.getHour() < 10 && digit == 2) {
            return "0";
        }
        return String.valueOf(time.getHour()).substring(digit - 1, digit);
    }

// Helper method to get the specified digit of the minute
    private String getMinuteDigit(LocalTime time, int digit) {
        if (time.getMinute() < 10 && digit == 2) {
            return "0";
        }
        return String.valueOf(time.getMinute()).substring(digit - 1, digit);
    }

    int monthe = 1;
    int yeare = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("type :" + info.getType());
        System.out.println("idev :" + info.getIdev());
//      System.out.println("ammount :"+ts.calcule_montanttotale(info.getIdev()));
        //*******************setup notifaction sms
        numcard.textProperty().addListener((obs, oldVal, newVal) -> {
            numcardcopy.setText(newVal);
        });

        //********************
        nompre.setText(pa.getNom_pers() + " " + pa.getPrenom_pers());
        // Add a change listener to the text field to update the label
        month.textProperty().addListener((obs, oldVal, newVal) -> {
            // Update the label with the new value
            cvvcp.setText(newVal);
        });
        // Add a change listener to the text field to update the label
        year.textProperty().addListener((obs, oldVal, newVal) -> {
            // Update the label with the new value
            cvvcp1.setText(newVal);
        });
        //************animation :
        // Set the initial position of the card
        card.setLayoutX(-card.getImage().getWidth() + card.getImage().getWidth() / 2 + 30);

        // Create an animation to move the card from left to right
        Timeline timeliner = new Timeline(
                new KeyFrame(Duration.seconds(1), new KeyValue(card.layoutXProperty(), 100)));

        // Set the animation to play in slow motion
        timeliner.setRate(0.7);
        timeliner.play();

        //*********************year  
        year.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                year.setText(oldValue);
                year.setStyle("-fx-border-color: red;");
                yeare = 1;
            } else {
                int yearr = Integer.parseInt(newValue);
                if (yearr < 1 || yearr > 29) {
                    year.setStyle("                                                                                                                                                                                                                        -fx-border-color: red;");
                    yeare = 1;
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        year.setText(oldValue);
                        year.setStyle("-fx-border-color: none;");
                        yeare = 0;
                    });
                    pause.play();
                } else {
                    year.setStyle("-fx-border-color: none;");
                    yeare = 0;
                }
            }
        });

        //********************month       
        month.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                month.setText(oldValue);
                month.setStyle("-fx-border-color: red;");
                monthe = 1;
            } else {
                int mont = Integer.parseInt(newValue);
                if (mont < 1 || mont > 12) {
                    month.setText(oldValue);
                    monthe = 1;
                    month.setStyle("-fx-border-color: red;");
                } else {
                    month.setStyle("-fx-border-color: none;");
                    monthe = 0;
                }
            }
        });
//***************************cvv        
        cvv.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove all non-digit characters
            String digitsOnly = newValue.replaceAll("[^\\d]", "");

            // If the text field's text is longer than 3 characters, truncate it
            if (digitsOnly.length() > 3) {
                digitsOnly = digitsOnly.substring(0, 3);
            }

            // Update the text field's text
            cvv.setText(digitsOnly);

        });

//*******************************************num card control
        numcard.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove all non-digit characters
            String digitsOnly = newValue.replaceAll("[^\\d]", "");

            // Insert a space after every 4 digits
            String formattedText = digitsOnly.replaceAll("(.{4})(?!$)", "$1 ");

            // Update the text field's text
            numcard.setText(formattedText);

            // If the text field's text is longer than 19 characters, truncate it
            if (numcard.getText().length() > 19) {
                numcard.setText(numcard.getText(0, 19));
            }

        });
//*******************************************num card control

// Create a timeline that updates the labels every second
        Timeline timelinee = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now();
            hourl.setText(getHourDigit(currentTime, 1));
            hourr.setText(getHourDigit(currentTime, 2));
            minl.setText(getMinuteDigit(currentTime, 1));
            minr.setText(getMinuteDigit(currentTime, 2));
        }));
        timelinee.setCycleCount(Animation.INDEFINITE);
        timelinee.play();
        ts = new TransactionsService();
        ps = new PersonneService();
        es = new EventService();
        mode.setItems(FXCollections.observableArrayList(ts.get_modepayment()));
        devise.setItems(FXCollections.observableArrayList(ts.get_devise()));
        if (info.getType() == 2) {
            ammount = String.valueOf(ts.calcule_montanttotale(info.getIdev()));//integration
        } else {

        }
        totale.setText(ammount);
        totale.setEditable(false);
        devise.setValue("EUR");
        mode.setValue("Square");
        //cvv

//devise api
        devise.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Ajouter le code que vous souhaitez exécuter lorsque l'option est sélectionnée

                String apiKey = "fwRBmaxRmRvD8xcUFFfAY4CERrYvRnMb";
                String baseUrl = "https://api.apilayer.com/fixer/convert";
                String amount = "";
                if (info.getType() == 2) {//event
                    amount = String.valueOf(ts.calcule_montanttotale(info.getIdev()));//integration
                } else if (type == 0) {

                    amount = mountinit;

                }
                if (newValue.compareTo("EUR") != 0) {
                    String fromCurrency = "EUR";
                    String toCurrency = newValue;

                    System.out.println(oldValue + amount + newValue);
                    String urll = String.format("%s?apikey=%s&amount=%s&from=%s&to=%s", baseUrl, apiKey, amount, fromCurrency, toCurrency);
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
                    System.out.println(result);
                    totale.setText(String.valueOf(result));
                    ammount = String.valueOf(result);
                } else {

                    amount = String.valueOf(ts.calcule_montanttotale(info.getIdev()));//integration

                    totale.setText(amount);

                }

            } catch (IOException ex) {
                Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Create a timeline that updates every second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    // Get the current time
                    LocalTime now = LocalTime.now();

                    // Update the labels
                    hourl.setText(String.valueOf(now.getHour() / 10));
                    hourr.setText(String.valueOf(now.getHour() % 10));
                    minl.setText(String.valueOf(now.getMinute() / 10));
                    minr.setText(String.valueOf(now.getMinute() % 10));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        System.out.println("aaa" + hourl.getText());
        // Set the timeline to loop indefinitely
        timeline.setCycleCount(Animation.INDEFINITE);

        // Start the timeline
        timeline.play();

    }

    @FXML
    private void annuler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler la création");
        alert.setHeaderText("les données que vous avez entrer seront perdues");
        alert.setContentText("Cliquer sur OK pour continuer le paiement");

        ButtonType okButton = new ButtonType("OK");
        ButtonType revenirButton = new ButtonType("Revenir");
        alert.getButtonTypes().setAll(okButton, revenirButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                Stage stage = (Stage) annulerbtn.getScene().getWindow();
                stage.close();
                Event t = es.findEventById(info.getType());
                es.supprimer(t);
                ServiceReservation rs = srs.findByIdEvent(info.getType());
                srs.supprimer(rs);
            } else if (response == revenirButton) {
                alert.close();
            }
        });
    }

    @FXML
    private void payer(ActionEvent event) throws IOException {
        Stripe.apiKey = "sk_test_51MiCB1Gqpa2PZgAVrlv2LKXgwScv6giwP2nPMkB6pTxddtrFvf9Om5ZHuLdUwovIzRy1ChhrZvbuKtStvgwEnEsS00B3Sw1WSs";
        String payment = "";
        int close = 0;
        int suceed = 10;
        if (monthe == 1 || yeare == 1 || month.getText().isEmpty() || year.getText().isEmpty() || cvv.getText().isEmpty() || mdpc.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de paiement");
            alert.setHeaderText("Le paiement a échoué.");
            alert.setContentText("Veuillez vérifier les informations de paiement et réessayer.");

            alert.showAndWait();

        } else {
            Transactions tr = new Transactions();
            Event eve = new Event();
            eve = es.findEventById(info.getType());//integration

            float montant_tot = Float.parseFloat(ammount);
            String devis = devise.getValue();
            String mode_trans = mode.getValue();
            String date_trans = java.time.LocalDate.now().toString();

            //control de payement
            //************api
            Long amount = Math.round(Double.parseDouble((totale.getText())));
            TokenCreateParams tokenParams = TokenCreateParams.builder()
                    .setCard(Card.builder()
                            .setNumber(numcard.getText().replaceAll("\\s", ""))
                            .setExpMonth(month.getText())
                            .setExpYear(year.getText())
                            .setCvc(cvv.getText())
                            .build())
                    .build();

            try {
                String tokenId = Token.create(tokenParams).getId();
                System.out.println("ammount" + amount);
                // Create a charge with the test token
                ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                        .setAmount(amount * 100)
                        .setCurrency(devise.getValue())
                        .setDescription("charge")
                        .setSource(tokenId)
                        .build();

                Charge charge = Charge.create(chargeParams);

                System.out.println("Charge succeeded! Status: " + charge.getStatus());
                tr = new Transactions(0, 0, montant_tot, devis, LocalDateTime.now(), mode_trans, pa);
                transactionId = ts.ajouterget(tr);
                System.out.println("transactionId" + transactionId);
            } catch (CardException e) {
                System.out.println("Charge failed: " + e.getMessage());
                suceed = 0;

            } catch (StripeException e) {
                System.out.println("Error: " + e.getMessage());
                suceed = -1;
            }

            switch (suceed) {
                case 0: {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur de paiement");
                    alert.setHeaderText("Le paiement a échoué.");
                    alert.setContentText("Veuillez vérifier les informations de paiement et réessayer.");

                    alert.showAndWait();
                    payment = "Erreur de paiement :Le paiement a échoué";
                    break;
                }
                case -1: {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur de paiement");
                    alert.setHeaderText("Le paiement a échoué en raison d'une erreur système.");
                    alert.setContentText("Veuillez réessayer plus tard ou contacter le support.");
                    payment = "Erreur de paiement :  Le paiement a échoué";
                    alert.showAndWait();
                    break;
                }
                default: {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Paiement réussi");
                    alert.setHeaderText("Le paiement a été effectué avec succès.");
                    alert.setContentText("Merci pour votre achat.");
                    payment = "Paiement réussi :  Le paiement a été effectué avec succès";
                    alert.showAndWait();
                    close = 1;
                    break;
                }
            }

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            System.out.println(pa.getNum_tel());
           Message.creator(new PhoneNumber("+21652381380"), new PhoneNumber(TWILIO_PHONE_NUMBER), payment).create();
             }
        if (close == 1) {
 //create a new scene object for the old scene
            Parent root = FXMLLoader.load(getClass().getResource("../organisationev/MesEvenements.fxml"));
            Scene oldScene = new Scene(root);

// Get the current stage and close it
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

// Create a new stage for the old scene
//Stage oldStage = new Stage();
//oldStage.setScene(oldScene); // set the old scene
//oldStage.show(); // show the old stage
        }

    }

}
