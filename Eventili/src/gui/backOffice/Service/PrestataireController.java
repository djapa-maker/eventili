package gui.backOffice.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class PrestataireController implements Initializable {
    @FXML
    private BorderPane prestataire;
    @FXML
    private Button servicebtn;
    @FXML
    private Button ssbtn;
    @FXML
    private Button avisbtn;

//------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainPage();
    }    
    private void MainPage() {
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("ListerService/listerService.fxml"));
            prestataire.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @FXML
    private void ListerService(ActionEvent event) {
        
        try {
            BorderPane view = FXMLLoader.load(getClass().getResource("ListerService/listerService.fxml"));
            prestataire.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
//------------------------------------------------------------------------------
    @FXML
    private void ListerSousService(ActionEvent event) {
         try {
            BorderPane view = FXMLLoader.load(getClass().getResource("ListerSousService/ListerSousService.fxml"));
            prestataire.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ListerAvis(ActionEvent event) {
         try {
            BorderPane view = FXMLLoader.load(getClass().getResource("ListerAvis/ListerAV.fxml"));
            prestataire.setCenter(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
