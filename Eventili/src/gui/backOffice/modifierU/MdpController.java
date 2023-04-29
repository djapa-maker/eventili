/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.modifierU;

import entities.Personne;
import gui.sigleton.singleton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class MdpController implements Initializable {
singleton data= singleton.getInstance();
    Personne p=data.getUser();
    @FXML
    private PasswordField txtMdp1;
    @FXML
    private PasswordField txtMdp2;
    @FXML
    private PasswordField txtMdp3;
    @FXML
    private Button btnmodifier;
    @FXML
    private Label txtMsg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifier(MouseEvent event) throws IOException {
        String anciern=txtMdp1.getText();
        String nv1=txtMdp2.getText();
         String nv2=txtMdp3.getText();
         PersonneService ps=new PersonneService();
         if(test(anciern,nv1,nv2)){
               p.setMdp(nv1);
              String hashed = BCrypt.hashpw(nv1, BCrypt.gensalt());
             p.setMdp(hashed);
             Personne p1=new Personne(p.getId_pers(),p.getNom_pers(), p.getPrenom_pers(), p.getNum_tel(), p.getEmail(),p.getMdp(),p.getAdresse(),p.getRib(),p.getRole());
        ps.modifier(p.getId_pers(), p1);
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sidebar/Administration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage) btnmodifier.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();*/
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Modification de mot de passe");
        a.setContentText("Mot de passe modifié avec succès");
        a.setHeaderText("Modfication réussie");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
           Stage stage1 = (Stage) btnmodifier.getScene().getWindow();
            stage1.close();
         }
    }
    }

    private boolean test(String anciern, String nv1, String nv2) {
        String bcryptHashString = p.getMdp();
        int x=0;
       if(nv1.compareTo("")==0 || nv2.compareTo("")==0 || anciern.compareTo("")==0){
            x++;
            txtMsg.setText("saisissez tous les champs");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
             }
       else{
        if (BCrypt.checkpw(anciern,bcryptHashString)){
             
             
            if(nv1.compareTo(nv2)!=0){
                
                x++;
                txtMsg.setText("les deux nouveau mot de passe ne sont pas identiques");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
            }
            else if(nv1.length() < 8){
                 txtMsg.setText("veuillez saisir un mdp long");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
              x++;
            }else if(!nv1.matches("(?=.*[0-9]).*")){
       txtMsg.setText("au moins un chiffre");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else if(!nv1.matches("(?=.*[a-zA-Z]).*")){
        txtMsg.setText("au moins une lettre");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
               else{
                   txtMsg.setText("");
              }
        }else{
            x++;
            txtMsg.setText("ancien pas correcte");
              txtMsg.setTextFill(Color.rgb(210, 39, 30));
        }}
          if(x==0)
                  return true;
              else
                  return false;
    }
    

}
