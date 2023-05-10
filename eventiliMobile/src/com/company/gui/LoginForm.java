package com.company.gui;


import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.company.entities.Personne;
import com.company.services.ReponseService;
import com.company.services.ServicePersonne;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author msi
 */
public class LoginForm extends Form{
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
       // setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenu, ", "WelcomeWhite"),
                new Label("dans votre compte", "WelcomeBlue")
        );
        
        //getTitleArea().setUIID("Container");
        
        TextField login = new TextField("", "E-Mail", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("Se connecter");
       // loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
          if( ServicePersonne.getInstance().login(login, password, theme)){
             Personne user= ServicePersonne.getInstance().find(SessionManager.getId());
             if(user!=null){
                // new BaseForm().show();
               new ListService(theme).show();
             }
          }
           
           
        });
       
        Button createNewAccount = new Button("Créer un nouveau compte");
        //createNewAccount.setUIID("CreateNewAccountButton");
         Button md = new Button("Mot de passe oublié?");
        //md.setUIID("CreateNewAccountButton");
         md.addActionListener(e -> {
         new ActivateForm(theme).show();
           
        });
        createNewAccount.addActionListener(e -> new SigninForm(theme).show());
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
          FlowLayout.encloseCenter(md)
        
        ));
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
