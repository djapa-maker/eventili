/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author win10
 */
public class SendMail {
    
    
    public static void sendMail(String receveursList,String object,String corps) {
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", 25); 
         properties.put("mail.smtp.user","yesmineguesmi@gmail.com");
       properties.put("mail.smtp.debug",true);
        properties.put("mail.smtp.socketFactory.port",465);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback",false);
        properties.put("spring.mail.password", "yywnthyglqlpxjcy");
       // properties.put("spring.mail.password", "roabojnccpuqbiko");
        String MonEmail = "yesmineguesmi@gmail.com";
        String password = "oyjdjatabndjaaxg";
        




        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new javax.mail.PasswordAuthentication(MonEmail, password);
            }
        
        });
        
        javax.mail.Message message = prepareMessage(session,MonEmail,receveursList,object,corps);
        
        try {
            javax.mail.Transport.send(message);
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.err.println("Message envoyé avec succès");
    }
    
    private static javax.mail.Message prepareMessage(Session session,String email,String receveursList,String object,String corps)
    {
        javax.mail.Message message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(email));
            
            message.setSubject(object);
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receveursList));
            message.setText(corps);
            
            return message;
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
