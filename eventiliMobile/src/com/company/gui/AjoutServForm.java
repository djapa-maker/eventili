package com.company.gui;

/**
 *
 * @author hr
 */
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
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.company.entities.Services;
import com.company.services.ServiceService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AjoutServForm extends BaseForm {

    Form current;
//------------------------------------------------------------------------------    

    public AjoutServForm(Resources res) {
        super(BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
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
//------------------------------------------------------------------------------             
        Toolbar toolbar = getToolbar();
        toolbar.setBackCommand(backCommand);
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xd7dcff);
        getContentPane().setScrollVisible(false);
//------------------------------------------------------------------------------
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
//------------------------------------------------------------------------------
        Label emptyLabel = new Label("");
        emptyLabel.getAllStyles().setMarginBottom(250);
        add(emptyLabel);
//------------------------------------------------------------------------------ 
        TextField nom = new TextField("", "nom");
        nom.setUIID("TextFieldBlack");
        Label label = new Label("Nom du service : ");
        label.getAllStyles().setFgColor(0x330033);
        add(label);
        add(nom);
//------------------------------------------------------------------------------
        ServiceService.getInstance().AllServices();
//------------------------------------------------------------------------------
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        //onclick button event 
        btnAjouter.addActionListener((e) -> {
            try {
                if (nom.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Services s = new Services(String.valueOf(nom.getText()).toString());
                    //appelle methode ajouter mt3 service  bch nzido données ta3na fi base 
                    ServiceService.getInstance().addService(s);
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    //ba3d ajout net3adaw lel List
                    new ListService(res).show();
                    refreshTheme();//Actualisation
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
//------------------------------------------------------------------------------

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x283252));
    }
//------------------------------------------------------------------------------

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        Container page1
                = LayeredLayout.encloseIn(
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );
        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);
    }
//------------------------------------------------------------------------------
    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }
//------------------------------------------------------------------------------
    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);

    }

}