package com.company.gui;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.company.entities.Evenement;
import com.company.gui.AjouterTicket;
import com.company.gui.ModifierTicket;
import com.company.services.EvenementService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfficherToutEvenement extends Form {

    Form previous;

    public static Evenement currentEvenement = null;


    public AfficherToutEvenement(Form previous) {
        super("Evenements", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {


        ArrayList<Evenement> listEvenements = EvenementService.getInstance().getAll();


        if (listEvenements.size() > 0) {
            for (Evenement evenement : listEvenements) {
                Component model = makeEvenementModel(evenement);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {

    }

    Label titreLabel, dateDebutLabel, dateFinLabel, descriptionEvLabel, typeLabel, visibiliteLabel, limiteparticipantLabel, prixLabel;


    private Container makeModelWithoutButtons(Evenement evenement) {
        Container evenementModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        evenementModel.setUIID("containerRounded");


        titreLabel = new Label("Titre : " + evenement.getTitre());
        titreLabel.setUIID("labelDefault");

        dateDebutLabel = new Label("DateDebut : " + new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateDebut()));
        dateDebutLabel.setUIID("labelDefault");

        dateFinLabel = new Label("DateFin : " + new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateFin()));
        dateFinLabel.setUIID("labelDefault");

        descriptionEvLabel = new Label("DescriptionEv : " + evenement.getDescriptionEv());
        descriptionEvLabel.setUIID("labelDefault");

        typeLabel = new Label("Type : " + evenement.getType());
        typeLabel.setUIID("labelDefault");

        visibiliteLabel = new Label("Visibilite : " + evenement.getVisibilite());
        visibiliteLabel.setUIID("labelDefault");

        limiteparticipantLabel = new Label("Limiteparticipant : " + evenement.getLimiteparticipant());
        limiteparticipantLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + evenement.getPrix());
        prixLabel.setUIID("labelDefault");


        evenementModel.addAll(

                titreLabel, dateDebutLabel, dateFinLabel, descriptionEvLabel, typeLabel, visibiliteLabel, limiteparticipantLabel, prixLabel
        );

        return evenementModel;
    }


    Container btnsContainer;

    private Component makeEvenementModel(Evenement evenement) {

        Container evenementModel = makeModelWithoutButtons(evenement);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        Button editBtn = new Button("Acheter un ticket");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> new AjouterTicket(this, evenement).show());

        btnsContainer.add(BorderLayout.CENTER, editBtn);


        evenementModel.add(btnsContainer);

        return evenementModel;
    }

}