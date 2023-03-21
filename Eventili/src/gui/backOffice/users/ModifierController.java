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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifierController implements Initializable {

    @FXML
    private Button btnenregistrer;
    @FXML
    private Label msgNom;
    @FXML
    private Button insertimg;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtAdresse;
    @FXML
    private ChoiceBox<String> txtRole;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMdp;
    @FXML
    private TextField txtRib;
    @FXML
    private Rectangle profileRectangle;
    @FXML
    private Label msgPrenom;
    @FXML
    private Label msgNum;
    @FXML
    private Label msgAdresse;
    @FXML
    private Label msgRib;
    @FXML
    private Label msgEmail;
    @FXML
    private Label msgRole;
    @FXML
    private Label msgImage;
    @FXML
    private TextField txtImage;
File file;
UserController us=new UserController();
    Stage stage;
    Personne p=new Personne();
    PersonneService ss=new PersonneService();
     String nom,prenom,num,email,mdp,adresse,image,rib,role;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txtRole.getItems().add("organisateur");
        txtRole.getItems().add("partenaire");
           txtRole.getItems().add("admin");
        txtImage.setVisible(false);
        txtMdp.setVisible(false);
        txtRib.setTextFormatter(textFormatter);
           txtNum.setTextFormatter(textFormatter1);
            txtEmail.setDisable(true);
    }    
    TextFormatter<String> textFormatter1 = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() >8) {
            return null;
        }
        return change;
    });
    TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.length() >20) {
            return null;
        }
        return change;
    });
    public void modifierData(Personne s, int id,imagepers i) throws SQLException, FileNotFoundException {
   
       nom=s.getNom_pers();
       prenom=s.getPrenom_pers();
       num=s.getNum_tel();
       email=s.getEmail();
       mdp=s.getMdp();
       adresse=s.getAdresse();
       image=i.getLast();
       rib=s.getRib();
       role=s.getRole();
       this.txtNom.setText(nom);
         this.txtPrenom.setText(prenom);
          this.txtNum.setText(num);
           this.txtEmail.setText(email);
            this.txtMdp.setText(mdp);
             this.txtAdresse.setText(adresse);
             this.txtImage.setText(i.getLast());
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+i.getLast()); 
        Image image = new Image(inputstream); 
        profileRectangle.setStroke(javafx.scene.paint.Color.ORANGE);
        profileRectangle.setFill(new ImagePattern(image)); 
               this.txtRib.setText(rib);
                this.txtRole.setValue(role);
                p=new Personne(nom, prenom, num, email, mdp, adresse, rib, role);
                //i=new imagepers(image, nom, id);
 
    }
public  void getController(UserController u){
    us=u;
}
    @FXML
    private void ajouter(MouseEvent event) throws SQLException, IOException {
        String pnom=txtNom.getText();
    String pprenom=txtPrenom.getText();
    String pnum=txtNum.getText();
    String pemail=txtEmail.getText();
    String pmdp=txtMdp.getText();
    String padresse=txtAdresse.getText();
    String pimage=txtImage.getText();
   String prib=txtRib.getText();
    String prole=txtRole.getValue();
    // String hashed = BCrypt.hashpw(txtMdp.getText(), BCrypt.gensalt());
        int id=ss.geIdbyemail(email);
        p=new Personne(id,pnom, pprenom, pnum, pemail,pmdp,padresse,prib,prole);
        
        if(test(pnom, pprenom, pnum, pemail,padresse,prib))
        
        {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Etes-vous sur de vouloir modifier cette personne ");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
           ss.modifier(id, p);
           imagepers im=new imagepers(pimage,pimage, id);
            System.out.println("lastt: "+im.getLast());
        ss.ajouterI(im);
         System.out.println("lastt2: "+im.getLast());
        ss.modifierI(id,pimage);
         System.out.println("lastt3: "+im.getLast());
        us.Refresh();
         Stage stage = (Stage) btnenregistrer.getScene().getWindow();
          stage.close();
        }
        }
        
    }

    @FXML
    private void importImage(ActionEvent event) throws FileNotFoundException {
     FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:/xampp/htdocs/img/"));
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
    
    
    boolean test(String nom, String prenom,String num,String email,String adresse,String rib){
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
                  msgEmail.setTextFill(Color.rgb(210, 39, 30)); 
                x++;}
              else{
                   msgEmail.setText("");
              }
              
              
              if (adresse.compareTo("")==0){
                  msgAdresse.setText("veuillez saisir une adresse");
              msgAdresse.setTextFill(Color.rgb(210, 39, 30));
               x++;}/*else if(!adresse.matches("\\d+\\s+([a-zA-Z]+\\s+)*[a-zA-Z]+,\\s*[A-Z]{2}\\s*\\d{5}(-\\d{4})?")){
                    msgAdresse.setText("adresse non valide");
               }*/else{
                   msgAdresse.setText("");
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
              
              
              if(x==0)
                  return true;
              else
                  return false;
    }
}
