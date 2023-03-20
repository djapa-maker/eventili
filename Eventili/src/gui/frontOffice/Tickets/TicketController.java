/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.Tickets;

import entities.Event;
import entities.EventCateg;
import entities.Personne;
import entities.Ticket;
import entities.Transactions;
import gui.SingletonEvent.SingletonE;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import services.EventService;
import services.TicketService;
import services.TransactionsService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class TicketController implements Initializable {

    SingletonE data = SingletonE.getInstance();
    EventService eventc = new EventService();
    TicketService ticketc = new TicketService();
    TransactionsService trans = new TransactionsService();
    EventService es = new EventService();
    Event e = new Event();
    int nbP;
    TransactionsService t = new TransactionsService();
    @FXML
    private Spinner<Integer> qte;
    @FXML
    private Button btnacheter;
    @FXML
    private Label nomevent;
    @FXML
    private Label datehr;
    @FXML
    private Label prix;
    private int idEvt;

    private float price;

    final int initialQteValue = 1;

    SpinnerValueFactory<Integer> qteFactory;
    @FXML
    private Rectangle imgE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        qteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        qte.setValueFactory(qteFactory);
    }

    public void loadData(int evtId, String evtName, String evtDate, String price) throws FileNotFoundException {
        idEvt = evtId;
        e = es.findEventById(idEvt);

        String Image = e.getImage();
        FileInputStream inputstream = new FileInputStream("C:\\xampp\\htdocs\\img\\" + Image);
        Image image = new Image(inputstream);
        imgE.setStroke(javafx.scene.paint.Color.ORANGE);
        imgE.setFill(new ImagePattern(image));

        nomevent.setText(evtName);
        prix.setText(price);
        datehr.setText(evtDate);
    }

    @FXML
    private void acheterTicket(ActionEvent event) throws IOException {
        nbP = e.getParticipantLimit();
        if (qteFactory.getValue() > nbP) {

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Ticket");
            a.setContentText("nombre de tickets limités");
            a.setHeaderText("Achat impossible, nombre de tickets restants: " + nbP);
            Optional<ButtonType> action = a.showAndWait();
            if (action.get() == ButtonType.OK) {
                Stage stage1 = (Stage) btnacheter.getScene().getWindow();
                stage1.close();

            }
        } else {
            price = Float.parseFloat(prix.getText());
            float prixTotal;
            float reduction;
            switch (qteFactory.getValue()) {
                case 1:
                    break;
                case 2:
                    prixTotal = price * qteFactory.getValue();
                    reduction = 0.05f * qteFactory.getValue() * price;
                    price -= reduction;
                    price = price * qteFactory.getValue();
                    System.out.println(price);
                    break;
                case 3:
                    prixTotal = price * qteFactory.getValue();
                    reduction = 0.05f * qteFactory.getValue() * price;
                    price -= reduction;
                    price = price * qteFactory.getValue();
                    break;
                case 4:
                    prixTotal = price * qteFactory.getValue();
                    reduction = 0.05f * qteFactory.getValue() * price;
                    price -= reduction;
                    price = price * qteFactory.getValue();
                    break;
                case 5:
                    prixTotal = price * qteFactory.getValue();
                    reduction = 0.05f * qteFactory.getValue() * price;
                    price -= reduction;
                    price = price * qteFactory.getValue();
                    break;
                default:
                    break;

            }

            Transactions te = trans.findById(1);
            Event e = es.findEventById(idEvt);
            Ticket t = new Ticket(1, qteFactory.getValue(), price, "actif", te, e);
            System.out.println(t);
            data.setE(e);
            data.setT(t);
            ticketc.ajouter(t);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ticketScan.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Stage stg = (Stage) btnacheter.getScene().getWindow();
            Scene s = new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
        }
    }

}
