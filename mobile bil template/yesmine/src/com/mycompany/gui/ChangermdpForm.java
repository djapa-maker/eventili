/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServicePersonne;

/**
 *
 * @author msi
 */
public class ChangermdpForm extends Form {
    TextField email;
    public ChangermdpForm(Resources theme,String mail) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
       // setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenu, ", "WelcomeWhite")
        );
        
        //getTitleArea().setUIID("Container");
        
       email = new TextField("","Entrez le nouveau mot de passe",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        Button loginButton = new Button("Valider");
       // loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
           if(ServicePersonne.getInstance().changer(email.getText().toString(),mail))
            {
                 ToastBar.showErrorMessage("Succès : Votre mot de passe a été changé avec succès  !");
 
                new LoginForm(theme).show();
            }
            else{
                 ToastBar.showErrorMessage("Erreur : mot de passe court !");
 
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
