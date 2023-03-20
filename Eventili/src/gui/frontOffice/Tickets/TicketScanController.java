/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.Tickets;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Event;
import entities.Ticket;
import gui.SingletonEvent.SingletonE;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class TicketScanController implements Initializable {
 SingletonE data= SingletonE.getInstance();
    @FXML
    private TextField datetf;
    @FXML
    private TextField prixtf;
    @FXML
    private Button qrbtn2;
    @FXML
    private TextField titres;
    @FXML
    private ImageView qrview;
 Event e= data.getE();
       Ticket T=data.getT();
    @FXML
    private Rectangle imgE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      prixtf.setDisable(true);
      datetf.setDisable(true);
      titres.setDisable(true);
     try {
         GetImg();
     } catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
     }
        titres.setText(e.getTitle());
        datetf.setText(e.getDate_debut().toString());
        prixtf.setText(String.valueOf(T.getPrice()) );
        afficherQR();
    }    

     private void GetImg() throws FileNotFoundException {
        
         String Image=e.getImage();
        FileInputStream inputstream = new FileInputStream("C:\\xampp\\htdocs\\img\\"+Image); 
        Image image = new Image(inputstream); 
        imgE.setStroke(javafx.scene.paint.Color.ORANGE);
        imgE.setFill(new ImagePattern(image)); 
    }
    
    void afficherQR(){
         try {
            // Generate the QR code
            String datef = datetf.getText();
            String prixf = prixtf.getText();
            String content = datef + "\n" + prixf;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);

            // Convert the bit matrix to an image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            Image image = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(encodedImage)));

            // Set the image view to display the generated QR code
            qrview.setImage(image);
            qrbtn2.setDisable(false);
            qrbtn2.setOnAction(event2 -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save QR Code Image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
                File file = fileChooser.showSaveDialog(qrbtn2.getScene().getWindow());
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
    
}
