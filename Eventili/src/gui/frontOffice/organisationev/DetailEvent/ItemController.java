/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.organisationev.DetailEvent;

import entities.Event;
import entities.SousServices;
import gui.frontOffice.client.DetailSousService.DetailSousServiceController;
import gui.frontOffice.organisationev.DetailSousService.AvisSousServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class ItemController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Label NomSousService;

    private int id_serv;
    private Event e;
    @FXML
    private Pane pane;

    SousServiceService ss = new SousServiceService();
    SousServices serv = new SousServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // getData();

        pane.setOnMouseClicked(this::handlePaneClick);
    }

    public void setData(SousServices s) throws FileNotFoundException {
        //System.out.println(s);
        id_serv = s.getId_sousServ();
        //System.out.println("ouuuuuuuh"+s.getIcon());
//        Image image1 = new Image(getClass().getResourceAsStream(s.getIcon()));
        FileInputStream inputstream = new FileInputStream("C:/xampp/htdocs/img/"+s.getIcon()); 
        Image img1 = new Image(inputstream); 
        img.setImage(img1);
        NomSousService.setText(s.getNom_serv());
        serv = ss.findServiceById(id_serv);

    }

    public void setDataEvent(Event ev) {
        this.e = ev;
        //System.out.println("kkkkkkkkkkkkkkkkk" + this.e);

    }

    public void handlePaneClick(MouseEvent event) {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../DetailSousService/AvisSousService.fxml"));
        Parent Root;
        try {
            Root = addLoader.load();
            AvisSousServiceController Avis = addLoader.getController();
           // Avis.setDataEvent(e);
            Avis.setSS(serv);
            Stage Stage = new Stage();
            Stage.setScene(new Scene(Root));
            Stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
