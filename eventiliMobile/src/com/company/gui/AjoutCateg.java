/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.company.entities.EventCateg;
import com.company.entities.Services;
import com.company.services.ServiceEventCateg;
import com.company.services.ServiceService;

/**
 *
 * @author chaim
 */
public class AjoutCateg  extends BaseForm {
     Form current;

    public AjoutCateg(Resources res) {
        super(BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
             Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Code to execute when the back button is pressed
                new ListService(res).show();
                refreshTheme();//Actualisation

            }
        };
        Toolbar toolbar = getToolbar();
        toolbar.setBackCommand(backCommand);
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xd7dcff);
          
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

//        addTab(swipe, s1, res.getImage("jellocolor.jpg"), "", "", res);

        //
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        Label emptyLabel = new Label("");

        emptyLabel.getAllStyles().setMarginBottom(250);
        add(emptyLabel);
        TextField type = new TextField("", "Type :");
        type.setUIID("TextFieldBlack");
        Label label = new Label("Enter le type de la catégorie : ");
        label.getAllStyles().setFgColor(0x330033); // Set the foreground color to purple
        add(label);
        add(type);

        ServiceService.getInstance().AllServices();
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);

        //onclick button event 
        btnAjouter.addActionListener((e) -> {

            try {
                 Services TheChosenOne = new Services();
                if (type.getText().equals("")) {
                    Dialog.show("Merci de remplir le champs", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    //njibo iduser men session (current user)
                    EventCateg ec = new EventCateg(String.valueOf(type.getText()).toString());

                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceEventCateg.getInstance().addCategory(ec);

                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout

                    //ba3d ajout net3adaw lel ListREclamationForm
                    new ListCateg(res).show();

                    refreshTheme();//Actualisation

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x283252));
    }


    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        
    }
      
}
