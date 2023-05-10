package com.company.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

public class Login extends Form {

    public static Form loginForm;

    public Login() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {


        Button frontendBtn = new Button("Acheter Votre ticket");
        frontendBtn.addActionListener(l -> new com.company.gui.AccueilFront(this).show());
        this.add(frontendBtn);


        Button backendBtn = new Button("Liste des Tickets");
        //backendBtn.addActionListener(l -> new com.company.gui.back.AccueilBack(this).show());

        this.add(backendBtn);
    }

}
