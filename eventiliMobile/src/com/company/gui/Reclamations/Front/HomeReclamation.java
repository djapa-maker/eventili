/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui.Reclamations.Front;

import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.company.entities.Reclamation;
import com.company.gui.ListService;
import com.company.gui.LoginForm;
import com.company.gui.ProfilForm;
import com.company.gui.SessionManager;
import com.company.services.ReclamationService;
import java.util.ArrayList;

/**
 *
 * @author bitri
 */
public class HomeReclamation extends Form {
    public HomeReclamation(Resources res){
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
         getAllStyles().setBgColor(0xd7dcff);
        Button Ajouter = new Button("Ajouter");
        TextArea DynamicSearchBar = new TextArea("");
        DynamicSearchBar.setHint("Recherche Dynamique");
        DynamicSearchBar.setColumns(20);
        final ArrayList<Integer> ReclamList = new ArrayList<>();
        DynamicSearchBar.addActionListener(l -> {
            Display.getInstance().callSerially(() -> {
                ArrayList<Integer> result = ReclamationService.getInstance().getReclamationsBySearch(DynamicSearchBar.getText());
                ReclamList.clear();
                ReclamList.addAll(result);
                System.out.println(ReclamList);
            });
        });
        
        Ajouter.addActionListener(l->{
            new AjouterReclamation(res).show();
        });
        
        Container AjouterContainer = BoxLayout.encloseY();
        if(!SessionManager.getRole().equals("admin")){
            AjouterContainer.add(Ajouter);
        }
        AjouterContainer.add(DynamicSearchBar);
        AjouterContainer.getAllStyles().setMargin(TOP, 250);
        add(BorderLayout.NORTH, AjouterContainer);
        AjouterContainer.setScrollableY(false);
        AjouterContainer.setScrollVisible(false);
        Container Liste = BoxLayout.encloseY();
        Container Cards = BoxLayout.encloseY();
        //
        if(SessionManager.getRole().equals("admin")){
        for(Reclamation R : ReclamationService.getInstance().getAllReclamations())
        {
         
        
        Container Card = BoxLayout.encloseY();
        Card.setName(Integer.toString(R.getId()));
        SpanLabel Titre = new SpanLabel("# " + Integer.toString(R.getId()) + " " + R.getTitre());
        Titre.getAllStyles().setAlignment(TOP,true);
        SpanLabel User = new SpanLabel("");
        User.getAllStyles().setAlignment(TOP,true);
        
        SpanLabel Description = new SpanLabel(R.getDescription());
        Description.getAllStyles().setAlignment(TOP,true);
        Card.add(Titre);
        if(SessionManager.getRole().equals("admin"))
            Card.add(User);
        Card.add(Description);
        Button Consulter = new Button("Consulter");
        Consulter.addActionListener(l->{
            new ConsulterReclamation(res,R).show();
        });
        
        Button Modifier = new Button("Modifier/Supprimer");
        Modifier.addActionListener(l->{
            new ModifierReclamation(res,R).show();
        });
        Card.add(Consulter);
        if(SessionManager.getRole().equals("admin"))
            Card.add(Modifier);
        
        Card.setScrollableY(false);
        Card.setScrollVisible(false);
        Cards.add(Card);
        }
        } else {
            for(Reclamation R : ReclamationService.getInstance().getAllReclamations(SessionManager.getId()))
        {
        Container Card = BoxLayout.encloseY();
        Card.setName(Integer.toString(R.getId()));
        SpanLabel Titre = new SpanLabel("# " + Integer.toString(R.getId()) + " " + R.getTitre());
        Titre.getAllStyles().setAlignment(TOP,true);
        SpanLabel User = new SpanLabel("");
        User.getAllStyles().setAlignment(TOP,true);
        
        SpanLabel Description = new SpanLabel(R.getDescription());
        Description.getAllStyles().setAlignment(TOP,true);
        Card.add(Titre);
        if(SessionManager.getRole().equals("admin"))
            Card.add(User);
        Card.add(Description);
        Button Consulter = new Button("Consulter");
        Consulter.addActionListener(l->{
            new ConsulterReclamation(res,R).show();
        });
        
        Button Modifier = new Button("Modifier/Supprimer");
        Modifier.addActionListener(l->{
            new ModifierReclamation(res,R).show();
        });
        Card.add(Consulter);
        if(SessionManager.getRole().equals("admin"))
            Card.add(Modifier);
        
        Card.setScrollableY(false);
        Card.setScrollVisible(false);
        Cards.add(Card);
        }
        }
        //
        
        Cards.setScrollableY(true);
        Cards.setScrollVisible(true);
        add(BorderLayout.CENTER,Cards);
    } 
}
