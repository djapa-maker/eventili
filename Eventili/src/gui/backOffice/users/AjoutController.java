/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.users;

import entities.Personne;
import entities.imagepers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PersonneService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class AjoutController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private Button btnenregistrer;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMdp;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtRib;
    @FXML
    private ChoiceBox<String> txtRole;
    @FXML
    private TextField txtImage;
    @FXML
    private Label msgRole;
    @FXML
    private Label msgRib;
    @FXML
    private Label msgImage;
    @FXML
    private Label msgAdresse;
    @FXML
    private Label msgMdp;
    @FXML
    private Label msgEmail;
    @FXML
    private Label msgNum;
    @FXML
    private Label msgPrenom;
    @FXML
    private Label msgNom;
    File file;
    Stage stage;
    @FXML
    private Button insertimg;
    @FXML
    private Rectangle profileRectangle;
UserController C=new UserController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRole.getItems().add("organisateur");
        txtRole.getItems().add("admin");
        txtRole.getItems().add("partenaire");
         txtRib.setTextFormatter(textFormatter);
         txtNum.setTextFormatter(textFormatter1);
        txtImage.setVisible(false);
    }    

TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() >20) {
            return null;
        }
        return change;
    });
    TextFormatter<String> textFormatter1 = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() >8) {
            return null;
        }
        return change;
    });
