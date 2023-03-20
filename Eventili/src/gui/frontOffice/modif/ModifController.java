/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.modif;

import com.github.sarxos.webcam.Webcam;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Personne;
import gui.frontOffice.login.LoginController;
import gui.frontOffice.sidebar.SideBarController;
import gui.sigleton.singleton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import services.PersonneService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifController implements Initializable {
    singleton data= singleton.getInstance();
      String filePhotoEnt = null;
    File file;
    Window stage;
    @FXML
    private Button btnenregistrer;
    @FXML
    private TextField txtNom;
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
    private ImageView btnImage;
    @FXML
    private FontAwesomeIconView btntake;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
               txtRole.getItems().add("organisateur");
        txtRole.getItems().add("partenaire");
    setUsername(data.getUser());
    txtImage.setEditable(false);
    } 
    
    public void setUsername(Personne user){
        this.txtNom.setText(user.getNom_pers());
         this.txtPrenom.setText(user.getPrenom_pers());
          this.txtNum.setText(user.getNum_tel());
           this.txtEmail.setText(user.getEmail());
            this.txtMdp.setText(user.getMdp());
             this.txtAdresse.setText(user.getAdresse());
              this.txtImage.setText(user.getImage());
               this.txtRib.setText(user.getRib());
                this.txtRole.setValue(user.getRole());
    }

    @FXML
    private void inscrire(MouseEvent event) throws IOException {
         PersonneService ps = new PersonneService();
        String nom=txtNom.getText();
    String prenom=txtPrenom.getText();
    String num=txtNum.getText();
    String email=txtEmail.getText();
    String mdp=txtMdp.getText();
    String adresse=txtAdresse.getText();
    String image=txtImage.getText();
    String rib=txtRib.getText();
    String role=txtRole.getValue();
   
         int intValue= data.getUser().getId_pers();
        Personne p=new Personne(intValue,nom, prenom, num, email,mdp,adresse,image,rib,role);
        
        ps.modifier(intValue, p);
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
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../backOffice/sidebar/Administration.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Stage stg=(Stage)btnenregistrer.getScene().getWindow();
            Scene s=new Scene(root);
            stg.setTitle("Eventili");
            stg.setScene(s);
            stg.show();
        }
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
    private void takephotp(MouseEvent event) throws IOException {
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
