/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.company.entities.Personne;
import com.company.entities.Transactions;
import com.company.gui.Reclamations.Front.HomeReclamation;
import com.company.gui.SessionManager;
import com.company.services.ServiceTransaction;
import java.util.Date;
import java.util.List;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class transactionForm extends BaseForm {

    public static final String ACCOUNT_SID = "AC23ac3c900ad7d1daac0be7b4fd4848e8";
    public static final String AUTH_TOKEN = "90c789305ea78e014db15435039c55c0";
    Form cuurent;

    public transactionForm(Resources res) {
        super(new BorderLayout());

        Toolbar tb = new Toolbar(true);//sidebar

        cuurent = this;//initialisation
        setToolbar(tb);
        super.addSideMenu(res);//ajout du sidebar

        this.setScrollable(false);

        ajouttransaction(res); // call the method to show the transaction form
    }

    public void refresh(Resources res) {
        this.removeAll();
        ajouttransaction(res);
        this.revalidate();
        this.repaint();
    }

    public void ajouttransaction(Resources res) {
        Toolbar tbb = new Toolbar(true);//sidebar

        cuurent = this;//initialisation
        setToolbar(tbb);
        super.addSideMenu(res);//ajout du sidebar
        //remplissage du toolbar
        System.out.println("hello");
        Toolbar tb = new Toolbar(true);
        cuurent = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        getContentPane().setScrollVisible(false);
        // Create container for text fields
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button displayFormButton = new Button("afficher");
        displayFormButton.addActionListener(e -> createAndDisplayForm(res));
        add(BorderLayout.NORTH, displayFormButton);

        TextField valeurTransField = new TextField("", "Enter value");
        valeurTransField.setUIID("TextFieldBlack");
        valeurTransField.getAllStyles().setBorder(Border.createLineBorder(1, 0x000000));
        addStringValue("Valeur Transaction", valeurTransField, container);

        TextField montantTotField = new TextField("", "Enter total amount");
        montantTotField.setUIID("TextFieldBlack");
        montantTotField.getAllStyles().setBorder(Border.createLineBorder(1, 0x000000));
        addStringValue("Montant Total", montantTotField, container);

        ComboBox<String> devisField = new ComboBox<>();
        devisField.addItem("USD");
        devisField.addItem("EUR");
        devisField.setUIID("ComboBox");
        addStringValue("Devis", devisField, container);

        ComboBox<String> modeTransField = new ComboBox<>();
        modeTransField.addItem("Square");
        modeTransField.addItem("Stripe");
        modeTransField.setUIID("ComboBox");
        addStringValue("Mode Transaction", modeTransField, container);

        // Add container to form
        add(BorderLayout.CENTER, container);

//----------------------------- click button evednt
        Button btnajouter = new Button("ajouter");

        btnajouter.addActionListener((e) -> {
            try {

                int id_trans = -1;
                float valeur_trans = -1;
                float montant_tot = -1;
                String devis = (String) devisField.getSelectedItem();
                // String date_trans = dateTransField.getText();
                String mode_trans = (String) modeTransField.getSelectedItem();

                if (valeurTransField.getText().equals("")) {
                    throw new NumberFormatException("Valeur Transaction field cannot be empty.");
                } else {
                    valeur_trans = Float.parseFloat(valeurTransField.getText());
                    if (valeur_trans < 0) {
                        throw new NumberFormatException("Valeur Transaction must be a positive number.");
                    }
                }

                // Validate Montant Total field
                if (montantTotField.getText().equals("")) {
                    throw new NumberFormatException("Montant Total field cannot be empty.");
                } else {
                    montant_tot = Float.parseFloat(montantTotField.getText());
                    if (montant_tot < 0) {
                        throw new NumberFormatException("Montant Total must be a positive number.");
                    }
                }

                // Validate Devis field
                if (devis.equals("")) {
                    throw new IllegalArgumentException("Devis field cannot be empty.");
                }

                // Validate Mode Transaction field
                if (mode_trans.equals("")) {
                    throw new IllegalArgumentException("Mode Transaction field cannot be empty.");
                }

                InfiniteProgress ip = new InfiniteProgress();//loading after verifiying the data inserted
                final Dialog iDialog = ip.showInfiniteBlocking();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Personne p = new Personne();//jointure sesssion
                p.setId_pers(SessionManager.getId());//jointure,
                Transactions tr = new Transactions(id_trans, valeur_trans, montant_tot, devis, format.format(new Date()), mode_trans, p);
                System.out.println("data transaction ==" + tr);

                //appel de la methode ajodzut de service transaction
                ServiceTransaction.getInstance().add(tr);
                //remove the loading
                iDialog.dispose();
                //actualisationdd
                refreshTheme();
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                String messageBody = "Achat confirmed. Montant totale: " + montant_tot;
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+21628899807"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        messageBody)
                        .create();

            } catch (NumberFormatException ex) {
                Dialog.show("Invalid input", ex.getMessage(), "Cancel", "OK");
            } catch (IllegalArgumentException ex) {
                Dialog.show("Invalid input", ex.getMessage(), "Cancel", "OK");
            }
        });

        addStringValue("", btnajouter, container);

        refreshTheme();

        revalidate();
        repaint();

    }

    public void createAndDisplayForm(Resources res) {
        Toolbar tbb = new Toolbar(true); // sidebar

        cuurent = this; // initialization
        setToolbar(tbb);
        addSideMenu(res); // ajout du sidebar

        //remplissage du toolbar
        Form newForm = new Form("Transactions");
        Button displayFormButton = new Button("Display Form");
        Button backButton = new Button("Back");
        newForm.add(backButton);
        backButton.addActionListener(e -> ajouttransaction(res));
        displayFormButton.addActionListener(e -> createAndDisplaycritere(res));
        // Define styles for titleLabel and transactionLabel components
        UIManager.getInstance().getComponentStyle("titleLabel").setAlignment(Component.CENTER);
        UIManager.getInstance().getComponentStyle("titleLabel").setBgColor(0x388e3c);
        UIManager.getInstance().getComponentStyle("titleLabel").setFgColor(0xffffff);
        UIManager.getInstance().getComponentStyle("titleLabel").setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        UIManager.getInstance().getComponentStyle("transactionLabel").setAlignment(Component.LEFT);
        UIManager.getInstance().getComponentStyle("transactionLabel").setBorder(Border.createEmpty());
        UIManager.getInstance().getComponentStyle("transactionLabel").setFgColor(0x212121);
        UIManager.getInstance().getComponentStyle("transactionLabel").setPaddingTop(5);
        UIManager.getInstance().getComponentStyle("transactionLabel").setPaddingBottom(5);

        // Retrieve transactions from service
        ServiceTransaction transactionService = new ServiceTransaction();
        List<Transactions> transactions = transactionService.findTransactions(SessionManager.getId());

        // Create UI design for displaying transactions
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        newForm.setLayout(boxLayout);

        // Add title label
        Label titleLabel = new Label("Transaction List");
        titleLabel.setUIID("titleLabel");
        newForm.add(titleLabel);

        // Add transactions
        newForm.add(new Label("--------------------------------------------------------"));
        for (Transactions transaction : transactions) {
            Container transactionContainer = new Container(new BorderLayout());
            Label idLabel = new Label("ID: " + transaction.getId_trans());
            Label valueLabel = new Label("Value: " + transaction.getValeur_trans());
            Label currencyLabel = new Label("Currency: " + transaction.getDevis());
            Label dateLabel = new Label("Date: " + transaction.getDate_trans());
            Label modeLabel = new Label("Mode: " + transaction.getMode_trans());
            Label totalLabel = new Label("Total: " + transaction.getMontant_tot());
            transactionContainer.add(BorderLayout.CENTER, BoxLayout.encloseY(idLabel, valueLabel, currencyLabel, dateLabel, modeLabel, totalLabel));
            transactionContainer.setUIID("transactionContainer");
            newForm.add(transactionContainer);
            newForm.add(new Label("--------------------------------------------------------"));
        }

        newForm.addComponent(displayFormButton);

        newForm.show();
    }

    public void createAndDisplaycritere(Resources res) {
        Toolbar tbb = new Toolbar(true);//sidebar

        cuurent = this;//initialisation
        setToolbar(tbb);
        super.addSideMenu(res);//ajout du sidebar
        //remplissage du toolbar
        // Add search criteria label and text field
        Form newForm = new Form("Transactions");
        Label criteriaLabel = new Label("Search by min value:");
        TextField criteriaTextField = new TextField();
        criteriaTextField.getUnselectedStyle().setFgColor(0x000000);
        UIManager.getInstance().getComponentStyle("titleLabel").setAlignment(Component.CENTER);
        UIManager.getInstance().getComponentStyle("titleLabel").setBgColor(0x388e3c);
        UIManager.getInstance().getComponentStyle("titleLabel").setFgColor(0xffffff);
        UIManager.getInstance().getComponentStyle("titleLabel").setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        UIManager.getInstance().getComponentStyle("transactionLabel").setAlignment(Component.LEFT);
        UIManager.getInstance().getComponentStyle("transactionLabel").setBorder(Border.createEmpty());
        UIManager.getInstance().getComponentStyle("transactionLabel").setFgColor(0x212121);
        UIManager.getInstance().getComponentStyle("transactionLabel").setPaddingTop(5);
        UIManager.getInstance().getComponentStyle("transactionLabel").setPaddingBottom(5);

        newForm.add(criteriaLabel);
        newForm.add(criteriaTextField);

        // Add search button
        Button searchButton = new Button("Search");
        newForm.add(searchButton);

//        Button menuButton = new Button("menu");
//        newForm.add(menuButton);
//        menuButton.addActionListener(e -> new HomeReclamation(res).show());//change to go to menu
        Button backButton = new Button("Menu");
        newForm.add(backButton);
        backButton.addActionListener(e -> new HomeReclamation(res).show());
        // Create UI design for displaying transactions
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        newForm.setLayout(boxLayout);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            // Retrieve transactions based on search criteria
            ServiceTransaction transactionService = new ServiceTransaction();
            List<Transactions> transactions = transactionService.findTransactionscritere(SessionManager.getId(), criteriaTextField.getText());//jointure

            // Clear existing components in the form
            newForm.removeAll();

            // Add title label
            Label titleLabel = new Label("Transaction List");
            titleLabel.setUIID("titleLabel");
            newForm.add(titleLabel);

            // Add search criteria components and search button back to the form
            newForm.add(criteriaLabel);
            newForm.add(criteriaTextField);
            newForm.add(searchButton);
            newForm.add(backButton);
            // Add transactions
            newForm.add(new Label("--------------------------------------------------------"));
            for (Transactions transaction : transactions) {
                Container transactionContainer = new Container(new BorderLayout());
                Label idLabel = new Label("ID: " + transaction.getId_trans());
                Label valueLabel = new Label("Value: " + transaction.getValeur_trans());
                Label currencyLabel = new Label("Currency: " + transaction.getDevis());
                Label dateLabel = new Label("Date: " + transaction.getDate_trans());
                Label modeLabel = new Label("Mode: " + transaction.getMode_trans());
                Label totalLabel = new Label("Total: " + transaction.getMontant_tot());
                transactionContainer.add(BorderLayout.CENTER, BoxLayout.encloseY(idLabel, valueLabel, currencyLabel, dateLabel, modeLabel, totalLabel));
                transactionContainer.setUIID("transactionContainer");
                newForm.add(transactionContainer);
                newForm.add(new Label("--------------------------------------------------------"));
            }

            newForm.revalidate();
        });

        newForm.show();
    }

    private void addStringValue(String s, Component v, Container container) {
        container.add(new Label(s, "PaddedLabel"));
        container.add(v);
    }
}
