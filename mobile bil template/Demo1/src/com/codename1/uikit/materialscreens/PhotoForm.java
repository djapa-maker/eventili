/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.materialscreens;

import com.codename1.capture.Capture;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.SessionManager;
import com.mycompany.services.ServicePersonne;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author msi
 */
public class PhotoForm extends Form{
     public PhotoForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Insérer, ", "WelcomeWhite"),
                new Label("une photo", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
       Label limage=new Label();
        
        
         
        Button loginButton = new Button("Insérer");
        loginButton.setUIID("LoginButton");
       Button AddButton = new Button("Insérer");
        AddButton.setUIID("LoginButton");
      
        loginButton.addActionListener(e -> {
             String path=Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            

            if(path!=null){
                try {
                    Image img=Image.createImage(path);
                    limage.setIcon(img);
                    String imageName = new File(path).getName();
                    SessionManager.setPhoto(imageName);
                    this.revalidate();
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
            }
        });
         AddButton.addActionListener(e -> {
             ServicePersonne.getInstance().AddP(SessionManager.getPhoto(),SessionManager.getIdPers());
             new LoginForm(theme).show();
            
        });
      
        Container by = BoxLayout.encloseY(
                welcome,
                limage,
                loginButton,
                AddButton
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    
     }
}
