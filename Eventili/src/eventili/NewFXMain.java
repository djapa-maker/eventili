
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventili;

import entities.Personne;
import gui.sigleton.singleton;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author chaim
 */
public class NewFXMain extends Application {
     Stage Stage1;
   singleton data= singleton.getInstance();
    Personne p=data.getUser();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        if(p==null){
        try {
         FXMLLoader addLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            //FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../gui/frontOffice/sidebar/SideBar.fxml"));
      
        Parent Root = addLoader.load();
        primaryStage.setTitle("Authentification");
        primaryStage.setScene(new Scene(Root));
        primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
        else{
            
              if ( data.getUser().getRole().compareTo("admin")!=0) {
                        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../gui/frontOffice/sidebar/SideBar.fxml"));
        Parent Root = addLoader.load();
        primaryStage.setTitle("Eventili");
        primaryStage.setScene(new Scene(Root));
        primaryStage.show();
                    
                }
                if (data.getUser().getRole().compareTo("admin")==0) {
                    
                    
                         FXMLLoader addLoader = new FXMLLoader(getClass().getResource("../gui/backOffice/sidebar/Administration.fxml"));
        Parent Root = addLoader.load();
        primaryStage.setTitle("Eventili");
        primaryStage.setScene(new Scene(Root));
        primaryStage.show();
        
        
                  
                    
                }
        }
    }  
  
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
