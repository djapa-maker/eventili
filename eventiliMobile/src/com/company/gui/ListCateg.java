/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.company.entities.EventCateg;
import com.company.entities.Services;
import com.company.services.ServiceEventCateg;
import com.company.services.ServiceService;
import java.util.ArrayList;

/**
 *
 * @author chaim
 */
public class ListCateg extends BaseForm {
    
    Form current;
//------------------------------------------------------------------------------

    public ListCateg(Resources res) {
        super(BoxLayout.y()); 
        Toolbar tb = new Toolbar(true);
        
        current = this;
        setToolbar(tb);
        super.addSideMenu(res);

//---------------------------------------------------------------------recherche        
        tb.addSearchCommand(e -> {
            String text = (String) e.getSource();
            ArrayList<EventCateg> filteredCateg = filterCategory(text);
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
            swipe.addSelectionListener((i, ii) -> {
                if (!rbs[ii].isSelected()) {
                    rbs[ii].setSelected(true);
                }
            });

            Component.setSameSize(radioContainer, s1, s2);
            add(LayeredLayout.encloseIn(swipe, radioContainer));
            Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");

            for (EventCateg c : filteredCateg) {
                Image placeHolder = Image.createImage(120, 90);
                EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);

                addButton(c, res);

                //ScaleImageLabel image = new ScaleImageLabel(urlImage);
                Container containerImg = new Container();
            }
        });
        
        //--------------------------------------------------------------------------

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
        //ButtonGroup bg = new ButtonGroup();
//        int size = Display.getInstance().convertToPixels(1);
//        Image unselectedWalkthru = Image.createImage(size, size, 0x2832520);
//        Graphics g = unselectedWalkthru.getGraphics();
//        g.setColor(0x283252);
//        g.setAlpha(100);
//        g.setAntiAliased(true);
//        g.fillArc(0, 0, size, size, 0, 360);
//        Image selectedWalkthru = Image.createImage(size, size, 0x283252);
//        g = selectedWalkthru.getGraphics();
//        g.setColor(0x283252);
//        g.setAntiAliased(true);
//        g.fillArc(0, 0, size, size, 0, 360);
//        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
//        FlowLayout flow = new FlowLayout(CENTER);
//        flow.setValign(BOTTOM);
//        Container radioContainer = new Container(flow);
//        for (int iter = 0; iter < rbs.length; iter++) {
//            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
//            rbs[iter].setPressedIcon(selectedWalkthru);
//            rbs[iter].setUIID("Label");
//            radioContainer.add(rbs[iter]);
//        }
//        swipe.addSelectionListener((i, ii) -> {
//            if (!rbs[ii].isSelected()) {
//                rbs[ii].setSelected(true);
//            }
//        });

//        Component.setSameSize(radioContainer, s1, s2);
//        add(LayeredLayout.encloseIn(swipe, radioContainer));
        Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        Style fabStyle = fab.getAllStyles();
        fabStyle.setBgColor(0xff8601);
//------------------------------------------------------------------------------
        // Set the button's position and size
        int x = 0;
        fab.bindFabToContainer(this, RIGHT, BOTTOM);
        // Add an action listener to the button
        fab.addActionListener(e -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            AjoutCateg a = new AjoutCateg(res);
            a.show();
            refreshTheme();
        });
        //Appel affichage methode
        ArrayList<EventCateg> list = ServiceEventCateg.getInstance().AllCategories();
        System.out.println(list);
        for (EventCateg c : list) {
//            Image placeHolder = Image.createImage(120, 90);
//            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            addButton(c, res);
            Container containerImg = new Container();
        }
    }
//------------------------------------------------------------------------------

//    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
//        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
//        if (image.getHeight() < size) {
//            image = image.scaledHeight(size);
//        }
//        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
//        ScaleImageLabel imageScale = new ScaleImageLabel(image);
//        imageScale.setUIID("Container");
//        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        Label overLay = new Label("", "ImageOverlay");
//        Container page1
//                = LayeredLayout.encloseIn(
//                        imageScale,
//                        overLay,
//                        BorderLayout.south(
//                                BoxLayout.encloseY(
//                                        new SpanLabel(text, "LargeWhiteText"),
//                                        spacer
//                                )
//                        )
//                );
//
//        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);
//    }
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

    private void addButton(EventCateg c, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button();
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        Style cntStyle = cnt.getAllStyles();
        cntStyle.setBgColor(0xffffff);
        cntStyle.setBorder(Border.createLineBorder(2, 0xffffff));

       
        Label nom = new Label(c.getType(), "NewsTopLine2");
        nom.getAllStyles().setMarginLeft(30);
        nom.getAllStyles().setFgColor(0x27187f); // sets the foreground color of the label to white

        Label emptyLabel = new Label("");
        emptyLabel.getAllStyles().setMarginBottom(70);
        emptyLabel.getAllStyles().setMarginLeft(50);
        emptyLabel.getAllStyles().setBgColor(0x758afd);
        createLineSeparator();

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

            if (dig.show("Suppression", "Voulez-vous supprimer cette catégorie ?", "Annuler", "Oui")) {

                dig.dispose();

            } else {
                if (ServiceEventCateg.getInstance().deleteCategory(c.getId())) {
                    new ListCateg(res).show();
                    //refreshTheme();//Actualisation
                }

                dig.dispose();
            }
        });

        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xaeb8ff);

        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);

        lModifier.addPointerPressedListener(l -> {
            new ModifierCateg(res, c).show();
        });
        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(nom),
                BoxLayout.encloseX(lModifier, lSupprimer)));

        add(cnt);
    }
//------------------------------------------------------------------------------

    private ArrayList<EventCateg> filterCategory(String searchText) {
        ArrayList<EventCateg> cat = ServiceEventCateg.getInstance().AllCategories();
        ArrayList<EventCateg> filteredcat = new ArrayList<>();

        if (searchText.isEmpty()) {
            // Si le champ de recherche est vide, retourner la liste complète des catégories
            return cat;
        }
        for (EventCateg c : cat) {
            String type = c.getType().toLowerCase();
            if (type.contains(searchText.toLowerCase()) || type.contains(searchText.toLowerCase())) {
                filteredcat.add(c);
            }
        }
        return filteredcat;
    }
}
