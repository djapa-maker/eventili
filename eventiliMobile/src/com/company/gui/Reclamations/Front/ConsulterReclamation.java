/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui.Reclamations.Front;

import com.codename1.components.SpanLabel;
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
import com.company.entities.Personne;
import com.company.entities.Reclamation;
import com.company.entities.Reponse;
import com.company.gui.ListService;
import com.company.gui.LoginForm;
import com.company.gui.ProfilForm;
import com.company.gui.SessionManager;
import com.company.services.ReclamationService;
import com.company.services.ReponseService;
import com.company.services.ServicePersonne;

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
          new ListService(res).show();
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
        TextArea Message = new TextArea();
        Message.setHint("Message..");
        Message.setColumns(20);
        if(R.getStatus().equals("cloturer"))
        {   Message.setEnabled(false);
            Message.setVisible(false);
        }
        Button SendRep = new Button("Répondre");
        if(R.getStatus().equals("cloturer"))
            SendRep.setEnabled(false);
        Button CloturerButton = new Button("Cloturé");
        CloturerButton.addActionListener(l->{
            ReclamationService.getInstance().cloturerRec(R);
            new ConsulterReclamation(res,R).show();
        });
                 getAllStyles().setBgColor(0xd7dcff);

        SendRep.addActionListener(l->{
            Reponse rep = new Reponse();
            rep.setPers(SessionManager.getId());
            rep.setMessage(Message.getText().toString());
            rep.setRec(R.getId());
            ReponseService.getInstance().ajouterRep(rep);
            new ConsulterReclamation(res,R).show();
        });
        Container Repss = BoxLayout.encloseY();
        Personne pers = ServicePersonne.getInstance().find(R.getPers());
        
        SpanLabel UserF = new SpanLabel(pers.getNom_pers() + " " + pers.getPrenom_pers() + ":");
        SpanLabel desc = new SpanLabel(R.getDescription());
        Container FirstMsg = BoxLayout.encloseY(
                UserF,
                desc
                
        );
        Repss.add(FirstMsg);
        for(Reponse reps : ReponseService.getInstance().getAllReponses(R.getId())){
            SpanLabel User = new SpanLabel("");
            Personne P = ServicePersonne.getInstance().find(reps.getPers());
            User.setText(P.getNom_pers() + " " + P.getPrenom_pers() + ":");
            SpanLabel Messagse = new SpanLabel(reps.getMessage());
            Button Modifier = new Button("Modifier/Supprimer");
            Modifier.addActionListener(l->{
                new ModifierReponse(res,reps,R).show();
            });
            Container Rep = BoxLayout.encloseY(
                    User,
                    Messagse,
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
