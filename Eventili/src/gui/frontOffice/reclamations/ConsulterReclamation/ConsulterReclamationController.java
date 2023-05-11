/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.reclamations.ConsulterReclamation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import entities.reclamation;
import entities.reponse;
import gui.sigleton.singleton;
import gui.singleton.SingletonReclam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import services.ReclamationService;
import services.ReponseService;

/**
 * FXML Controller class
 *
 * @author othma
 */
public class ConsulterReclamationController implements Initializable {
    
    private SingletonReclam singletonR = SingletonReclam.getInstance();
    private reclamation reclam = singletonR.getReclam();
    @FXML
    private Button RetourBoutton;
    @FXML
    private Button EnvoyerButton;
    @FXML
    private TextArea MessageBox;
    @FXML
    private Label DateCloture;
    @FXML
    private GridPane Messages;
    @FXML
    private Label NumeroReclam;
    @FXML
    private Pane MainView;
    @FXML
    private ScrollPane scroll;
    private int autoupdate;
    private boolean editMode;
    private int editRep;
    private boolean badWordDetected = false;
    private boolean EmptyStringDetected = false;
    singleton data = singleton.getInstance();
    Personne P = data.getUser();
    ReclamationService reclamService = new ReclamationService();
    ReponseService reponseService = new ReponseService();

    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {              
                editMode = false;
                autoupdate = reponseService.getAllAnswers(reclam).size();
                LoadInfoReclam();
                checkCloturer();
                LoadMessages();

            }
        });
        //TODO Finir design
    }

    private void checkCloturer() {
        if (reclam.getStatus().equals("Cloturer")) {
            String Message = "La Reclamation a ete Cloture";
            DateCloture.setText(Message);
            DateCloture.setTextFill(Color.web("red"));
            MessageBox.setText("Message ....");
            FontAwesomeIconView fa = new FontAwesomeIconView(FontAwesomeIcon.SEND);
            fa.getStyleClass().add("EnvoyerIconeDisabled");
            EnvoyerButton.setGraphic(fa);
            MessageBox.setDisable(true);
            EnvoyerButton.setDisable(true);
        } else {
            DateCloture.setText(" ");
            DateCloture.setVisible(false);
        }
    }

    public void setReclam(reclamation reclam) {
        this.reclam = reclam;
    }

    private void LoadInfoReclam() {
        NumeroReclam.setText("Reclamation Numero : " + reclam.getId() + " - " + reclam.getTitre());
        FontAwesomeIconView fa = new FontAwesomeIconView(FontAwesomeIcon.SEND);
        fa.getStyleClass().add("EnvoyerIcone");
        EnvoyerButton.setGraphic(fa);
        MainView.hoverProperty().addListener(l -> {
            if (autoupdate != reponseService.getAllAnswers(reclam).size() && !editMode) {
                LoadMessages();
            }
        });
    }

    @FXML
    private void RetourBouttonFunction(ActionEvent event) {
       
            Stage thisStage = (Stage) RetourBoutton.getScene().getWindow();
            
            thisStage.close();
        
    }

    @FXML
    private void EnvoyerButtonFunction(ActionEvent event) {
        String Message = MessageBox.getText().trim();
        if (ControlSaisie(Message)) {
            reponse rep = new reponse(P, reclam, Message);
            reclam.setStatus("EnAttenteAdmin");
            reclamService.modifier(reclam);
            reponseService.ajouter(rep);
        } else if (badWordDetected) {
            Notifications.create().title("Eventili").text("Votre Message Contient un mot inaprorier").showError();
            badWordDetected = false;
        } else if (EmptyStringDetected) {
            Notifications.create().title("Eventili").text("Votre Message est vide").showError();
            EmptyStringDetected = false;
        }
        MessageBox.clear();
        LoadMessages();
    }

    private Group FirstMessage() {
        Text description = new Text(reclam.getDescription());
        int textW = (int) description.getLayoutBounds().getWidth();
        int textH = (int) description.getLayoutBounds().getHeight();
        int w = textW + 70;
        int h = textH + 40;
        Text date = new Text(reclam.getTimeStamp().toLocalDateTime().format(DateTimeFormatter.ofPattern("H:m")));
        int dateW = (int) date.getLayoutBounds().getWidth();
        int dateH = (int) date.getLayoutBounds().getHeight();
        h += dateH;

        Label descriptionWrap = new Label(description.getText());
        descriptionWrap.setFont(Font.font("Arial", 15));
        descriptionWrap.setTextFill(Color.WHITE);
        descriptionWrap.setTranslateX(16);
        descriptionWrap.setTranslateY(14);

        Label dateWrap = new Label(date.getText());
        dateWrap.setTranslateX(w - (dateW + 12));
        dateWrap.setTranslateY(textH + 10 * 2);
        dateWrap.setFont(Font.font("Arial", 12));
        dateWrap.setTextFill(Color.LIGHTGRAY);

        Rectangle rect = new Rectangle();
        rect.setWidth(w);
        rect.setHeight(h);
        rect.setArcHeight(30);
        rect.setArcWidth(30);
        rect.setFill(Color.rgb(0, 126, 229));
        Group message = new Group();
        message.getChildren().addAll(rect, descriptionWrap, dateWrap);
        return message;
    }

    private Group Messages(reponse rep, boolean isSender) {
        Group message = new Group();
        Text description = new Text(rep.getMessage());
        int textW = (int) description.getLayoutBounds().getWidth();
        int textH = (int) description.getLayoutBounds().getHeight();
        int w = textW + 70;
        int h = textH + 40;
        Text date = new Text(rep.getTimeStamp().toLocalDateTime().format(DateTimeFormatter.ofPattern("H:m")));
        int dateW = (int) date.getLayoutBounds().getWidth();
        int dateH = (int) date.getLayoutBounds().getHeight();
        h += dateH;
        Label dateWrap = new Label(date.getText());
        dateWrap.setTranslateX(w - (dateW + 12));
        dateWrap.setTranslateY(textH + 10 * 2);
        dateWrap.setFont(Font.font("Arial", 12));
        dateWrap.setTextFill(Color.LIGHTGREY);
        Rectangle rect = new Rectangle();
        rect.setWidth(w);
        rect.setHeight(h);
        rect.setArcHeight(30);
        rect.setArcWidth(30);
        Label descriptionWrap = new Label(description.getText());
        descriptionWrap.setFont(Font.font("Arial", 15));
        descriptionWrap.setTranslateX(16);
        descriptionWrap.setTranslateY(14);
        Button Delete = new Button();
        Delete.setStyle("-fx-background-color: transparent;");
        FontAwesomeIconView del = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
        del.getStyleClass().add("RemoveReponse");
        del.autosize();
        Delete.setGraphic(del);
        Delete.setOnAction(value -> {
            Supprimer(rep);
        });

        Delete.setTranslateX(w - (dateW + 12));
        Delete.setTranslateY(textH - 10 * 2);
        Button Edit = new Button();
        Edit.setTranslateX(w - (dateW + 12) + 14);
        Edit.setTranslateY((textH - 10 * 2));
        Edit.setStyle("-fx-background-color: transparent;");
        FontAwesomeIconView edit = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        edit.getStyleClass().add("editReponse");
        edit.autosize();
        Edit.setGraphic(edit);
        Edit.setOnAction(value -> {
            editMode = true;
            editRep = rep.getIdRep();
            LoadMessages();
        });
        if (isSender) {
            descriptionWrap.setTextFill(Color.WHITE);
            rect.setFill(Color.rgb(0, 126, 229));
        } else {
            descriptionWrap.setTextFill(Color.WHITE);
            rect.setFill(Color.rgb(90, 90, 90));
        }
        message.getChildren().addAll(rect, descriptionWrap, dateWrap, Delete, Edit);

        return message;
    }

    private void Supprimer(reponse r) {
        Alert delete = new Alert(Alert.AlertType.WARNING);
        delete.setTitle("Confirmer la Suppression du Message");
        delete.setContentText("Message: \n" + r.getMessage());
        ButtonType oui = new ButtonType("Confirmer");
        ButtonType non = new ButtonType("Annuler");
        delete.getButtonTypes().clear();
        delete.getButtonTypes().addAll(oui, non);
        Optional<ButtonType> result = delete.showAndWait();
        if (result.get() == oui) {
            reponseService.supprimer(r);
        }
        editMode = false;
    }

   private Group editMessage(reponse rep) {
        Group g = new Group();
        TextArea message = new TextArea();
        message.setText(rep.getMessage());
        Text description = new Text(rep.getMessage());
        int textW = (int) description.getLayoutBounds().getWidth();
        int textH = (int) description.getLayoutBounds().getHeight();
        int w = 200;
        int h = 50;
        Rectangle rect = new Rectangle();
        rect.setWidth(w);
        rect.setHeight(h);
        rect.setArcHeight(30);
        rect.setArcWidth(30);
        rect.setFill(Color.rgb(0, 126, 229));
        message.setTranslateX(16);
        message.setTranslateY(14);
        message.setPrefSize(w, h);
        message.setText(description.getText());
        message.setFont(Font.font("Arial", 15));
        Button save = new Button();
        save.setStyle("-fx-background-color: transparent;");
        FontAwesomeIconView s = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
        s.getStyleClass().add("saveButton");
        s.autosize();
        save.setGraphic(s);
        save.setTranslateX(w - ((int) s.getLayoutBounds().getWidth()+20));
        save.setTranslateY(textH + 10 * 2);
        save.setOnAction(value -> {
            if (ControlSaisie(message.getText().trim())) {
                Alert ConfirmEdit = new Alert(Alert.AlertType.INFORMATION);
                ConfirmEdit.setTitle("Confirmer la modification du Message");
                ConfirmEdit.setContentText("Ancien Message: \n" + rep.getMessage() + "\nNouveau Message:\n" + message.getText());
                ButtonType oui = new ButtonType("Confirmer");
                ButtonType non = new ButtonType("Annuler");
                ConfirmEdit.getButtonTypes().clear();
                ConfirmEdit.getButtonTypes().addAll(oui, non);
                Optional<ButtonType> result = ConfirmEdit.showAndWait();
                if (result.get() == oui) {
                    rep.setMessage(message.getText());
                    reponseService.modifier(rep);
                    
                }
            } else {
                if (EmptyStringDetected) {
                    Notifications.create().title("Eventili").text("Le Message ne peut pas etre vide").showError();

                } else if (badWordDetected) {
                    Notifications.create().title("Eventili").text("Un mot innaprorier a été detecter dans votre message").showError();
                }
                badWordDetected = false;
                EmptyStringDetected = false;
            }
            editMode = false;
            LoadMessages();
        });
        
        Button cancel = new Button();
        cancel.setStyle("-fx-background-color: transparent;");
        FontAwesomeIconView cancelIcon = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);
        cancelIcon.getStyleClass().add("cancelButton");
        cancelIcon.autosize();
        cancel.setGraphic(cancelIcon);
        cancel.setTranslateX(w - ((int) cancelIcon.getLayoutBounds().getWidth() + 8));
        cancel.setTranslateY(textH + 10 * 2);
        cancel.setOnAction(value -> {
            editMode = false;
            LoadMessages();
        });
        g.getChildren().addAll(rect, message, save, cancel);
        return g;
    }

    private void LoadMessages() {
        Messages.getChildren().clear();
        List<reponse> reps = reponseService.getAllAnswers(reclam);
        Messages.add(FirstMessage(), 2, 0);
        int rows = 1;
        RowConstraints row = new RowConstraints();
        row.setValignment(VPos.CENTER);
        Messages.getRowConstraints().add(row);
        Messages.setVgap(5);
        for (reponse rep : reps) {
            if (rep.getSender().getId_pers() == P.getId_pers()) {
                if (editMode && editRep == rep.getIdRep()) {
                    Messages.getRowConstraints().add(row);
                    Messages.add(editMessage(rep), 2, rows);
                } else {
                    Messages.getRowConstraints().add(row);
                    Messages.add(Messages(rep, true), 2, rows);
                }
            } else {
                Messages.getRowConstraints().add(row);
                Messages.add(Messages(rep, false), 1, rows);
            }
            rows++;
        }
    }

    private boolean ControlSaisie(String Message) {
        if (Message.equals("")) {
            EmptyStringDetected = true;
            return false;
        }
        try {
            URL git = new URL("https://raw.githubusercontent.com/bitri12/pidev_3A26_badWords/main/badwords.txt");
            URLConnection connection = git.openConnection();
            connection.setRequestProperty("X-Requested-With", "Curl");
            BufferedReader lecteur = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String comparateur;
            while ((comparateur = lecteur.readLine()) != null) {
                if (Message.toLowerCase().contains(comparateur)) {
                    badWordDetected = true;
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
