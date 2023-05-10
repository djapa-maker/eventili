    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.company.utils.Statics;

import com.company.entities.Video;

import com.company.services.ServiceVideo;
import java.util.ArrayList;

/**
 *
 * @author ahmed
 */
public class ListVideoForm extends BaseForm {

    Form current;

    public ListVideoForm(Resources res) {
        super(BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        Toolbar tb = new Toolbar(true);
        //logooo
        Image logo = res.getImage("logo.png");
        Label logoLabel = new Label(logo);
        add(logoLabel);
        current = this;
        // sidemenu
        addSideMenu(res);
        
        
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getAllStyles().setBgColor(0xB1A2D4);

        getContentPane().setScrollVisible(false);
        // Create the floating button
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

        // Set the button's position and size
        int x = 0;
        fab.bindFabToContainer(this, RIGHT, BOTTOM);

        // Add an action listener to the button
        fab.addActionListener(e -> {
            // Perform the desired action when the button is clicked
            //System.out.println("Floating button clicked!");
//            new AddBookForm(res).show();
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            AjoutVideoForm a = new AjoutVideoForm(res);
            a.show();
            refreshTheme();

        });

        tb.addSearchCommand(e -> {
            String text = (String) e.getSource();
            ArrayList<Video> filteredVideo = filterVideo(text);
//            System.out.println(filteredVideo);
            this.removeAll();
            
            Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("jellocolor.jpg"), "", "", res);

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

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Add Video", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Show Video", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Edit Video", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");

        

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        liste.setSelected(true);
        arrow.setVisible(false);
        
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
       

        //Appel affichage methode
//        ArrayList<Video> list = ServiceVideo.getInstance().affichageVideos();

        for (Video v : filteredVideo) {
            String imageUrl = "http://localhost/img/" + v.getImage_path();
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlImage = URLImage.createToStorage(enc, imageUrl, imageUrl, URLImage.RESIZE_SCALE);
            addButton(urlImage, v, res);

            ScaleImageLabel image = new ScaleImageLabel(urlImage);

            Container containerImg = new Container();

            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);}
            
        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("jellocolor.jpg"), "", "", res);

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

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Add Video", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Video overview", barGroup);
        liste.setUIID("SelectBar");
//        RadioButton partage = RadioButton.createToggle("Edit Video", barGroup);
//        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down.png"), "Container");

        liste.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            ListVideoForm a = new ListVideoForm(res);
            a.show();
            refreshTheme();
        });

        mesListes.addActionListener((e) -> {

            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            AjoutVideoForm a = new AjoutVideoForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, liste),
                FlowLayout.encloseBottom(arrow)
        ));

        liste.setSelected(true);
        arrow.setVisible(false);
//        addShowListener(e -> {
//            arrow.setVisible(true);
//            updateArrowPosition(partage, arrow);
//        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
//        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //Appel affichage methode
        ArrayList<Video> list = ServiceVideo.getInstance().affichageVideos();

        for (Video v : list) {
            String imageUrl = "http://localhost/img/" + v.getImage_path();
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlImage = URLImage.createToStorage(enc, imageUrl, imageUrl, URLImage.RESIZE_SCALE);
            addButton(urlImage, v, res);

            ScaleImageLabel image = new ScaleImageLabel(urlImage);

            Container containerImg = new Container();

            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }

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

    private void addButton(Image img, Video v, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label title = new Label("title: " + v.getTitle(), "NewsTopLine2");

        Label desc = new Label("Description : " + v.getDescription(), "NewsTopLine2");

//        Label videoPath = new Label("video path : " + v.getVideo_path(), "NewsTopLine2");
//        Label categ = new Label("categ : " + v.getCateg().getTitle(), "NewsTopLine2");

        // Label etatTxt2 = new Label("Categorie: Animal", "NewsTopLine2");
        Label emptyLabel = new Label("");

        emptyLabel.getAllStyles().setMarginBottom(70); // Set a margin to create space between the labels and the button

        createLineSeparator();

        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xffffff);

        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);

        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {

            Dialog dig = new Dialog("Suppression");

            if (dig.show("Suppression", "Vous voulez supprimer cette video ?", "Annuler", "Oui")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //n3ayto l suuprimer men service Reclamation
            if (ServiceVideo.getInstance().deleteVideo(v.getId())) {
                new ListVideoForm(res).show();
            }

        });

        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xffffff);

        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);

        lModifier.addPointerPressedListener(l -> {
            //            System.out.println("hello update");
            new ModifierVideoForm(res, v).show();
        });

//        Label lplay = new Label(" ");
//        lModifier.setUIID("NewsTopLine");
//        Style playStyle = new Style(lModifier.getUnselectedStyle());
//        modifierStyle.setFgColor(0xffffff);
//
//        FontImage pFontImage = FontImage.createMaterial(FontImage.MATERIAL_PLAY_ARROW, playStyle);
//        lplay.setIcon(pFontImage);
//        lplay.setTextPosition(LEFT);
//
//        lplay.addPointerPressedListener(l -> {
//            //            System.out.println("hello update");
//            VideoForm videoForm = new VideoForm(v);
//            videoForm.show();
//        });
        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(title),
                BoxLayout.encloseX(desc),
//                BoxLayout.encloseX(videoPath),
//                BoxLayout.encloseX(categ),
                BoxLayout.encloseX(lModifier, lSupprimer)));

        add(cnt);
    }
    
    
    private ArrayList<Video> filterVideo(String searchText) {
        ArrayList<Video> videos = ServiceVideo.getInstance().affichageVideos();
        ArrayList<Video> filteredVideo = new ArrayList<>();

        if (searchText.isEmpty()) {
            // Si le champ de recherche est vide, retourner la liste complète des sujets
            return videos;
        }

        for (Video video : videos) {
            String title = video.getTitle().toLowerCase();
            // String contenuSujet = music.getContenuSujet().toLowerCase();
            if (title.contains(searchText.toLowerCase()) || title.contains(searchText.toLowerCase())) {
                filteredVideo.add(video);
            }

        }
        return filteredVideo;
    }

}
