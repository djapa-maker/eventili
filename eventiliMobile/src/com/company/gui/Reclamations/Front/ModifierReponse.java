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
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Reponse;
import com.mycompany.gui.HomeForm;
import com.mycompany.gui.LoginForm;
import com.mycompany.gui.ProfilForm;
import com.mycompany.gui.SessionManager;
import com.mycompany.services.ReponseService;

/**
 *
 * @author bitri
 */
public class ModifierReponse extends Form {
    public ModifierReponse(Resources res, Reponse R, Reclamation rec){
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
        TextArea Message = new TextArea(R.getMessage());
        Message.setColumns(20);
        Message.setHint("Message");
        Button Modifer = new Button("Modifier");
        Button Supprimer = new Button("Supprimer");
        Supprimer.addActionListener(l->{
            ReponseService.getInstance().supprimerRep(R);
            new ConsulterReclamation(res,rec).show();
        });
        Modifer.addActionListener(l->{
            R.setMessage(Message.getText().toString());
            R.setRec(rec.getId());
            ReponseService.getInstance().updateRep(R);
        });
        Container titleCmp = BoxLayout.encloseY(
                BorderLayout.center(Message),
                BorderLayout.center(Modifer),
                BorderLayout.center(Supprimer)
        );
        add(BorderLayout.CENTER, titleCmp);
        
        titleCmp.setScrollableY(false);
        titleCmp.setScrollVisible(false);
    } 
}
