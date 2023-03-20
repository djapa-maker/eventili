/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.client.DetailSousService;

import entities.Avis;
import entities.Personne;
import entities.Service;
import entities.SousServices;
import gui.backOffice.Service.ListerService.ListerServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.AvisService;
import services.ServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemAvisController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Text sous_service;
    @FXML
    private Label comment;
    @FXML
    private Label note;
    @FXML
    private ImageView img;
    @FXML
    private Label nomPers;
    private Avis av=new Avis();
    private String c;
    private float r;
    private Personne p;
    private SousServices s;
    private String i;
    private String n;
   // ListerServiceController li;
    AvisService as= new AvisService();
    ListerServiceController lst=new ListerServiceController();
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }
      public void SetData(Avis a) throws SQLException, FileNotFoundException {
       c=a.getComment();
       r=(float) a.getRating();
       p=a.getP();
       s=a.getS();
       i=a.getP().getImage();
       n=a.getP().getNom_pers();
           Image img1 = new Image("http://localhost/img/"+i);
        img.setImage(img1);
        nomPers.setText(n);
        note.setText(r+"/5");
        comment.setText(c);
        av=new Avis(c, r, s, p);   
    }
    
}
