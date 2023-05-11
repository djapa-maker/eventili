/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.liveChat;

import entities.Event;
import entities.Personne;
import gui.sigleton.singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author othma
 */
public class LiveChatController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Event e;
    private Personne p;
    @FXML
    private Button Send;
    @FXML
    private Button exit;
    @FXML
    private TextArea MessageBox;
    @FXML
    private TextArea Messages;
    private boolean EmptyStringDetected = false;
    private boolean badWordDetected = false;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
   private singleton data = singleton.getInstance();
    private Personne P = data.getUser();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            socket = new Socket("localhost", 8000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String username = P.getNom_pers() + " " + P.getPrenom_pers();
            
            out.println(username);
            ReceiveMessagesTask receiveMessagesTask = new ReceiveMessagesTask(in, MessageBox);
            new Thread(receiveMessagesTask).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void SendFunction(ActionEvent event){
        String Message = Messages.getText();
        if(ControlSaisie(Message)){
            out.println(Message);
        } else if(badWordDetected){
            Alert A = new Alert(AlertType.ERROR);
            A.setTitle("Eventili");
            A.setHeaderText("Un Mot inapriorier a été detecter");
            A.show();
        }
       Messages.clear();
    }
    @FXML
    private void CloseFunction(ActionEvent event) {
          Stage stage = (Stage) exit.getScene().getWindow();
          stage.close();
          
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
class ReceiveMessagesTask implements Runnable {
    private BufferedReader in;
    private final TextArea messageArea;

    public ReceiveMessagesTask(BufferedReader in, TextArea messageArea) {
        this.in = in;
        this.messageArea = messageArea;
    }

    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String finalMessage = message;
                Platform.runLater(() -> {
                    messageArea.appendText(finalMessage + "\n");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
