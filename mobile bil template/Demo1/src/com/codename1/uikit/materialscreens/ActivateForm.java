/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author msi
 */
import com.mycompany.services.ServicePersonne;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class ActivateForm extends Form{
    TextField email;
    public ActivateForm(Resources res) {
    super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("LoginForm");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("LoginForm");
        
       
        
        
        
         email = new TextField("","Entrez votre email",20,TextField.ANY);
        email.setSingleLineTextArea(false);
        
        Button valider = new Button("Valider");
        valider.setUIID("LoginButton");
        
        Container content = BoxLayout.encloseY(
                BorderLayout.center(email),
                BorderLayout.center(valider)
        );
        
        content.setScrollableY(false);
        content.setScrollVisible(false);

        // Ajouter la boîte centrée au centre de la page
        add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseY(new Label("")),
                content,
                BoxLayout.encloseY(new Label(""))
        ));
        
        valider.requestFocus();
        
        valider.addActionListener(e -> {
            if(ServicePersonne.getInstance().sendMail(email.getText().toString())){
                 new CodeForm(res,email.getText().toString()).show();
            }else{
                 Dialog.show("Echec:  ","Email introuvable","OK",null);
             
            }
            
        });
        
        
        
    }
    
    //sendMail
    
   /*public void sendMail(Resources res) {
        try {
            
            Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp"); //SMTP protocol
		props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication
            Session session = Session.getInstance(props,null); // aleh 9rahach 5ater mazlna masabinach javax.mail .jar
            System.out.println("1");
            
            MimeMessage msg = new MimeMessage(session);
            System.out.println("2");
            msg.setFrom(new InternetAddress("Reintialisation mot de passe <yesmine.guesmi@esprit.tn>"));
            msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
            msg.setSubject("Evëntili  : Confirmation du ");
            msg.setSentDate(new Date(System.currentTimeMillis()));
            System.out.println("linaa");
           String mp = ServicePersonne.getInstance().getCodeByEmail(email.getText().toString(), res);//mp taw narj3lo
           String txt = "Bienvenue sur Evëntili : Tapez ce mot de passe : "+mp+" dans le champs requis et appuiez sur confirmer";
           
           
           msg.setText(txt);
           
          SMTPTransport  st = (SMTPTransport)session.getTransport("smtps") ;
            
          st.connect("smtp.gmail.com",465,"yesmineguesmi@gmail.com","oyjdjatabndjaaxg");
           
          st.sendMessage(msg, msg.getAllRecipients());
            
          System.out.println("server response : "+st.getLastServerResponse());
          
        }catch(Exception e ) {
            e.printStackTrace();
        }
    }*/
}
