package gui.frontOffice.client;

import entities.Event;
import entities.Personne;
import entities.Service;
import entities.ServiceReservation;
import entities.SousServices;
import gui.frontOffice.Devis.PDFGeneratorController;
import gui.frontOffice.client.ItemSousService.ItemSousServiceController;
import gui.sigleton.singleton;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import services.EventService;
import services.ServiceReservationService;
import services.ServiceService;
import services.SousServiceService;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListerServiceController implements Initializable {

    singleton data = singleton.getInstance();
    Personne p1 = data.getUser();
    @FXML
    private TextField searchbar;
    @FXML
    private Button searchbtn;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane Grid;
    @FXML
    private ScrollPane scroll1;
    ServiceService ss = new ServiceService();
    SousServiceService ss1 = new SousServiceService();
    ArrayList<Service> listServ = new ArrayList<>();
    ArrayList<SousServices> listSS = new ArrayList<>();
    ArrayList<Service> listD = new ArrayList<>();
    SousServiceService v = new SousServiceService();
    private int id = ss.getAll().get(0).getId_service();
    EventService es = new EventService();
    private String nomSS;
    private Event e;
    private int id1;
    ServiceReservationService srs = new ServiceReservationService();
    @FXML
    private Button btn;
//------------------------------------------------------------------------------
    @FXML
    private Button annulerbtn;

    public void Data(Event ev) throws SQLException, IOException {
        this.e = ev;
        LoadData2();
    }
//------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SearchDynamic();
        try {
            LoadData();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
//        scroll.setFitToHeight(true);
//        scroll.setFitToWidth(true);
    }
//------------------------------------------------------------------------------

    public void LoadData() throws SQLException, IOException {
        listServ = (ArrayList<Service>) ss.getAll();
        int column = 0;
        for (Service item : listServ) {
            scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-background-color: transparent;");
            //scroll.lookup(".scroll-bar:vertical").setStyle("-fx-background-color: transparent;");
            Button button = new Button(item.getNom_service());
            button.setStyle("-fx-background-color: #ff9901; -fx-background-radius: 50; -fx-padding: 10px 20px;-fx-text-fill: white;");
            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-background-color: white; -fx-text-fill:#ff9901; -fx-padding: 10px 20px; -fx-background-radius: 50; -fx-text-fill: #ff9901; ");
            });
            button.setOnMouseExited(e -> {
                button.setStyle("-fx-background-color: #ff9901; -fx-background-radius: 50; -fx-padding: 10px 20px; -fx-padding: 10px 20px; -fx-text-fill: white;");
            });
            button.setOnMouseClicked(e -> {
                button.setStyle("-fx-background-color: #ff9901; -fx-background-radius: 50; -fx-padding: 10px 20px; -fx-padding: 10px 20px; -fx-text-fill: white;");
            });
            button.setPrefWidth(800);
            button.getStyleClass().add("my-button");
            grid.setMargin(button, new Insets(10));
            grid.add(button, column, 1);
            column++;
            button.setOnAction(e -> {
                String name = button.getText();
                id = ss.findByNames(name).getId_service();
                //nomSS= ss.findByNames(name).getNom_service();
                try {
                    LoadData1();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                button.setStyle("-fx-background-color: #758BFD; -fx-background-radius: 50; -fx-padding: 10px 20px; -fx-text-fill: white;");
            });
        }
    }
