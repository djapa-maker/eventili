/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
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
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.company.entities.Personne;
import com.company.services.ServicePersonne;
import java.util.Vector;

/**
 *
 * @author msi
 */
public class ProfilForm extends Form{
    
    public ProfilForm(Resources res) {
       super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
     Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        //Form previous = Display.getInstance().getCurrent();
        //tb.setBackCommand("", e -> previous.showBack());
        
        
        Button menuButton = new Button("");
   FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
    menuButton.addActionListener(e -> getToolbar().openSideMenu());
    tb.addComponentToSideMenu(menuButton);
  
    
      Button b = new Button("Accueil");
    //loginButton.setUIID("LoginButton");

   b.addActionListener(e -> {
       new ListService(res).show();
    });
    tb.addComponentToSideMenu(b);
    Button a = new Button("Mon profil");
    //loginButton.setUIID("LoginButton");

    a.addActionListener(e -> {
        new ProfilForm(res).show();
    });
    tb.addComponentToSideMenu(a);
    
    Button deconnection = new Button("Déconnexion");
    //loginButton.setUIID("LoginButton");
    deconnection.addActionListener(e -> {
         new LoginForm(res).show();
        SessionManager.pref.clearAll();
        Storage.getInstance().clearStorage();
        Storage.getInstance().clearCache();
    });
 tb.addComponentToSideMenu(deconnection);
    
        Personne user=ServicePersonne.getInstance().find(SessionManager.getId());
        
       TextField nomPers = new TextField(user.getNom_pers(), "nom");
         TextField prenomPers = new TextField(user.getPrenom_pers(), "prénom");
        TextField numTel = new TextField(user.getNum_tel(), "numéro");
        TextField login = new TextField(user.getEmail(), "E-Mail", 20, TextField.EMAILADDR) ;
        TextField rib = new TextField(user.getRib(), "rib");
        TextField adresse = new TextField(user.getAdresse(), "adresse");
       
        Vector<String> vectorRole;
        vectorRole = new Vector();
        vectorRole.add("partenaire");
        vectorRole.add("admin");
        
        ComboBox<String>role = new ComboBox<>(vectorRole);
        
        
        nomPers.getAllStyles().setMargin(LEFT, 0);
        login.getAllStyles().setMargin(LEFT, 0);
       prenomPers.getAllStyles().setMargin(LEFT, 0);
    numTel.getAllStyles().setMargin(LEFT, 0);
      Label nomPersIcon = new Label("", "TextField");
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label prenomPersIcon = new Label("", "TextField");
    Label numTelIcon = new Label("", "TextField");
    Label ribIcon = new Label("", "TextField");
    Label adresseIcon = new Label("", "TextField");
        
        nomPersIcon.getAllStyles().setMargin(RIGHT, 0);
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomPersIcon.getAllStyles().setMargin(RIGHT, 0);
    numTelIcon.getAllStyles().setMargin(RIGHT, 0);
    ribIcon.getAllStyles().setMargin(RIGHT, 0);
    adresseIcon.getAllStyles().setMargin(RIGHT, 0);
    
        FontImage.setMaterialIcon(nomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(prenomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
    FontImage.setMaterialIcon(numTelIcon, FontImage.MATERIAL_PHONE, 3);
    FontImage.setMaterialIcon(ribIcon, FontImage.MATERIAL_ACCOUNT_BALANCE, 3);
    FontImage.setMaterialIcon(adresseIcon, FontImage.MATERIAL_LOCATION_ON, 3);

         Button loginButton = new Button("Modifier");
        //loginButton.setUIID("LoginButton");
        
      
       
        
        loginButton.addActionListener(e -> {
            user.setId_pers(SessionManager.getId());
        user.setNom_pers(nomPers.getText());
        user.setPrenom_pers(prenomPers.getText());
        user.setNum_tel(numTel.getText());
        user.setEmail(login.getText());
             System.out.println("persone-----------------"+user);
            if(ServicePersonne.getInstance().modifierUser(user,user.getId_pers())){
                 System.out.println("persone-----------------"+user);
               Dialog.show("Succès","Utilisateur modifié","OK",null);
                new ListService(res).show();
            
            }
        });
        
       
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
                        welcome,
                spaceLabel,
                BorderLayout.center(nomPers).
                        add(BorderLayout.WEST, nomPersIcon),
                BorderLayout.center(prenomPers).
                        add(BorderLayout.WEST, prenomPersIcon),
                BorderLayout.center(numTel).
                        add(BorderLayout.WEST, numTelIcon),
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(rib).
                        add(BorderLayout.WEST, ribIcon),
                BorderLayout.center(adresse).
                        add(BorderLayout.WEST, adresseIcon),
                loginButton
        );
        
       add(BorderLayout.CENTER, titleCmp);
        
        titleCmp.setScrollableY(true);
        titleCmp.setScrollVisible(false);
      
    }
    
    

    
}
