/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventili;

import com.github.sarxos.webcam.Webcam;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import entities.imagepers;
import gui.sigleton.singleton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import org.mindrot.jbcrypt.BCrypt;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class FirstController implements Initializable {
Window stage;
File file;
    @FXML
    private ChoiceBox<String> txtRole;
    @FXML
    private VBox vbox;
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
    private TextField txtImage;
    @FXML
    private Button btninscrire;
    @FXML
    private Text gotologn;
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
     String filePhotoEnt = null;
singleton data= singleton.getInstance();
    @FXML
    private PasswordField txtMdp;
    @FXML
    private TextField txtMdpS;
    @FXML
    private FontAwesomeIconView eye;
    @FXML
    private FontAwesomeIconView eyeS;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRole.getItems().add("organisateur");
        txtRole.getItems().add("partenaire");
        txtRib.setTextFormatter(textFormatter);
        txtMdpS.setVisible(false);
        eyeS.setVisible(false);
        txtImage.setDisable(true);
         txtNum.setTextFormatter(textFormatter1);
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
                  msgEmail.setTextFill(Color.rgb(210, 39, 30));
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
              else if(!mdp.matches("(?=.*[0-9]).*")){
        msgMdp.setText("au moins un chiffre");
              msgMdp.setTextFill(Color.rgb(210, 39, 30));
               x++;
    }
              else if(!mdp.matches("(?=.*[a-zA-Z]).*")){
        msgMdp.setText("au moins une lettre");
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
    @FXML
    private void inscrire(MouseEvent event) throws IOException {
        
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
    String token=" ";
   LocalDateTime date= LocalDateTime.now(); 
   PersonneService ps=new PersonneService();
   String md;
         if (!txtMdp.isVisible()){
      md=txtMdpS.getText();
  }
  else{
      md=txtMdp.getText();
  }
   if(ps.findbyemail(email) == null){
  if(test( nom, prenom, num, adresse, image, rib, email,md))
   { 
                String hashed = BCrypt.hashpw(md, BCrypt.gensalt());
          
                
    
       Personne p=new Personne(nom, prenom, num,email,hashed, adresse, rib,  role,token,verified,date);
      
       System.out.println(p);
       
    ps.ajouter(p);
    Personne p1= ps.findbyemail(email);
     imagepers i=new imagepers(image,image, p1.getId_pers());
      System.out.println(i);
    ps.ajouterI(i);
  data.setUser(p);
  data.setImage(i);
            if(data.getUser().getRole().compareTo("admin")!=0){
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/frontOffice/sidebar/SideBar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btninscrire.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();

            }
           
   }
}
  if(ps.findbyemail(email) != null){
      msgEmail.setText("email existant");
     msgEmail.setTextFill(Color.rgb(210, 39, 30));
  }
    }

    @FXML
    private void afficherlogin(MouseEvent event) throws IOException {
        
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage) gotologn.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
    }
   private String code_random() {

	String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		+ "0123456789"
		+ "abcdefghijklmnopqrstuvxyz";

	// create StringBuffer size of AlphaNumericString 
	StringBuilder sb = new StringBuilder(4);

	for (int i = 0; i < 4; i++) {

	    // generate a random number between 
	    // 0 to AlphaNumericString variable length 
	    int index
		    = (int) (AlphaNumericString.length()
		    * Math.random());

	    // add Character one by one in end of sb 
	    sb.append(AlphaNumericString
		    .charAt(index));
	}

	return sb.toString();
    } 

    @FXML
    private void addImage(MouseEvent event) {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\xampp\\htdocs\\img"));
        fileChooser.setTitle("Sélectionnez un fichier PNG");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images PNG","*.png", "*.jpg", "*.bmp", "*.gif"));
        
        

        // Afficher la boîte de dialogue et obtenir le fichier sélectionné//
        File fichierSelectionne = fileChooser.showOpenDialog(stage);

        if (fichierSelectionne != null) {
            txtImage.setText(fichierSelectionne.getName());
              file = fichierSelectionne;
        }
    }

    @FXML
    private void takephoto(MouseEvent event) throws IOException {
         String code_random = code_random();

	Webcam webcam = Webcam.getDefault();
	webcam.open();
	String filename = "";
	filename = code_random + "_" + txtNom.getText() + ".jpeg";
	ImageIO.write(webcam.getImage(), "JPG", new File("C:\\xampp\\htdocs\\img\\" + filename));
	filePhotoEnt = filename;
        txtImage.setText(filePhotoEnt);
	webcam.close();
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
}