//------------------------------------------------------------------------------    

    public void LoadData1() throws SQLException, IOException {
        scroll1.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-background-color: transparent;");
        listSS = (ArrayList<SousServices>) ss1.findSSByIdService(id);
        int column = 0;
        int row = 1;
        Grid.getChildren().clear();
        for (SousServices v : listSS) {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("../client/ItemSousService/itemSousService.fxml"));
            Pane pane = fxmlLoader1.load();
            ItemSousServiceController itemController = fxmlLoader1.getController();
            itemController.setData(v);
            if (e != null) {
                itemController.setDataEvent(e);
            }
            if (column == 4) {
                column = 0;
                row++;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }
//------------------------------------------------------------------------------

    public void LoadData2() throws SQLException, IOException {
        scroll1.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-background-color: transparent;");
        listD = (ArrayList<Service>) ss.getAll();
        int id1 = listD.get(0).getId_service();
        listSS = (ArrayList<SousServices>) ss1.findSSByIdService(id1);
        System.out.println(listSS);
        int column = 0;
        int row = 1;
        Grid.getChildren().clear();
        for (SousServices v : listSS) {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("../client/ItemSousService/itemSousService.fxml"));
            Pane pane = fxmlLoader1.load();
            ItemSousServiceController itemController = fxmlLoader1.getController();
            itemController.setData(v);
            if (e != null) {
                itemController.setDataEvent(e);
            }
            if (column == 4) {
                column = 0;
                row++;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }
//------------------------------------------------------------------------------

    public void LoadData3() throws SQLException, IOException {
        scroll1.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-background-color: transparent;");
        // listSS = (ArrayList<SousServices>) ss1.findSSByIdServiceAndName(id, nomSS);
        int column = 0;
        int row = 1;
        Grid.getChildren().clear();
        for (SousServices v : listSS) {
            FXMLLoader fxmlLoader2 = new FXMLLoader();
            fxmlLoader2.setLocation(getClass().getResource("../client/ItemSousService/itemSousService.fxml"));
            Pane pane = fxmlLoader2.load();
            ItemSousServiceController itemController = fxmlLoader2.getController();
            itemController.setData(v);
            if (e != null) {
                itemController.setDataEvent(e);
            }
            if (column == 4) {
                column = 0;
                row++;
            }
            Grid.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }
//------------------------------------------------------------------------------        

    private void SearchDynamic() {
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println(id);
                System.out.println(newValue);
                listSS = (ArrayList<SousServices>) v.findSSByIdServiceAndName(id, newValue);
                System.out.println(listSS);
                Grid.getChildren().clear();
                LoadData3();
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    public void generate() throws IOException, COSVisitorException {
        //-----------------------Creation------------------------------------------
        PDDocument document = new PDDocument();
        PDPage firstPage = new PDPage();
        document.addPage(firstPage);

        File imageFile = new File("C:/xampp/htdocs/img/logo4.jpg");
        PDXObjectImage img = new PDJpeg(document, new FileInputStream(imageFile));
        float width = 100;
        float height = 100;
        float scale = Math.min(width / img.getWidth(), height / img.getHeight());
        float scaledWidth = scale * img.getWidth();
        float scaledHeight = scale * img.getHeight();
        int pageHeight = (int) firstPage.getTrimBox().getHeight();
        int pageWidth = (int) firstPage.getTrimBox().getWidth();
        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
        contentStream.drawXObject(img, 510, 690, scaledWidth, scaledHeight);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 45);
        contentStream.moveTextPositionByAmount(30, 720);
        contentStream.drawString("Devis");
        contentStream.endText();

        contentStream.moveTo(30, 650);
        contentStream.lineTo(580, 650);
        contentStream.stroke();
        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(2);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.moveTextPositionByAmount(30, 620);
        contentStream.drawString("Nom de l'utilisateur : " + p1.getNom_pers() + " " + p1.getPrenom_pers());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.moveTextPositionByAmount(30, 595);
        contentStream.drawString("Numéro de téléphone : " + p1.getNum_tel());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.moveTextPositionByAmount(30, 570);
        contentStream.drawString("Adresse mail : " + p1.getEmail());
        contentStream.endText();

        contentStream.setLineWidth(1);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        ServiceReservationService rs = new ServiceReservationService();
        ServiceReservation SR = rs.findByIdEvent(e.getId_ev());
        ArrayList<SousServices> Lss = SR.getList();
        int initX = 50;
        int initY = pageHeight - 300;
        int cellHeight = 30;
        int cellWidth = 170;
        int colCount = 3;
        Color tableHeadC = new Color(80, 73, 150);
        Color tableBodyC = new Color(211, 209, 229);
        int rowCount = Lss.size() + 1;
        float Total = 0;
        //int rowCount = 10;
        for (int i = 1; i <= rowCount; i++) {
            for (int j = 1; j <= colCount; j++) {

                if (i == 1 && j == 1) {

                    contentStream.setNonStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.setStrokingColor(Color.WHITE);
                    contentStream.fill(1);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("Sn°");
                    contentStream.endText();
                    contentStream.setNonStrokingColor(tableHeadC);

                } else if (i == 1 && j == 2) {
                    contentStream.setNonStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.setStrokingColor(Color.WHITE);
                    contentStream.fill(1);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("Service");
                    contentStream.endText();
                } else if (i == 1 && j == 3) {
                    contentStream.setNonStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.setStrokingColor(Color.WHITE);
                    contentStream.fill(1);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("Prix");
                    contentStream.endText();
                } else if (j == 1) {
                    contentStream.setStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("" + (i - 1));
                    contentStream.endText();
                } else if (j == 2) {
                    contentStream.setStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("" + Lss.get(i - 2).getNom_serv());
                    contentStream.endText();
                } else if (j == 3) {
                    contentStream.setStrokingColor(tableHeadC);
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                    contentStream.stroke();

                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
                    contentStream.drawString("" + Lss.get(i - 2).getPrix_serv());
                    contentStream.endText();
                    Total += Lss.get(i - 2).getPrix_serv();
                } else {
                    System.out.println("error");
                }

                initX += cellWidth;
            }
            initX = 50;
            initY -= cellHeight;
        }
        contentStream.addRect(initX += 2 * cellWidth, initY, cellWidth, cellHeight);
        contentStream.setStrokingColor(Color.GRAY);

        contentStream.beginText();
        contentStream.moveTextPositionByAmount(initX + 10, initY + 10);
        contentStream.drawString("Total =" + Total + "DT");
        contentStream.endText();

        contentStream.stroke();

        contentStream.close();

        document.save("C:\\xampp\\htdocs\\img\\mypdf.pdf");
        System.out.println("PDF Created");

        //-----------------------Display------------------------------------------
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Devis/PDFGenerator.fxml"));
        Parent root = loader.load();
        PDFGeneratorController PDFController = loader.getController();
        PDFController.setIdEvent(e.getId_ev());
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Devis");
        stage.show();
        //document = PDDocument.load(new File("C:\\PDF\\mypdf.pdf"));
        BufferedImage image = firstPage.convertToImage(BufferedImage.TYPE_INT_RGB, 300);
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        PDFController.getPdfImageView().setImage(fxImage);
        PDFController.setPdDocument(document);

        stage.setOnCloseRequest(e -> {
            try {
                document.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void Devis(ActionEvent event) throws IOException, COSVisitorException {
        ServiceReservation rs = srs.findByIdEvent(e.getId_ev());
        if (rs.getId_res() == 0) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Attention");
                alert1.setHeaderText(null);
                alert1.setContentText("Vous devez séléctionner au moins un service");
                alert1.showAndWait();
        } else {
            generate();
        }

    }

    @FXML
    private void Annuler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler la création");
        alert.setHeaderText("les données que vous avez entrer seront perdues");
        alert.setContentText("Cliquer sur OK pour continuer ou bien Revenir pour continuer la création");

        ButtonType okButton = new ButtonType("OK");
        ButtonType revenirButton = new ButtonType("Revenir");
        alert.getButtonTypes().setAll(okButton, revenirButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                Stage stage = (Stage) annulerbtn.getScene().getWindow();
                stage.close();
                es.supprimer(e);
                ServiceReservation rs = srs.findByIdEvent(e.getId_ev());
                if (rs.getId_res() != 0) {
                    srs.supprimer(rs);
                }

            } else if (response == revenirButton) {
                alert.close();
            }
        });
    }
}
