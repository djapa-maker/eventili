/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui.Reclamations.Front;

import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
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
import com.mycompany.services.ReclamationService;
import com.mycompany.services.ReponseService;
import com.mycompany.services.ServicePersonne;

/**
 *
 * @author bitri
 */
public class ConsulterReclamation extends Form {
    public ConsulterReclamation(Resources res, Reclamation R){
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
        TextField Message = new TextField();
        Message.setHint("Message..");
        Message.setColumns(20);
        if(R.getStatus().equals("cloturer"))
            Message.setEnabled(false);
        Button SendRep = new Button("Répondre");
        if(R.getStatus().equals("cloturer"))
            SendRep.setEnabled(false);
        Button CloturerButton = new Button("Cloturé");
        CloturerButton.addActionListener(l->{
            ReclamationService.getInstance().cloturerRec(R);
            new ConsulterReclamation(res,R).show();
        });
        SendRep.addActionListener(l->{
            Reponse rep = new Reponse();
            rep.setPers(SessionManager.getId());
            rep.setMessage(Message.getText().toString());
            rep.setRec(R.getId());
            ReponseService.getInstance().ajouterRep(rep);
            new ConsulterReclamation(res,R).show();
        });
        Container Repss = BoxLayout.encloseY();
        for(Reponse reps : ReponseService.getInstance().getAllReponses(R.getId())){
            SpanLabel User = new SpanLabel("");
            User.setText(reps.getMessage());
            Button Modifier = new Button("Modifier/Supprimer");
            Modifier.addActionListener(l->{
                new ModifierReponse(res,reps,R).show();
            });
            Container Rep = BoxLayout.encloseY(
                    User,
                    Modifier
            );
            Repss.add(Rep);
        }
        
        Container titleCmp = BoxLayout.encloseY(
                BorderLayout.center(Repss)
        
        );
        if(!R.getStatus().equals("cloturer"))
            titleCmp.add(BorderLayout.south(CloturerButton));
        add(BorderLayout.CENTER, titleCmp);
        Container Rpon = BoxLayout.encloseY(
                BorderLayout.south(Message),
                BorderLayout.south(SendRep)
        );
        Rpon.setScrollableY(false);
        Rpon.setScrollVisible(false);
        add(BorderLayout.SOUTH,Rpon);
        titleCmp.setScrollableY(true);
        titleCmp.setScrollVisible(true);
    } 
}
