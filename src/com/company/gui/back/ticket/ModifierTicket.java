package com.company.gui.back.ticket;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.company.entities.Evenement;
import com.company.entities.Ticket;
import com.company.entities.Transaction;
import com.company.services.EvenementService;
import com.company.services.TicketService;
import com.company.services.TransactionService;
import com.company.utils.AlertUtils;

import java.util.ArrayList;

public class ModifierTicket extends Form {


    Ticket currentTicket;

    TextField nbTickTF;
    TextField prixTF;
    TextField statusTF;
    Label nbTickLabel;
    Label prixLabel;
    Label statusLabel;


    ArrayList<Transaction> listTransactions;
    PickerComponent transactionPC;
    Transaction selectedTransaction = null;
    ArrayList<Evenement> listEvenements;
    PickerComponent evenementPC;
    Evenement selectedEvenement = null;


    Button manageButton;

    Form previous;

    public ModifierTicket(Form previous) {
        super("Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentTicket = AfficherToutTicket.currentTicket;

        addGUIs();
        addActions();


        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
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


        nbTickLabel = new Label("NbTick : ");
        nbTickLabel.setUIID("labelDefault");
        nbTickTF = new TextField();
        nbTickTF.setHint("Tapez le nbTick");


        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");


        statusLabel = new Label("Status : ");
        statusLabel.setUIID("labelDefault");
        statusTF = new TextField();
        statusTF.setHint("Tapez le status");


        nbTickTF.setText(String.valueOf(currentTicket.getNbTick()));
        prixTF.setText(String.valueOf(currentTicket.getPrix()));
        statusTF.setText(currentTicket.getStatus());


        transactionPC.getPicker().setSelectedString(currentTicket.getTransaction().getMode());
        selectedTransaction = currentTicket.getTransaction();
        evenementPC.getPicker().setSelectedString(currentTicket.getEvenement().getTitre());
        selectedEvenement = currentTicket.getEvenement();


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(


                nbTickLabel, nbTickTF,
                prixLabel, prixTF,
                statusLabel, statusTF,

                transactionPC, evenementPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = TicketService.getInstance().edit(
                        new Ticket(
                                currentTicket.getId(),


                                selectedTransaction,
                                (int) Float.parseFloat(nbTickTF.getText()),
                                Float.parseFloat(prixTF.getText()),
                                statusTF.getText(),
                                selectedEvenement

                        )
                );
                if (responseCode == 200) {
                    AlertUtils.makeNotification("Ticket modifié avec succes");
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de ticket. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((AfficherToutTicket) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nbTickTF.getText().equals("")) {
            Dialog.show("Avertissement", "NbTick vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nbTickTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nbTickTF.getText() + " n'est pas un nombre valide (nbTick)", new Command("Ok"));
            return false;
        }


        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prix vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un nombre valide (prix)", new Command("Ok"));
            return false;
        }


        if (statusTF.getText().equals("")) {
            Dialog.show("Avertissement", "Status vide", new Command("Ok"));
            return false;
        }


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