/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.company.services.ServicePersonne;
import java.util.Vector;

/**
 *
 * @author msi
 */
public class SigninForm extends Form{
   /* public SigninForm(Resources theme) {
       //setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenue, "),
                new Label("dans votre compte")
        );
        
        //getTitleArea().setUIID("Container");
        
        TextField nomPers = new TextField("", "nom");
         TextField prenomPers = new TextField("", "prénom");
        TextField numTel = new TextField("", "numéro");
        TextField login = new TextField("", "E-Mail", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD) ;
        Vector<String> vectorRole;
        vectorRole = new Vector();
        vectorRole.add("partenaire");
        vectorRole.add("admin");
        
        ComboBox<String>role = new ComboBox<>(vectorRole);
        
        
        nomPers.getAllStyles().setMargin(LEFT, 0);
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        prenomPers.getAllStyles().setMargin(LEFT, 0);
        numTel.getAllStyles().setMargin(LEFT, 0);
  
          Label nomPersIcon = new Label("", "TextField");
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label prenomPersIcon = new Label("", "TextField");
        Label numTelIcon = new Label("", "TextField");
    Label ribIcon = new Label("", "TextField");
    Label adresseIcon = new Label("", "TextField");
        
        nomPersIcon.getAllStyles().setMargin(RIGHT, 0);
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomPersIcon.getAllStyles().setMargin(RIGHT, 0);
    numTelIcon.getAllStyles().setMargin(RIGHT, 0);
    ribIcon.getAllStyles().setMargin(RIGHT, 0);
    adresseIcon.getAllStyles().setMargin(RIGHT, 0);
    
        FontImage.setMaterialIcon(nomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(prenomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
    FontImage.setMaterialIcon(numTelIcon, FontImage.MATERIAL_PHONE, 3);
    FontImage.setMaterialIcon(ribIcon, FontImage.MATERIAL_ACCOUNT_BALANCE, 3);
    FontImage.setMaterialIcon(adresseIcon, FontImage.MATERIAL_LOCATION_ON, 3);
        
        Button loginButton = new Button("S'inscrire");
        //loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            boolean contientChiffres = false;
            boolean contientChiffress = false;
            boolean contientCaractère = false;
             boolean contientCaractères = false;
         int test=0;

for (int i = 0; i < nomPers.getText().length(); i++) {
    char c = nomPers.getText().charAt(i);
    if (Character.isDigit(c)) {
        contientChiffres = true;
        break;
    }
}
for (int i = 0; i < numTel.getText().length(); i++) {
    char c = numTel.getText().charAt(i);
    if (!Character.isDigit(c)) {
        contientCaractère = true;
        break;
    }
}

          String prenom = prenomPers.getText();
for (int i = 0; i < prenom.length(); i++) {
    char c = prenom.charAt(i);
    if (Character.isDigit(c)) {
        contientChiffress = true;
        break;
    }
}
if (nomPers.getText().equals("") || prenomPers.getText().equals("") || login.getText().equals("")|| numTel.getText().equals("")|| password.getText().equals("")) {
 ToastBar.showErrorMessage("Erreur : champ du nom vide !");
 test++;
}
        
else if (contientChiffres) {
    ToastBar.showErrorMessage("Erreur : le nom ne doit pas contenir de chiffres !");
 test++;
}

         
else if (contientChiffress) {
    ToastBar.showErrorMessage("Erreur : le prénom ne doit pas contenir de chiffres !");
 test++;
}
else if(numTel.getText().length() != 8) {
    ToastBar.showErrorMessage("Erreur : numéro de téléphone invalide !"); 
 test++; 
}
else if (contientCaractère) {
    ToastBar.showErrorMessage("Erreur : numéro de téléphone invalide !");
 test++;
}

else if (login.getText().indexOf("@") < 0 || login.getText().indexOf(".") < 0 || login.getText().indexOf("@") > login.getText().lastIndexOf(".")) {
 
    ToastBar.showErrorMessage("Erreur : email invalide !");
 test++;
}
else if(password.getText().length() < 8) {
    ToastBar.showErrorMessage("Erreur : mot de passe court !"); 
 test++; 
}
else if (contientCaractères) {
    ToastBar.showErrorMessage("Erreur : rib invalide !");
 test++;
}

if( ServiceUser.getInstance().signin(nomPers,prenomPers, login, password, role, numTel, theme) && test==0)
       
  new LoginForm(theme).show();  

            
             
          
        });
        
        Button createNewAccount = new Button("Se connecter");
        //createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener(e -> new LoginForm(theme).show());
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(nomPers).
                        add(BorderLayout.WEST, nomPersIcon),
                BorderLayout.center(prenomPers).
                        add(BorderLayout.WEST, prenomPersIcon),
                BorderLayout.center(numTel).
                        add(BorderLayout.WEST, numTelIcon),
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                role
                
        );
        by.addComponent(loginButton);
        by.addComponent(createNewAccount);
       add(BorderLayout.CENTER, by);
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    
     }*/
    
    
    
