/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServicePersonne;

/**
 *
 * @author msi
 */
public class ChangermdpForm extends Form{
    TextField email;
     public ChangermdpForm(Resources res,String mail) {
    super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("LoginForm");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("LoginForm");
        
       
        
        
        
         email = new TextField("","Entrez le nouveau mot de passe",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        Button valider = new Button("Valider");
        valider.setUIID("LoginButton");
        
        Container content = BoxLayout.encloseY(
                BorderLayout.center(email),
                BorderLayout.center(valider)
        );
        
        content.setScrollableY(false);
        content.setScrollVisible(false);

        // Ajouter la boîte centrée au centre de la page
        add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseY(new Label("")),
                content,
                BoxLayout.encloseY(new Label(""))
        ));
        
        valider.requestFocus();
        
        valider.addActionListener(e -> {
            if(ServicePersonne.getInstance().changer(email.getText().toString(),mail))
            {
                 ToastBar.showErrorMessage("Succès : Votre mot de passe a été changé avec succès  !");
 
                new LoginForm(res).show();
            }
            else{
                 ToastBar.showErrorMessage("Erreur : mot de passe court !");
 
            }
           
            
            
        });
        
        
        
    }
}