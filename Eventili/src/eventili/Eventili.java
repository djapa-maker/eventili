/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventili;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

/*import entities.Avis;
import entities.Event;
import entities.EventCateg;
import entities.Personne;
import entities.Services;
import entities.Sous_service;
import entities.Transactions;
import java.sql.Date;
import java.util.List;
import services.AvisService;
import services.EventService;
import services.ServiceService;
import services.Sous_serviceS;
import services.TransactionsService;
import entities.ServiceReservation;
import entities.Ticket;
import entities.chat;
import services.ChatService;
import services.EventCategService;
import services.ServiceReservationService;
import services.TicketService;
import services.PersonneService;
*/
/**
 *
 * @author HP
 */
public class Eventili {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               
        // Replace with your actual API key and secret
        String apiKey = "ba73172c-c4b6-48c9-868d-de0c16383f5c";
        String apiSecret = "d7d17a4c-a159-4fbd-8a98-1078001bd05f";
        
        // Base64 encode the API key and secret
        String encodedApiKey = Base64.getEncoder().encodeToString(apiKey.getBytes());
        String encodedApiSecret = Base64.getEncoder().encodeToString(apiSecret.getBytes());
        
        try {
            // Prepare the HTTP request
            URL url = new URL("https://pay.flouci.com/api/v1/payments");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic " + encodedApiKey + ":" + encodedApiSecret);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Prepare the payment request data
            String data = "{\"amount\":\"10.00\",\"currency\":\"TND\",\"description\":\"Test payment\"}";
            
            // Send the payment request
            conn.getOutputStream().write(data.getBytes());
            
            // Read the response
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            
            // Print the response
            System.out.println(response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }  
      /*  Personne p1 = new Personne(8, "rania", "guesmi", "56108211", "yesmine.guesmi@esprit.tn","tes", "naser", "rien", "rien","organisateur");
        
        Transactions t1 = new Transactions(1, 4f, 5f, "fffdfdgd", Date.valueOf("2023-1-1") , "fdfetftf", p1);
        
        EventCateg c1 = new EventCateg(3, "Mariage");
        
        Event e1 = new Event(1, 2, 15.0f, "ggg", "hhhh", "fff", "vvvv", Date.valueOf("2023-1-1"), Date.valueOf("2023-1-10"), c1);
        
        
        
        Ticket t = new Ticket( 2, 2, false, t1 , e1);
        TicketService tick = new TicketService();
           
       // tick.ajouter(t);
        //tick.modifierTicket(3,t);
        
        //System.out.println(tick.getAll());
        //System.out.println(tick.findById(8));
        
        //tick.supprimer(t);



        //ServiceService s = new ServiceService();
        //Personne p1 = new Personne(8, "rania", "guesmi", "56108211", "yesmine.guesmi@esprit.tn", "naser", "rien", "rien","organisateur");
        
//       Personne p0 = new Personne(6,"rania", "guesmi","56108211","yesmine.guesmi@esprit.tn","naser","rien","rien");
//        
        //Services s1 = new Services(14, 20, "Buffet", "Nous sommes ravis de vous présenter nos services", "icon1", 5, p1);
//        Services s2=new Services(2,15,"musique","Nous sommes ravis de vous présenter nos services","icon2",4,1);
        // s.ajouter(s1);
//        s.ajouter(s2);
        //  System.out.println(s.getAll());
//        s.supprimer(s1);
//        s.modifierService(15, s2);
//        s.modifierService(6,11f,"mekla","eeeee","",2, s2);
        //System.out.println(s.findServiceById(14));
        //----------------sous_service-----------------------------
//         Sous_serviceS ss=new Sous_serviceS();
//         Sous_service ss1=new Sous_service(1,"gateaux",s1);
//         Sous_service ss2=new Sous_service(2,"pop",13);
        //ss.ajouter(ss1);
//         ss.ajouter(ss2);
        // System.out.println(ss.getAll());
//         ss.supprimer(ss2);
//         ss.modifierSS(ss2,"hello");
//         System.out.println(ss.findSSByIdService(12));

        //--------------personne-------------------------------
        PersonneService ps = new PersonneService();
        //Personne p1 = new Personne(7,"rania", "guesmi","56108211","yesmine.guesmi@esprit.tn","naser","rien","rien");
         //Personne p2 = new Personne(8, "test", "guesmi", "50555555", "yesmine.guesmi@esprit.tn", "naser", "rien", "rien","organisateur");
        // ps.ajouter(p2);
        //ps.supprimer(p2);
        
       //ps.modifier(8, p2);
       //System.out.println(ps.getAll());
//        Personne personnes = ps.findById(8);
//        System.out.println(personnes);

        //-------------Transactions--------------------
        TransactionsService t3 = new TransactionsService();
//        Transactions t1 = new Transactions(1,4, 15, 200,"euro",new Date(2023,12,16),"aw");
//         Transactions t2 = new Transactions(2,4, 15, 215,"euro",new Date(2010,12,16),"aw");
        //t.ajouter(t2);
        //t.supprimer(t2);
        //t.modifierTransaction(""+ "$", t1);
        System.out.println(t3.getAll());
//       List<Transactions> tr = t.findById(3);
//       for (Transactions tt : tr) {
//            System.out.println(tt);
//        }
        //-------------Avis--------------------
//        AvisService a = new AvisService();
//         Avis a1 = new Avis(1,"bien", "rabeb", 4,new Date(2023,12,16),p1,s1);
//         Avis a2 = new Avis(2,"très bien", "rabeb", 4,new Date(2023,12,16),p1,s1);
        //a.ajouter(a2);
        //a.ajouter(a1);
        //a.supprimer(a2);
        //a.modifierTransaction("chaima", a1);
        //System.out.println(a.getAll());
        // a.findById(1);
        //----------------event--------------------
       // EventCategService cs = new EventCategService();
       // EventCateg c1 = new EventCateg(3, "Mariage");
        //cs.ajouter(c1);
       // EventService es = new EventService();

       // Event e1 = new Event(9, 2, 15.0f, "ggg", "hhhh", "fff", "vvvv", Date.valueOf("2023-1-1"), Date.valueOf("2023-1-10"), c1);
       // es.ajouter(e1);
       //es.modifier("Concert", Date.valueOf("2023-01-12"), Date.valueOf("2023-01-15"), "youhoo", 30, " ", " ", 15, 3, e1);
        //es.supprimer(e1);
        //System.out.println(es.getAll());

       //cs.ajouter(c1);
        //cs.modifier("mari", c1);
        //supprimer(c1);
        //System.out.println(cs.getAll());
        //ServiceReservationService rs = new ServiceReservationService();
        //ServiceReservation sr=new ServiceReservation(1, 14, 1, "created",s1,e1);
        //(int id_res, int id_serv, int id_ev, String status)
        //rs.ajouter(e1);
        //rs.modifier("Concert", Date.valueOf("2023-01-12"), Date.valueOf("2023-01-15"), "youhoo", 30, " ", " ", 15, 3, e1);
        //rs.supprimer(e1);
        //System.out.println(rs.getAll());
       //----------------chat--------------------  
//         ChatService a = new ChatService();
//         chat c = new chat(1,"bien", p1, e1);
//        // a.ajouter(c);
////         a.supprimer(c);
//        // System.out.println(a.getAll());
//         System.out.println(a.findById(3));
////         System.out.println(a.findbyEvent(9));
*/
}
}