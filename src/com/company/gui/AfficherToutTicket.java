package com.company.gui;


import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.company.entities.Ticket;
import com.company.services.TicketService;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.company.entities.Ticket;
import com.company.services.TicketService;

import java.util.ArrayList;

public class AfficherToutTicket extends Form {

    Form previous;

    public static Ticket currentTicket = null;
    public static AfficherToutTicket affT;
    TextField searchTF;
    ArrayList<Component> componentModels;


    public AfficherToutTicket(Form previous) {
        super("Tickets", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        affT = this;
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

        ArrayList<Ticket> listTickets = TicketService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher ticket par Prix");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Ticket ticket : listTickets) {
                if (String.valueOf(ticket.getPrix()).toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeTicketModel(ticket);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listTickets.size() > 0) {
            for (Ticket ticket : listTickets) {
                Component model = makeTicketModel(ticket);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
    }

    Label transactionLabel, nbTickLabel, prixLabel, statusLabel, evenementLabel;


    private Container makeModelWithoutButtons(Ticket ticket) {
        Container ticketModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ticketModel.setUIID("containerRounded");


        transactionLabel = new Label("Transaction : " + ticket.getTransaction());
        transactionLabel.setUIID("labelDefault");

        nbTickLabel = new Label("NbTick : " + ticket.getNbTick());
        nbTickLabel.setUIID("labelDefault");

        prixLabel = new Label("Prix : " + ticket.getPrix());
        prixLabel.setUIID("labelDefault");

        statusLabel = new Label("Status : " + ticket.getStatus());
        statusLabel.setUIID("labelDefault");

        evenementLabel = new Label("Evenement : " + ticket.getEvenement());
        evenementLabel.setUIID("labelDefault");

        transactionLabel = new Label("Transaction : " + ticket.getTransaction().getMode());
        transactionLabel.setUIID("labelDefault");

        evenementLabel = new Label("Evenement : " + ticket.getEvenement().getTitre());
        evenementLabel.setUIID("labelDefault");
        
        Label prixEventLabel = new Label("Prix evenement : " + ticket.getEvenement().getPrix());
        prixEventLabel.setUIID("labelDefault");


        ticketModel.addAll(

                transactionLabel, nbTickLabel, prixLabel, statusLabel, evenementLabel, prixEventLabel
        );

        return ticketModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeTicketModel(Ticket ticket) {

        Container ticketModel = makeModelWithoutButtons(ticket);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentTicket = ticket;
            new ModifierTicket(this).show();
        });
        
                   // Create a button for statistics
com.codename1.ui.Button statisticsButton = new com.codename1.ui.Button("Statistics");
statisticsButton.addActionListener(e -> {
    // Code to navigate to the statistics page
    StatisticsForm statisticsForm = new StatisticsForm(tickets);
    statisticsForm.show();
});

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce ticket ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = TicketService.getInstance().delete(ticket.getId());

                if (responseCode == 200) {
                    currentTicket = null;
                    dlg.dispose();
                    ticketModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du ticket. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        ticketModel.add(btnsContainer);

        return ticketModel;
    }
    
    
    

}