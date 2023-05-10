package com.company.gui;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.company.entities.Evenement;
import com.company.entities.Ticket;
import com.company.entities.Transaction;
import com.company.gui.AccueilFront;
import com.company.gui.AfficherToutEvenement;
import com.company.services.EvenementService;
import com.company.services.TicketService;
import com.company.services.TransactionService;
import com.company.utils.AlertUtils;

import java.util.ArrayList;

public class AjouterTicket extends Form {


    PickerComponent nbTickPicker;
    TextField statusTF;
    Label prixLabel;
    Label prixLabelEvenet;
    Label statusLabel;


    ArrayList<Transaction> listTransactions;
    PickerComponent transactionPC;
    Transaction selectedTransaction = null;
    ArrayList<Evenement> listEvenements;
    PickerComponent evenementPC;
    Evenement selectedEvenement = null;


    Button manageButton;

    Form previous;

    public AjouterTicket(Form previous, Evenement evenement) {
        super("Ajouter", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        this.selectedEvenement = evenement;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> AccueilFront.accueilForm.showBack());
    }

    private void addGUIs() {

        String[] transactionStrings;
        int transactionIndex;
        transactionPC = PickerComponent.createStrings("").label("Transaction");
        listTransactions = TransactionService.getInstance().getAll();
        transactionStrings = new String[listTransactions.size()];
        transactionIndex = 0;
        for (Transaction transaction : listTransactions) {
            transactionStrings[transactionIndex] = transaction.getMode();
            transactionIndex++;
        }
        if (listTransactions.size() > 0) {
            transactionPC.getPicker().setStrings(transactionStrings);
            transactionPC.getPicker().addActionListener(l -> selectedTransaction = listTransactions.get(transactionPC.getPicker().getSelectedStringIndex()));
        } else {
            transactionPC.getPicker().setStrings("");
        }

        nbTickPicker = PickerComponent.createStrings("1").label("Nombre de tickets : ");
        String[] nbStrings = new String[5];
        nbStrings[0] = "1";
        nbStrings[1] = "2";
        nbStrings[2] = "3";
        nbStrings[3] = "4";
        nbStrings[4] = "5";
        
        nbTickPicker.getPicker().setStrings(nbStrings);
        nbTickPicker.getPicker().setSelectedString("0");
        nbTickPicker.getPicker().addActionListener(l -> {
            selectedNbTicket = nbTickPicker.getPicker().getSelectedStringIndex();
            prixLabel.setText("Prix total : " + selectedNbTicket * selectedEvenement.getPrix());
            this.refreshTheme();
        });

        String[] evenementStrings;
        int evenementIndex;
        evenementPC = PickerComponent.createStrings("").label("Evenement");
        listEvenements = EvenementService.getInstance().getAll();
        evenementStrings = new String[listEvenements.size()];
        evenementIndex = 0;
        for (Evenement evenement : listEvenements) {
            evenementStrings[evenementIndex] = evenement.getTitre();
            evenementIndex++;
        }
        if (listEvenements.size() > 0) {
            evenementPC.getPicker().setStrings(evenementStrings);
            evenementPC.getPicker().addActionListener(l -> selectedEvenement = listEvenements.get(evenementPC.getPicker().getSelectedStringIndex()));
        } else {
            evenementPC.getPicker().setStrings("");
        }

        if (selectedEvenement != null) evenementPC.getPicker().setSelectedString(selectedEvenement.getTitre());

        prixLabelEvenet = new Label("");
        if (selectedEvenement != null) prixLabelEvenet = new Label("Prix event : " + selectedEvenement.getPrix());
        
        prixLabel = new Label("Prix total : 0");
        prixLabel.setUIID("labelDefault");

        statusLabel = new Label("Status : ");
        statusLabel.setUIID("labelDefault");
        statusTF = new TextField();
        statusTF.setHint("Tapez le status");

        

        manageButton = new Button("Acheter");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
prixLabelEvenet,

                nbTickPicker,

                transactionPC, evenementPC,

                prixLabel,

                manageButton
        );

        this.addAll(container);
    }

    int selectedNbTicket = 0;

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = TicketService.getInstance().add(
                        new Ticket(
                                selectedTransaction,
                                nbTickPicker.getPicker().getSelectedStringIndex(),
                                selectedNbTicket * selectedEvenement.getPrix(),
                                "active",
                                selectedEvenement
                        )
                );
                if (responseCode == 200) {
                    AfficherToutTicket.affT.refresh();
                    AlertUtils.makeNotification("Ticket ajouté avec succes");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de ticket. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        try {
            ((AfficherToutTicket) previous).refresh();
        } catch (ClassCastException ignored) {
            ((AfficherToutEvenement) previous).refresh();
        }
        AccueilFront.accueilForm.showBack();
    }

    private boolean controleDeSaisie() {


       


        if (selectedTransaction == null) {
            Dialog.show("Avertissement", "Veuillez choisir un transaction", new Command("Ok"));
            return false;
        }

        if (selectedEvenement == null) {
            Dialog.show("Avertissement", "Veuillez choisir un evenement", new Command("Ok"));
            return false;
        }


        return true;
    }
}