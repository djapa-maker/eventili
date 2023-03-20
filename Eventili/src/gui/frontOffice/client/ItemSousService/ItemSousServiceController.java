package gui.frontOffice.client.ItemSousService;

import entities.Event;
import entities.SousServices;
import gui.frontOffice.client.DetailSousService.DetailSousServiceController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
 * @author HP
 */
public class ItemSousServiceController implements Initializable {
    @FXML
    private ImageView img;
    @FXML
    private Label NomSousService;
    @FXML
    private Pane pane;
    private int id_serv;
    private Event e;
    SousServiceService ss = new SousServiceService();
    SousServices serv = new SousServices();
//------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setOnMouseClicked(this::handlePaneClick);
    }
//------------------------------------------------------------------------------
    public void setData(SousServices s) throws FileNotFoundException {
        //System.out.println(s);
        id_serv = s.getId_sousServ();
//        Image image1 = new Image(getClass().getResourceAsStream(s.getIcon()));
            Image img1 = new Image("http://localhost/img/"+s.getIcon());
        img.setImage(img1);
        NomSousService.setText(s.getNom_serv());
        serv = ss.findServiceById(id_serv);
    }
//------------------------------------------------------------------------------
    public void setDataEvent(Event ev) {
        this.e = ev;
    }
//------------------------------------------------------------------------------
    public void handlePaneClick(MouseEvent event)  {
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../DetailSousService/DetailSousService.fxml"));
        Parent Root;
        try {
            Root = addLoader.load();
            DetailSousServiceController detail = addLoader.getController();
            detail.setDataEvent(e);
            detail.setData(serv);
            try {
              detail.setAvis(serv.getId_sousServ());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                }
            Stage Stage = new Stage();
            Stage.setScene(new Scene(Root));
            Stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
