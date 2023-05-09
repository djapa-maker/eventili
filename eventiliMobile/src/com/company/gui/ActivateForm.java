/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui;

import com.codename1.ui.Button;
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

import com.company.services.ServicePersonne;

/**
 *
 * @author msi
 */
public class ActivateForm extends Form{
    TextField email;
    public ActivateForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
       // setUIID("LoginForm");
       Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
       
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenu, ", "WelcomeWhite")
        );
        
        //getTitleArea().setUIID("Container");
        
        email = new TextField("","Entrez votre email",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        Button loginButton = new Button("Valider");
       // loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            
            
            
           if(ServicePersonne.getInstance().sendMail(email.getText().toString())){
               new CodeForm(theme,email.getText().toString()).show();
            }else{
                 Dialog.show("Echec:  ","Auncun utilisateur ayant cet email","OK",null);
             
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
                email,
                loginButton
                
        );
        add(BorderLayout.CENTER, by);
       
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
