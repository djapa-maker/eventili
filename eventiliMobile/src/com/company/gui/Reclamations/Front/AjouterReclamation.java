/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui.Reclamations.Front;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reclamation;
import com.mycompany.gui.HomeForm;
import com.mycompany.gui.LoginForm;
import com.mycompany.gui.ProfilForm;
import com.mycompany.gui.SessionManager;
import com.mycompany.services.ReclamationService;

/**
 *
 * @author bitri
 */
public class AjouterReclamation extends Form {
    public AjouterReclamation(Resources res){
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        Button menuButton = new Button("");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        tb.addComponentToSideMenu(menuButton);
        Button b = new Button("Accueil");
        b.addActionListener(e -> {
            new HomeForm(res).show();
        });
        tb.addComponentToSideMenu(b);
        Button a = new Button("Mon profil");
        a.addActionListener(e -> {
            new ProfilForm(res).show();
        });
        tb.addComponentToSideMenu(a);
        Button deconnection = new Button("Déconnexion");
        deconnection.addActionListener(e -> {
            new LoginForm(res).show();
            SessionManager.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
        });
        tb.addComponentToSideMenu(deconnection);
        Button ReclamationsButton = new Button("Reclamations");
        ReclamationsButton.addActionListener(l->{ new HomeReclamation(res).show();});
        tb.addComponentToSideMenu(ReclamationsButton);
        TextField Titre = new TextField("");
        Titre.setHint("Sujet de la Réclamations");
        Titre.setColumns(20);
        TextArea Description = new TextArea("");
        Description.setHint("Description de la Réclamation");
        Description.setColumns(20);
        Description.setRows(10);
        Button Ajouter = new Button("Ajouter");
        
        Ajouter.addActionListener(l->{
            ReclamationService.getInstance().addReclamation(new Reclamation(Description.getText().toString(),Titre.getText().toString(),SessionManager.getId()));
        });
        Container titleCmp = BoxLayout.encloseY(
                BorderLayout.center(Titre),
                BorderLayout.center(Description),
                Ajouter
        );
        add(BorderLayout.CENTER, titleCmp);
        
        titleCmp.setScrollableY(true);
        titleCmp.setScrollVisible(false);
    } 
}
