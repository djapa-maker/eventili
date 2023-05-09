/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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
import static com.codename1.ui.events.ActionEvent.Type.Command;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import com.company.entities.Services;

import com.company.services.ServiceService;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class ModifierService extends BaseForm {

    Form current;

    public ModifierService(Resources res, Services v) {
        super(BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
//        Image logo = res.getImage("logo.png");
//        Label logoLabel = new Label(logo);
//        add(logoLabel);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xd7dcff);
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

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0x2832520);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0x283252);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0x283252);
        g = selectedWalkthru.getGraphics();
        g.setColor(0x283252);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

//        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        Label emptyLabel = new Label("");
        emptyLabel.getAllStyles().setMarginBottom(250);
        add(emptyLabel);
        TextField nom = new TextField(v.getNom(), "entrer nom");
        nom.setUIID("TextFieldBlack");
        Label label = new Label("Nom: ");
        label.getAllStyles().setFgColor(0x330033); // Set the foreground color to purple
        add(label);
        add(nom);

        Button btnModifier = new Button("Modifier");
        Style btnStyle = btnModifier.getAllStyles();
        btnStyle.setBgColor(0xFF0000); // set background color to red
        btnStyle.setFgColor(0xFFFFFF); // set foreground (text) color to white
        //btnStyle.setFontSize(24); // set font size to 24 pixels

        //btnModifier.setUIID("Button");

        //Event onclick btnModifer
        btnModifier.addPointerPressedListener(l -> {
            try {
                if (nom.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    //njibo iduser men session (current user)
                    Services serv = new Services(v.getIdService(), String.valueOf(nom.getText()).toString());
                    ServiceService.getInstance().modifierService(serv);
                    iDialog.dispose();
                    new ListService(res).show();
                    refreshTheme();//Actualisation
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListService(res).show();
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

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);

    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);

    }
}
