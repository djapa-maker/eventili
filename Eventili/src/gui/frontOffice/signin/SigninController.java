/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.signin;

import com.github.sarxos.webcam.Webcam;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import gui.frontOffice.sidebar.SideBarController;
import gui.sigleton.singleton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class SigninController implements Initializable {
Window stage;
File file;
    String filePhotoEnt = null;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMdp;
    @FXML
    private TextField txtAdresse;
    @FXML
    private Label txtImage;
    @FXML
    private TextField txtRib;
    @FXML
    private TextField txtPrenom;
    @FXML
    private ChoiceBox<String> txtRole;
    @FXML
    private TextField txtNom;
    @FXML
    private Button btnenregistrer;
singleton data= singleton.getInstance();
    @FXML
    private Label msgEmail;
    @FXML
    private Label msgPrenom;
    @FXML
    private Label msgNum;
    @FXML
    private Label msgNom;
    @FXML
    private Label msgMdp;
    @FXML
    private Label msgAdresse;
    @FXML
    private Label msgImage;
    @FXML
    private Label msgRib;
    @FXML
    private Label msgRole;
    @FXML
    private ImageView btnImage;
    @FXML
    private FontAwesomeIconView btntake;
      
       
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txtRole.getItems().add("organisateur");
        txtRole.getItems().add("admin");
        txtRole.getItems().add("partenaire");
    }    

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
   PersonneService ps=new PersonneService();
   /*if(test( nom, prenom, num, adresse, image, rib, email,mdp))
   {
       Personne p=new Personne(nom, prenom, num,email,mdp, adresse, image, rib,  role);
    ps.ajouter(p);
  data.setUser(p);
            if(data.getUser().getRole().compareTo("admin")!=0){
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sidebar/SideBar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnenregistrer.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();

            }
            if((data.getUser().getRole().compareTo("admin")==0)){
              FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../backOffice/sidebar/Administration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnenregistrer.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
  
            }*/
           
  //}
   
  
  
  
  
  if(ps.findbyemail(email) == null){
  if(test( nom, prenom, num, adresse, image, rib, email,mdp))
   {
     
                String hashed = BCrypt.hashpw(txtMdp.getText(), BCrypt.gensalt());
              
                
    
       Personne p=new Personne(nom, prenom, num,email,hashed, adresse, image, rib,  role);
       System.out.println(p);
    ps.ajouter(p);
  data.setUser(p);
            if(data.getUser().getRole().compareTo("admin")!=0){
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sidebar/SideBar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnenregistrer.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();

            }
            if((data.getUser().getRole().compareTo("admin")==0)){
              FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../backOffice/sidebar/Administration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnenregistrer.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
  
            }
   }
}
  if(ps.findbyemail(email) != null){
      msgEmail.setText("email existant");
     msgEmail.setTextFill(Color.RED);
  }
}

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\msi\\Bureau\\9raya\\moi 3ème\\pidev\\integration\\pidev\\Eventili\\src\\images"));
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
	ImageIO.write(webcam.getImage(), "JPG", new File("C:\\Users\\msi\\Bureau\\9raya\\moi 3ème\\pidev\\integration\\pidev\\Eventili\\src\\images\\" + filename));
	filePhotoEnt = filename;
        txtImage.setText(filePhotoEnt);
	webcam.close();
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
    
    
}
