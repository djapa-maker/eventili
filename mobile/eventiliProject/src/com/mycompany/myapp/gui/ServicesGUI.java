/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Services;
import com.mycompany.myapp.entities.Sousservices;
import com.mycompany.myapp.services.ServiceService;

/**
 *
 * @author HP
 */
public class ServicesGUI extends Form{
    
        public ServicesGUI(Form previous) {
        setTitle("Ajouter un nouveau service :");
        setLayout(BoxLayout.y());
        
        TextField nom = new TextField("","nom du service");
        Button btnValider = new Button("Ajouter");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length()==0))
                    Dialog.show("Alert", "merci d'ajouter un nom ", new Command("OK"));
                else
                {
                    try {
                        Services s = new Services(nom.getText());
                        if( ServiceService.getInstance().addService(s))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
            }
        });
        
        addAll(nom,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
}
