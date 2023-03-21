/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventili;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import entities.imagepers;
import gui.frontOffice.forgotMdp.ForgotMdpController;
import gui.sigleton.singleton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class LoginController implements Initializable {
  singleton data= singleton.getInstance();
  Stage Stage1;
    @FXML
    private VBox vbox;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtMdp;
    @FXML
    private Button btnconnecter;
    @FXML
    private Text gotologn;
    @FXML
    private Label Lmsg;
    @FXML
    private TextField txtMdpS;
    @FXML
    private FontAwesomeIconView eye;
    @FXML
    private FontAwesomeIconView eyeS;
    @FXML
    private Text forgotMdp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txtMdpS.setVisible(false);
        eyeS.setVisible(false);
    }    

    @FXML
    private void connecter(MouseEvent event) throws IOException {
        String md;
         if (!txtMdp.isVisible()){
      md=txtMdpS.getText();
  }
  else{
      md=txtMdp.getText();
  }
         if (!txtEmail.getText().isEmpty() && !md.isEmpty()) {
            PersonneService us = new PersonneService();
            Personne user = us.findbyemail(txtEmail.getText());
            if (user == null){
                Lmsg.setText("Email ou mot de passe incorrecte"); 
                Lmsg.setTextFill(Color.rgb(210, 39, 30));
            }else{
  // user pwd crypted
 
            String bcryptHashString = user.getMdp();
               
            if (BCrypt.checkpw(md,bcryptHashString)){
                int id_p=user.getId_pers();
                Personne p=us.findById(id_p);
                imagepers image = us.findByIdI(id_p);
                if(p!=null)
                {
             data.setUser(p);
              data.setImage(image);
                if (data.getUser().getRole().compareTo("admin")!=0) {
                    
                    Parent root = FXMLLoader.load(getClass().getResource("../gui/frontOffice/sidebar/SideBar.fxml"));
                        Stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                                      root.setOnMousePressed(pressEvent -> {
                        root.setOnMouseDragged(dragEvent -> {
        Stage1.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
        Stage1.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
    });
});
                        Scene  scene = new Scene(root);
                        Stage1.setScene(scene);
                        Stage1.show();

                    
                }
                if (data.getUser().getRole().compareTo("admin")==0) {
                   Parent root = FXMLLoader.load(getClass().getResource("../gui/backOffice/sidebar/Administration.fxml"));
                        Stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                                      root.setOnMousePressed(pressEvent -> {
                        root.setOnMouseDragged(dragEvent -> {
        Stage1.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
        Stage1.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
    });
});
                        Scene  scene = new Scene(root);
                        Stage1.setScene(scene);
                        Stage1.show();
                    
                }
             
                }
                else {
                Lmsg.setText("Email ou mot de passe incorrecte"); 
                Lmsg.setTextFill(Color.rgb(210, 39, 30));
            }
            }else{
                 Lmsg.setText("Email ou mot de passe incorrecte"); 
                Lmsg.setTextFill(Color.rgb(210, 39, 30));
            }
        }}else {

            Lmsg.setText("Veuillez entrez l'email et le mdp");
 Lmsg.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    @FXML
    private void afficherlogin(MouseEvent event) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("First.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage) gotologn.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
    }

   @FXML
    private void show(MouseEvent event) {
        String pass=txtMdp.getText();
        txtMdpS.setText(pass);
        txtMdp.setVisible(false);
        txtMdpS.setVisible(true);
        eye.setVisible(false);
        eyeS.setVisible(true);
    }

    @FXML
    private void hide(MouseEvent event) {
        String pass=txtMdpS.getText();
        txtMdp.setText(pass);
        txtMdp.setVisible(true);
        txtMdpS.setVisible(false);
        eye.setVisible(true);
        eyeS.setVisible(false);
    }

    @FXML
    private void forgot(MouseEvent event) throws IOException {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../gui/frontOffice/forgotMdp/forgotMdp.fxml"));
        Parent loader = addLoader.load();
        ForgotMdpController modifController = addLoader.getController();
        //modifController.modifierData(sev, id_service);
        //modifController.get(id_service);

        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
    
}