boolean test(String nom,String prenom,String num,String adresse,String image,String rib,String email,String mdp){
      
    
    String role=txtRole.getValue();
    int x=0;
       if (nom.compareTo("")==0){
                  msgNom.setText("veuillez saisir un nom");
              msgNom.setTextFill(Color.rgb(210, 39, 30));
              x++;
              }else if (!nom.matches("^[^0-9]*$")){
                  msgNom.setText("nom incorrecte");
              msgNom.setTextFill(Color.rgb(210, 39, 30));
               x++;}
              else{
                   msgNom.setText("");
              }
              
              if (prenom.compareTo("")==0){
                  msgPrenom.setText("veuillez saisir un prenom");
              msgPrenom.setTextFill(Color.rgb(210, 39, 30));
               x++;
              }
              else if (!prenom.matches("^[^0-9]*$")){
                  msgPrenom.setText("prenom incorrecte");
              msgPrenom.setTextFill(Color.rgb(210, 39, 30));
               x++;
              }
              else{
                   msgPrenom.setText("");
              }
              
              if (num.compareTo("")==0){
                  msgNum.setText("veuillez saisir un num");
              msgNum.setTextFill(Color.rgb(210, 39, 30));
               x++;
              }
              else if (num.matches("^[^0-9]*$") || num.length()!=8 ){
                  msgNum.setText("num incorrecte");
              msgNum.setTextFill(Color.rgb(210, 39, 30));
               x++;
              }
              else{
                   msgNum.setText("");
              }
               String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        
        
              if (email.compareTo("")==0){
                  msgEmail.setText("veuillez saisir un email");
              msgEmail.setTextFill(Color.rgb(210, 39, 30));
               x++;} 
              else if(! matcher.matches()){
                  msgEmail.setText("email incorrecte");
                x++;}
              else{
                   msgEmail.setText("");
              }
              if (mdp.compareTo("")==0){
                  msgMdp.setText("veuillez saisir un mdp");
              msgMdp.setTextFill(Color.rgb(210, 39, 30));
               x++;} 
              else if(mdp.length() < 8){
        msgMdp.setText("veuillez saisir un mdp long");
              msgMdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else if(!mdp.matches("(?=.*[a-z]).*")){
        msgMdp.setText("au moins une lettre");
              msgMdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else if(!mdp.matches("(?=.*[0-9]).*")){
        msgMdp.setText("au moins un chiffre");
              msgMdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else{
                   msgMdp.setText("");
              }
              
              if (adresse.compareTo("")==0){
                  msgAdresse.setText("veuillez saisir une adresse");
              msgAdresse.setTextFill(Color.rgb(210, 39, 30));
               x++;}else{
                   msgAdresse.setText("");
              }
              
              if (image.compareTo("")==0){
                  msgImage.setText("veuillez saisir une image");
              msgImage.setTextFill(Color.rgb(210, 39, 30));
               x++;}else{
                   msgImage.setText("");
              }
              
              if (rib.compareTo("")==0){
                  msgRib.setText("veuillez saisir un rib");
              msgRib.setTextFill(Color.rgb(210, 39, 30));
               x++;}else if (rib.matches("^[^0-9]*$") ){
                  msgRib.setText("rib incorrecte");
              msgRib.setTextFill(Color.rgb(210, 39, 30));
               x++;}
               else if (rib.length()!=20 ){
                   int nb=20-rib.length();
                   if(nb>0)
                  msgRib.setText("ajoutez "+ nb +" autres chiffres");
                   else{
                        nb=-nb;
                      msgRib.setText(nb +" chiffres en excès"); 
                   }
              msgRib.setTextFill(Color.rgb(210, 39, 30));
               x++;}else{
                   msgRib.setText("");
              }
              
              if (txtRole.getValue()==null){
                  msgRole.setText("veuillez saisir un role");
              msgRole.setTextFill(Color.rgb(210, 39, 30));
               x++;}else{
                   msgRole.setText("");
              } 
              if(x==0)
                  return true;
              else
                  return false;
    }
public void getController(UserController u){
    C=u;
}
    @FXML
    private void ajouter(MouseEvent event) throws IOException, SQLException {
     String nom=txtNom.getText();
    String prenom=txtPrenom.getText();
    String num=txtNum.getText();
    String email=txtEmail.getText();
    String mdp=txtMdp.getText();
    String adresse=txtAdresse.getText();
    String image=txtImage.getText();
    String rib=txtRib.getText();
    String role=txtRole.getValue();
    boolean verified=false;
    LocalDateTime date= LocalDateTime.now();
    String token=" ";
   PersonneService ps=new PersonneService();
       // System.out.println(ps.findbyemail(email)); 
  if(ps.findbyemail(email) == null){
  if(test( nom, prenom, num, adresse, image, rib, email,mdp))
   {
     
                String hashed = BCrypt.hashpw(txtMdp.getText(), BCrypt.gensalt());
       Personne p=new Personne(nom, prenom, num,email,hashed, adresse, rib,  role,token, verified,date);
       //System.out.println(p);
    ps.ajouter(p);//ween
     Personne p1= ps.findbyemail(email);
     imagepers i=new imagepers(image,image, p1.getId_pers());
      System.out.println(i);
    ps.ajouterI(i);
    C.Refresh();
    Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Ajout");
        a.setContentText("Personne ajoutée avec succès");
        a.setHeaderText("Ajout réussie");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
           Stage stage1 = (Stage) btnenregistrer.getScene().getWindow();
            stage1.close();
    
   }
}
  
  
  
    }else if(ps.findbyemail(email) != null){
      msgEmail.setText("email existant");
     msgEmail.setTextFill(Color.RED);
  }  
    }

    @FXML
    private void importImage(ActionEvent event) throws FileNotFoundException {
        
        FileChooser fileChooser = new FileChooser(); 
         fileChooser.setInitialDirectory(new File("C:/xampp/htdocs/img"));
        fileChooser.setTitle("Sélectionnez un fichier PNG");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images PNG","*.png", "*.jpg", "*.bmp", "*.gif"));
        
        

        // Afficher la boîte de dialogue et obtenir le fichier sélectionné//
        File fichierSelectionne = fileChooser.showOpenDialog(stage);

        if (fichierSelectionne != null) {
            txtImage.setText(fichierSelectionne.getName());
              file = fichierSelectionne;
              
              
              txtImage.setText(fichierSelectionne.getName());
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+fichierSelectionne.getName()); 
        Image image = new Image(inputstream); 
        profileRectangle.setStroke(javafx.scene.paint.Color.ORANGE);
        profileRectangle.setFill(new ImagePattern(image)); 
        }
    }
    
}
