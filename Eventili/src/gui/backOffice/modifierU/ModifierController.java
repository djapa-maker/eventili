/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.modifierU;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import gui.sigleton.singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifierController implements Initializable {

    @FXML
    private Circle ProfileCircle;
    @FXML
    private FontAwesomeIconView btnmodif;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtRib;
    @FXML
    private TextField txtRole;
    @FXML
    private PasswordField txtMdp;
    @FXML
    private Button btnmodifMdp;
    @FXML
    private Button btnmodifier;
 singleton data= singleton.getInstance();
      String filePhotoEnt = null;
    File file;
    Window stage;
    @FXML
    private TextField txtImage;
    @FXML
    private Label msgEmail;
    @FXML
    private Label msgNum;
    @FXML
    private Label msgPrenom;
    @FXML
    private Label msgNom;
    @FXML
    private Label msgAdresse;
    @FXML
    private Label msgRib;
    @FXML
    private Text txtB;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRole.setDisable(true);
        txtEmail.setDisable(true);
        txtImage.setVisible(false);
        txtMdp.setVisible(false);
        try {
            setUsername(data.getUser());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModifierController.class.getName()).log(Level.SEVERE, null, ex);
        }
          txtRib.setTextFormatter(textFormatter);
           txtNum.setTextFormatter(textFormatter1);
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
 public void setUsername(Personne user) throws FileNotFoundException{
     if(data.getUser().getRole().compareTo("admin")!=0){
         txtB.setText("Bonjour et bienvenue sur notre plateforme ! Nous sommes ravis que vous ayez choisi de rejoindre notre communauté. Nous espérons que vous apprécierez votre expérience parmi nous et que vous pourrez y trouver toutes les informations et ressources dont vous avez besoin.");
       //txtB.setTextColor(Color.parseColor("#300751"));
     } 
        this.txtNom.setText(user.getNom_pers());
         this.txtPrenom.setText(user.getPrenom_pers());
          this.txtNum.setText(user.getNum_tel());
           this.txtEmail.setText(user.getEmail());
             this.txtAdresse.setText(user.getAdresse());
               this.txtRib.setText(user.getRib());
                this.txtRole.setText(user.getRole());
             this.txtMdp.setText(user.getMdp());
             String Image=user.getImage();
             this.txtImage.setText(Image);
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+Image); 
        Image image = new Image(inputstream); 
        ProfileCircle.setStroke(javafx.scene.paint.Color.ORANGE);
        ProfileCircle.setFill(new ImagePattern(image)); 
        
             
 }
 public void refresh(Personne user) throws FileNotFoundException{
        this.txtNom.setText(user.getNom_pers());
         this.txtPrenom.setText(user.getPrenom_pers());
          this.txtNum.setText(user.getNum_tel());
           this.txtEmail.setText(user.getEmail());
             this.txtAdresse.setText(user.getAdresse());
               this.txtRib.setText(user.getRib());
                this.txtRole.setText(user.getRole());
             
             String Image=user.getImage();
             this.txtImage.setText(Image);
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+Image); 
        Image image = new Image(inputstream); 
        ProfileCircle.setStroke(javafx.scene.paint.Color.ORANGE);
        ProfileCircle.setFill(new ImagePattern(image)); 
        
             
 }
    @FXML
    private void modifier(MouseEvent event) throws IOException {
        
          PersonneService ps = new PersonneService();
        String nom=txtNom.getText();
    String prenom=txtPrenom.getText();
    String num=txtNum.getText();
    String email=txtEmail.getText();
    String mdp=txtMdp.getText();
    String adresse=txtAdresse.getText();
    String image=txtImage.getText();
    String rib=txtRib.getText();
   String role=txtRole.getText();
         int intValue= data.getUser().getId_pers();
        Personne p=new Personne(intValue,nom, prenom, num, email,mdp,adresse,image,rib,role);
        if(test(nom, prenom, num, email,adresse,rib)){
        ps.modifier(intValue, p);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Ajout");
        a.setContentText("Personne ajoutée avec succès");
        a.setHeaderText("Ajout réussie");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
           a.setOnCloseRequest(events -> {
    Stage stage1 = (Stage) a.getDialogPane().getScene().getWindow();
    stage1.close();
});
    
        data.setUser(p);
        if(data.getUser().getRole().compareTo("admin")!=0){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../frontOffice/sidebar/SideBar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnmodifier.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sidebar/Administration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnmodifier.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
        }
        }
    }
    }

    @FXML
    private void addImage(MouseEvent event) throws FileNotFoundException {
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
        ProfileCircle.setStroke(javafx.scene.paint.Color.ORANGE);
        ProfileCircle.setFill(new ImagePattern(image)); 
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

    @FXML
    private void changeMdp(MouseEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mdp.fxml"));
//            Parent root = (Parent) fxmlLoader.load();
//            
//            Stage stg=(Stage)btnmodifMdp.getScene().getWindow();
//            Scene s=new Scene(root);
//            stg.setTitle("Modifier mot de passe");
//            stg.setScene(s);
//            stg.show();
//            
//            
            
             FXMLLoader addLoader = new FXMLLoader(getClass().getResource("mdp.fxml"));
        Parent loader = addLoader.load();

        Stage Stage = new Stage();
        Stage.setTitle("Modifier mot de passe");
        Stage.setScene(new Scene(loader));
        Stage.show();
    }
}