    public SigninForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
       // setUIID("LoginForm");
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
       
       Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenu, ", "WelcomeWhite"),
                new Label("dans votre compte", "WelcomeBlue")
        );
        
        //getTitleArea().setUIID("Container");
        
         
        TextField nomPers = new TextField("", "nom");
         TextField prenomPers = new TextField("", "prénom");
        TextField numTel = new TextField("", "numéro");
        TextField login = new TextField("", "E-Mail", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD) ;
        TextField rib = new TextField("", "rib");
        TextField adresse = new TextField("", "adresse");
        Vector<String> vectorRole;
        vectorRole = new Vector();
        vectorRole.add("partenaire");
        vectorRole.add("admin");
        
        ComboBox<String>role = new ComboBox<>(vectorRole);
        
        
        nomPers.getAllStyles().setMargin(LEFT, 0);
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        prenomPers.getAllStyles().setMargin(LEFT, 0);
        numTel.getAllStyles().setMargin(LEFT, 0);
  rib.getAllStyles().setMargin(LEFT, 0);
    adresse.getAllStyles().setMargin(LEFT, 0);
    
          Label nomPersIcon = new Label("", "TextField");
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label prenomPersIcon = new Label("", "TextField");
        Label numTelIcon = new Label("", "TextField");
    Label ribIcon = new Label("", "TextField");
    Label adresseIcon = new Label("", "TextField");
        
        nomPersIcon.getAllStyles().setMargin(RIGHT, 0);
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomPersIcon.getAllStyles().setMargin(RIGHT, 0);
    numTelIcon.getAllStyles().setMargin(RIGHT, 0);
    ribIcon.getAllStyles().setMargin(RIGHT, 0);
    adresseIcon.getAllStyles().setMargin(RIGHT, 0);
    
        FontImage.setMaterialIcon(nomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(prenomPersIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
    FontImage.setMaterialIcon(numTelIcon, FontImage.MATERIAL_PHONE, 3);
    FontImage.setMaterialIcon(ribIcon, FontImage.MATERIAL_ACCOUNT_BALANCE, 3);
    FontImage.setMaterialIcon(adresseIcon, FontImage.MATERIAL_LOCATION_ON, 3);
        
        
        
        
        Button loginButton = new Button("S'inscrire");
       // loginButton.setUIID("LoginButton");
         loginButton.addActionListener(e -> {
            boolean contientChiffres = false;
            boolean contientChiffress = false;
            boolean contientCaractère = false;
             boolean contientCaractères = false;
         int test=0;

for (int i = 0; i < nomPers.getText().length(); i++) {
    char c = nomPers.getText().charAt(i);
    if (Character.isDigit(c)) {
        contientChiffres = true;
        break;
    }
}
for (int i = 0; i < numTel.getText().length(); i++) {
    char c = numTel.getText().charAt(i);
    if (!Character.isDigit(c)) {
        contientCaractère = true;
        break;
    }
}
for (int i = 0; i < rib.getText().length(); i++) {
    char c = rib.getText().charAt(i);
    if (!Character.isDigit(c)) {
        contientCaractères = true;
        break;
    }
}

          String prenom = prenomPers.getText();
for (int i = 0; i < prenom.length(); i++) {
    char c = prenom.charAt(i);
    if (Character.isDigit(c)) {
        contientChiffress = true;
        break;
    }
}
if (nomPers.getText().equals("") || prenomPers.getText().equals("") || login.getText().equals("")|| numTel.getText().equals("")|| password.getText().equals("")) {
 ToastBar.showErrorMessage("Erreur : champ du nom vide !");
 test++;
}
        
else if (contientChiffres) {
    ToastBar.showErrorMessage("Erreur : le nom ne doit pas contenir de chiffres !");
 test++;
}

         
else if (contientChiffress) {
    ToastBar.showErrorMessage("Erreur : le prénom ne doit pas contenir de chiffres !");
 test++;
}
else if(numTel.getText().length() != 8) {
    ToastBar.showErrorMessage("Erreur : numéro de téléphone invalide !"); 
 test++; 
}
else if (contientCaractère) {
    ToastBar.showErrorMessage("Erreur : numéro de téléphone invalide !");
 test++;
}

else if (login.getText().indexOf("@") < 0 || login.getText().indexOf(".") < 0 || login.getText().indexOf("@") > login.getText().lastIndexOf(".")) {
 
    ToastBar.showErrorMessage("Erreur : email invalide !");
 test++;
}
else if(password.getText().length() < 8) {
    ToastBar.showErrorMessage("Erreur : mot de passe court !"); 
 test++; 
}
else if (contientCaractères) {
    ToastBar.showErrorMessage("Erreur : rib invalide !");
 test++;
}else if(rib.getText().length() < 20) {
    int nb=20-rib.getText().length();
    ToastBar.showErrorMessage("Erreur : rib invalide il manque: "+nb+" chiffres !"); 
 test++; 
}
else if(rib.getText().length() > 20) {
    int nb=rib.getText().length()-20;
    ToastBar.showErrorMessage("Erreur : rib invalide il y a: "+nb+" chiffres de plus !"); 
  test++;
}

if( ServicePersonne.getInstance().signin(nomPers,prenomPers, login, password, role,rib, numTel,adresse, theme) && test==0)
       
  new LoginForm(theme).show();  

            
             
          
        });
         
         
       
        

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
              welcome,
                spaceLabel,
                BorderLayout.center(nomPers).
                        add(BorderLayout.WEST, nomPersIcon),
                BorderLayout.center(prenomPers).
                        add(BorderLayout.WEST, prenomPersIcon),
                BorderLayout.center(numTel).
                        add(BorderLayout.WEST, numTelIcon),
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                BorderLayout.center(rib).
                        add(BorderLayout.WEST, ribIcon),
                BorderLayout.center(adresse).
                        add(BorderLayout.WEST, adresseIcon),
                role,
                loginButton
                //createNewAccount
        );
        add(BorderLayout.CENTER, by);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
         
        ));
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
