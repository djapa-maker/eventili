package com.company.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.company.entities.Services;
import com.company.entities.Sousservices;
import com.company.services.ServiceSS;
import com.company.services.ServiceService;

import java.io.IOException;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ListSousservice extends BaseForm {

    Form current;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
//------------------------------------------------------------------------------

    public ListSousservice(Resources res) {
        super(BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        Toolbar tb = new Toolbar(true);//sidebar

        current = this;//initialisation
        setToolbar(tb);
        super.addSideMenu(res);//ajout du sidebar
        //remplissage du toolbar

//---------------------------------------------------------------------recherche        
        tb.addSearchCommand(e -> {
            //la variable recherché
            String text = (String) e.getSource();
            //resultat de recherche
            ArrayList<Sousservices> filteredsousserv = filterSS(text);
            //clear avant affichage
            this.removeAll();
            //contenu du container 
            Tabs swipe = new Tabs();
            Label s1 = new Label();
            Label s2 = new Label();
            swipe.setUIID("Container");
            swipe.getContentPane().setUIID("Container");
            swipe.hideTabs();
            Label emptyll = new Label("");
            emptyll.getAllStyles().setMarginBottom(250);
            add(emptyll);
            ButtonGroup bg = new ButtonGroup();
            int size = Display.getInstance().convertToPixels(1);
            Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");
//------------------------------------------------------------------------------
            for (Sousservices ss : filteredsousserv) {
                Image placeHolder = Image.createImage(120, 90);
                EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
                addButton(ss, res);
                //ScaleImageLabel image = new ScaleImageLabel(urlImage);
                Container containerImg = new Container();
            }
        });
//------------------------------------------------------------------------------
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xd7dcff);
        getContentPane().setScrollVisible(false);
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        Label emptyl = new Label("");
        emptyl.getAllStyles().setMarginBottom(250);
        add(emptyl);
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);

        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {

            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        Style fabStyle = fab.getAllStyles();
        fabStyle.setBgColor(0xff8601);
//------------------------------------------------------------------------------

        //Appel affichage methode
        ArrayList<Sousservices> list = ServiceSS.getInstance().affichageSS();
        for (Sousservices s : list) {
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            addButton(s, res);
            Container containerImg = new Container();
        }
    }
//------------------------------------------------------------------------------

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
//----------------------------------------------------------------------les divs

    private void addButton(Sousservices ss, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button();
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        Style cntStyle = cnt.getAllStyles();
        cntStyle.setBgColor(0xffffff);
        cntStyle.setBorder(Border.createLineBorder(2, 0xffffff));
        Label nom = new Label(ss.getNom(), "NewsTopLine2");
        Label desc = new Label(ss.getDescription(), "NewsTopLine2");
        nom.getAllStyles().setMarginLeft(30);
        nom.getAllStyles().setFgColor(0x27187f); // sets the foreground color of the label to white
        desc.getAllStyles().setMarginLeft(30);
        desc.getAllStyles().setFgColor(0xffffff);
//------------------------------------------------------------------------------
        Label emptyLabel = new Label("");
        emptyLabel.getAllStyles().setMarginBottom(70);
        emptyLabel.getAllStyles().setMarginLeft(50);
        emptyLabel.getAllStyles().setBgColor(0x758afd);
        createLineSeparator();
//------------------------------------------------------------------------------
        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0x758afd);

        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);

        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {

            Dialog dig = new Dialog("Suppression");

            if (dig.show("Suppression", "Voulez-vous supprimer ce sous service ?", "Annuler", "Oui")) {

                dig.dispose();

            } else {
                if (ServiceSS.getInstance().deleteSS((ss.getId())) ){
                    new ListSousservice(res).show();
                    refreshTheme();//Actualisation
                }

                dig.dispose();
            }
        });
//------------------------------------------------------------------------------
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(nom, lSupprimer),
                        BoxLayout.encloseY(desc)
                )
        );

        add(cnt);
    }
//------------------------------------------------------------------------------

    private ArrayList<Sousservices> filterSS(String searchText) {
        ArrayList<Sousservices> se = ServiceSS.getInstance().affichageSS();
        ArrayList<Sousservices> filteredsousserv = new ArrayList<>();

        if (searchText.isEmpty()) {
            // Si le champ de recherche est vide, retourner la liste complète des sujets
            return se;
        }
        for (Sousservices ser : se) {
            String nom = ser.getNom().toLowerCase();
            if (nom.contains(searchText.toLowerCase()) || nom.contains(searchText.toLowerCase())) {
                filteredsousserv.add(ser);
            }
        }
        return filteredsousserv;
    }

}
