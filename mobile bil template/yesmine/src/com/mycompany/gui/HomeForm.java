/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Personne;
import com.mycompany.services.ServicePersonne;
import java.util.Vector;

/**
 *
 * @author msi
 */
public class HomeForm extends Form{
   
    public HomeForm(Resources res) {
    super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
    Toolbar tb = new Toolbar(true);
    setToolbar(tb);
    //Form previous = Display.getInstance().getCurrent();
    //tb.setBackCommand("", e -> previous.showBack());
    
    // Ajout du bouton pour ouvrir la Side Menu Bar
    Button menuButton = new Button("");
   FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
    menuButton.addActionListener(e -> getToolbar().openSideMenu());
    tb.addComponentToSideMenu(menuButton);
    
    
    Button loginButton = new Button("Mon profil");
    //loginButton.setUIID("LoginButton");

    loginButton.addActionListener(e -> {
        new ProfilForm(res).show();
    });
    tb.addComponentToSideMenu(loginButton);
    
    Button deconnection = new Button("Déconnexion");
    //loginButton.setUIID("LoginButton");
    deconnection.addActionListener(e -> {
         new LoginForm(res).show();
        SessionManager.pref.clearAll();
        Storage.getInstance().clearStorage();
        Storage.getInstance().clearCache();
    });
 tb.addComponentToSideMenu(deconnection);
    Label spaceLabel;
    if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
        spaceLabel = new Label();
    } else {
        spaceLabel = new Label(" ");
    }
    
    Container welcome = FlowLayout.encloseCenter(
            new Label("Bienvenue, ", "WelcomeWhite"),
            new Label("dans votre compte", "WelcomeBlue")
    );
    
    Container titleCmp = BoxLayout.encloseY(
            welcome
    );
    
    add(BorderLayout.CENTER, titleCmp);
    
    titleCmp.setScrollableY(true);
    titleCmp.setScrollVisible(false);
}


}
