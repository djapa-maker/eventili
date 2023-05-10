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
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
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
public class ModifierCateg extends BaseForm {
     Form current;

    public ModifierCateg(Resources res, EventCateg c) {
        super(BoxLayout.y());

        Toolbar tb = new Toolbar(true);

        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xd7dcff);
        Command backCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Code to execute when the back button is pressed
                new ListCateg(res).show();
                refreshTheme();//Actualisation

            }
        };
        Toolbar toolbar = getToolbar();
        toolbar.setBackCommand(backCommand);

        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

       // addTab(swipe, s1, res.getImage("jellocolor.jpg"), "", "", res);
        //
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();


        Label emptyLabel = new Label("");
        emptyLabel.getAllStyles().setMarginBottom(250);
        add(emptyLabel);
        TextField type = new TextField(c.getType(), "entrer le type de la catégorie");
        type.setUIID("TextFieldBlack");
        Label label = new Label("Type: ");
        label.getAllStyles().setFgColor(0x330033); // Set the foreground color to purple
        add(label);
        add(type);

        Button btnModifier = new Button("Modifier");
        Style btnStyle = btnModifier.getAllStyles();
        btnStyle.setBgColor(0xFF0000); 
        btnStyle.setFgColor(0xFFFFFF); // set foreground (text) color to white

        //Event onclick btnModifer
        btnModifier.addPointerPressedListener(l -> {
            try {
                if (type.getText().equals("")) {
                    Dialog.show("Veuillez remplir le champ", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    EventCateg Ec = new EventCateg(c.getId(), String.valueOf(type.getText()).toString());
                    ServiceEventCateg.getInstance().modifierCategory(Ec);
                    iDialog.dispose();
                    new ListCateg(res).show();
                    refreshTheme();//Actualisation
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListCateg(res).show();
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label();
        Container content = BoxLayout.encloseY(
                //l1, l2,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
        );

        add(content);
        show();

    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);

    }
}
