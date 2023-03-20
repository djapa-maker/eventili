/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.login;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    @FXML
    private Button btnConnecter;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMdp;
    @FXML
    private Label Lmsg;
    @FXML
    private Label btnforgot;
 Stage Stage1;
    @FXML
    private FontAwesomeIconView eye;
    @FXML
    private TextField txtMdpS;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void connecter(MouseEvent event) throws IOException {
        
//        PersonneService per= new PersonneService();
//        int id=per.findByEmailMdp(txtEmail.getText(),txtMdp.getText());
//        if(id !=0){
//             Lmsg.setText("");
//             data.setUser(per.findById(id));
//             if(data.getUser().getRole().compareTo("admin")!=0){
//                 
//             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sidebar/SideBar.fxml"));
//            Parent root = (Parent) fxmlLoader.load();
//            
//            Stage stg=(Stage)btnConnecter.getScene().getWindow();
//            Scene s=new Scene(root);
//            stg.setTitle("Eventili");
//            stg.setScene(s);
//            stg.show();
//             }
//             if((data.getUser().getRole().compareTo("admin")==0)){
//                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../backOffice/sidebar/Administration.fxml"));
//            Parent root = (Parent) fxmlLoader.load();
//            
//            Stage stg=(Stage)btnConnecter.getScene().getWindow();
//            Scene s=new Scene(root);
//            stg.setTitle("Eventili");
//            stg.setScene(s);
//            stg.show();
//             }
//        }
//        else{
//             Lmsg.setText("Email ou mot de passe incorrecte");
//            Lmsg.setTextFill(Color.RED);
//            }
//
//        
//        
//        
        
        if (!txtEmail.getText().isEmpty() && !txtMdp.getText().isEmpty()) {
            PersonneService us = new PersonneService();
            Personne user = us.findbyemail(txtEmail.getText());
            if (user == null){
                Lmsg.setText("Email ou mot de passe incorrecte"); 
                Lmsg.setTextFill(Color.rgb(210, 39, 30));
            }else{
  // user pwd crypted
            String bcryptHashString = user.getMdp();
               
            if (BCrypt.checkpw(txtMdp.getText(),bcryptHashString)){
                int id_p=user.getId_pers();
                Personne p=us.findById(id_p);
                if(p!=null)
                {
             data.setUser(p);
             
                if (data.getUser().getRole().compareTo("admin")!=0) {
                    
                    Parent root = FXMLLoader.load(getClass().getResource("../sidebar/SideBar.fxml"));
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
                   Parent root = FXMLLoader.load(getClass().getResource("../../backOffice/sidebar/Administration.fxml"));
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
 
        }

    }

    @FXML
    private void forgot(MouseEvent event) throws IOException {
       /* try {
                     Parent root = FXMLLoader.load(getClass().getResource("../forgotMdp/forgotMdp.fxml"));
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

                } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                }
    }
    
    
    */
    
    
     FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../forgotMdp/forgotMdp.fxml"));
        Parent loader = addLoader.load();
        ForgotMdpController modifController = addLoader.getController();
        //modifController.modifierData(sev, id_service);
        //modifController.get(id_service);

        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        Stage.show();
}

    @FXML
    private void show(MouseEvent event) {
      String pass=txtMdp.getText();
      txtMdpS.setText(pass);
        
    }
}

   

    
    

    
