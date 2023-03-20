/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.backOffice.GestionEvent.Ticket;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Ticket;
import gui.backOffice.Service.ListerService.ListerServiceController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.TicketService;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ItemSController implements Initializable {

    @FXML
    private Text nomeve;
    @FXML
    private Button supp;
    @FXML
    private HBox hboxsupp;
    @FXML
    private Button modif;
    @FXML
    private HBox hboxmodif;
    @FXML
    private Text nbT;
    @FXML
    private Text prixt;
    @FXML
    private Text stat;
    private int id_s;
private Ticket serv=new Ticket();
    TicketService ss=new TicketService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hboxsupp.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.TRASH,
                                "25px"));
        hboxmodif.getChildren()
                .addAll(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL,
                                "25px"));
    }    
    public void SetData(Ticket s) throws SQLException {
         //System.out.println(s);
        nomeve.setText(s.getTev().getTitle());
        nbT.setText(String.valueOf(s.getNb_tick()));
        prixt.setText(String.valueOf(s.getPrice()));
        stat.setText(s.getStatus());
        serv = s;
        id_s=s.getId_tick();
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException, IOException {
         Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Attention");
        a.setContentText("Est-vous sure de supprimer ce service ?");
        a.setHeaderText("Attention !");
        Optional<ButtonType> action = a.showAndWait();
        if (action.get() == ButtonType.OK) {
            ss.supprimerById(id_s);
            TicketController ls=new TicketController();
            ls.LoadData();
        }
    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException, SQLException {
        
          FXMLLoader addLoader = new FXMLLoader(getClass().getResource("modtick.fxml"));
        Parent loader = addLoader.load();
      ModtickController modifier = addLoader.getController();
        modifier.modifierData(serv, id_s);
        
      
        Stage Stage = new Stage();
        Stage.setScene(new Scene(loader));
        //--refresh
        Stage.showAndWait();
      //  li.refreshData();
    }
}
