/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.URL;
import com.company.entities.Transactions;
import com.company.utils.Statics;
 import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Personne;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
 

/**
 *
 * @author sel3a
 */
public class ServiceTransaction {
      public static ServiceTransaction instance=null;
    public static boolean resultOk = true;
   private ConnectionRequest req;
   String json;
   int jsons;
   int id = 0;
   int x=0;
   int y=0;
   int z=0;
   private String response;
    public static ServiceTransaction getInstance(){
       if(instance==null) 
           instance=new ServiceTransaction();
       
       return instance;
    }
   
     public ServiceTransaction(){
        req=new ConnectionRequest();
    }  
//find all transaction by id
  //------------------------
public Transactions find(int id) {
    Transactions t = new Transactions();
    String url = Statics.BASE_URL + "/transactionsearch/" + id;
    req.setUrl(url);
    
    req.addResponseListener((evt) -> {
        String str = new String(req.getResponseData());
        JSONParser jsonp = new JSONParser();
        try {
            Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(str.toCharArray()));
            
            t.setId_trans((int)obj.get("id_trans"));
            t.setValeur_trans((float)obj.get("valeur_trans"));
            t.setMontant_tot((float)obj.get("montant_tot"));
            t.setDevis(obj.get("devis").toString());
            t.setDate_trans(obj.get("date_trans").toString());
            t.setMode_trans(obj.get("mode_trans").toString());
            } catch (IOException ex) {
            System.out.println("Error parsing response data: " + ex.getMessage());
        }
        
        System.out.println("Data: " + str);
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return t;
}
//----------------------
//---------------find all transaction for  
public List<Transactions> findTransactionsByUserId(int userId) {
    List<Transactions> transactions = new ArrayList<>();

    // Build the request URL
    String url = Statics.BASE_URL + "/transactionsearchidu?iduser=" + userId;
    System.out.println("Request URL: " + url);

    req.setUrl(url);
    req.addResponseListener((evt) -> {
        String str = new String(req.getResponseData());
        System.out.println("Response Data: " + str);
        
        JSONParser jsonp = new JSONParser();
        try {
            Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(str.toCharArray()));
            
            if (obj.containsKey("transactions")) {
                Object transactionsObj = obj.get("transactions");
                if (transactionsObj instanceof List) {
                    List<?> transactionsList = (List<?>) transactionsObj;
                    for (Object transactionObj : transactionsList) {
                        if (transactionObj instanceof Map) {
                            Map<?, ?> transactionMap = (Map<?, ?>) transactionObj;
                            Transactions t = new Transactions();
                            t.setId_trans((int) transactionMap.get("idTrans"));
                            t.setValeur_trans((float) transactionMap.get("valeurTrans"));
                            t.setMontant_tot((float) transactionMap.get("montantTot"));
                            t.setDevis(transactionMap.get("devis").toString());
                            t.setDate_trans(transactionMap.get("dateTrans").toString());
                            t.setMode_trans(transactionMap.get("modeTrans").toString());
                            transactions.add(t);
                        }
                    }
                }
            } else {
                System.out.println("Error: response does not contain transactions");
            }
            
        } catch (IOException ex) {
            System.out.println("Error parsing response data: " + ex.getMessage());
        }
    });
                            System.out.println("Transaction added to list: " +transactions);

    return transactions;
}

//------
 public List<Transactions> findTransactions(int id) {
List<Transactions> result = new ArrayList<>();
 String url = Statics.BASE_URL + "/transactionsearchidu?iduser="+ id;
ConnectionRequest req = new ConnectionRequest();
req.setUrl(url);

req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        try {
            JSONParser jsonp = new JSONParser();
            Map<String,Object> mapTransactions = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            
            System.out.println("mapTransactions: " + mapTransactions);
            
            if (mapTransactions.containsKey("root")) {
                Object transactionsObj = mapTransactions.get("root");
                if (transactionsObj instanceof List) {
                    List<?> transactionsList = (List<?>) transactionsObj;
                    for (Object transactionObj : transactionsList) {
                        if (transactionObj instanceof Map) {
                            Map<?, ?> transactionMap = (Map<?, ?>) transactionObj;
                            Transactions t = new Transactions();
                            t.setId_trans(((Double) transactionMap.get("idTrans")).intValue());
                            t.setValeur_trans(((Double) transactionMap.get("valeurTrans")).floatValue());
                            t.setMontant_tot(((Double) transactionMap.get("montantTot")).floatValue());
                              t.setDate_trans(transactionMap.get("dateTrans").toString().substring(0, 10));
                            t.setMode_trans(transactionMap.get("modeTrans").toString());
                            t.setDevis(transactionMap.get("devis").toString());
                            // Extract user data
                            Map<?, ?> userMap = (Map<?, ?>) transactionMap.get("userId");
                            Personne p = new Personne();
                            p.setId_pers(((Double) userMap.get("idPers")).intValue());
                            p.setNom_pers(userMap.get("nomPers").toString());
                            p.setPrenom_pers(userMap.get("prenomPers").toString());
                            p.setNum_tel(userMap.get("numTel").toString());
                            p.setEmail(userMap.get("email").toString());
                            p.setAdresse(userMap.get("adresse").toString());
                            p.setRib(userMap.get("rib").toString());
                            p.setRole(userMap.get("role").toString());
                            
                            t.setP(p);
                            
                            // Date
                            Object dateObj = transactionMap.get("dateTrans");
                            if (dateObj instanceof Map) {
                                Map<?, ?> dateMap = (Map<?, ?>) dateObj;
                                Object timestampObj = dateMap.get("timestamp");
                                if (timestampObj instanceof Double) {
                                    Double timestamp = (Double) timestampObj;
                                    Date date = new Date(timestamp.longValue() * 1000);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateString = formatter.format(date);
                                    t.setDate_trans(dateString);
                                }
                            }
                            
                            // Insert data into result list
                            result.add(t);
                         }
                    }
                }
            } else {
                System.out.println("mapTransactions does not contain root key");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Throw an exception or return an error message here
        }
    }
});

NetworkManager.getInstance().addToQueueAndWait(req);

return result;

 
 }
 //----ajout transaction
public void add(Transactions t) {
    // Build the request URL
    String url = Statics.BASE_URL + "/savetransaction?"+ 
            "&valeur_trans=" + t.getValeur_trans() +
            "&montant_tot=" + t.getMontant_tot() +
            "&devis=" + t.getDevis() + 
            "&mode_trans=" + t.getMode_trans() +
            "&idpers=" + t.getP().getId_pers();
    
    // Set the URL and HTTP method of the request
    req.setUrl(url);
    req.setHttpMethod("POST");
    
    // Add a response listener to the request
    req.addResponseListener((evt) -> {
        String str = new String(req.getResponseData());
        System.out.println("Response: " + str);
    });
    
    // Send the request to the server
    NetworkManager.getInstance().addToQueueAndWait(req);
}
//par citere 
 public List<Transactions> findTransactionscritere(int id,String valmin) {
List<Transactions> result = new ArrayList<>();
String url = Statics.BASE_URL + "/transactionsearchiducrit?userId=" + id + "&valmin=" + valmin;
ConnectionRequest req = new ConnectionRequest();
req.setUrl(url);

req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        try {
            JSONParser jsonp = new JSONParser();
            Map<String,Object> mapTransactions = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            
            System.out.println("mapTransactions: " + mapTransactions);
            
            if (mapTransactions.containsKey("root")) {
                Object transactionsObj = mapTransactions.get("root");
                if (transactionsObj instanceof List) {
                    List<?> transactionsList = (List<?>) transactionsObj;
                    for (Object transactionObj : transactionsList) {
                        if (transactionObj instanceof Map) {
                            Map<?, ?> transactionMap = (Map<?, ?>) transactionObj;
                            Transactions t = new Transactions();
                            t.setId_trans(((Double) transactionMap.get("idTrans")).intValue());
                            t.setValeur_trans(((Double) transactionMap.get("valeurTrans")).floatValue());
                            t.setMontant_tot(((Double) transactionMap.get("montantTot")).floatValue());
                              t.setDate_trans(transactionMap.get("dateTrans").toString().substring(0, 10));
                            t.setMode_trans(transactionMap.get("modeTrans").toString());
                            t.setDevis(transactionMap.get("devis").toString());
                            // Extract user data
                            Map<?, ?> userMap = (Map<?, ?>) transactionMap.get("userId");
                            Personne p = new Personne();
                            p.setId_pers(((Double) userMap.get("idPers")).intValue());
                            p.setNom_pers(userMap.get("nomPers").toString());
                            p.setPrenom_pers(userMap.get("prenomPers").toString());
                            p.setNum_tel(userMap.get("numTel").toString());
                            p.setEmail(userMap.get("email").toString());
                            p.setAdresse(userMap.get("adresse").toString());
                            p.setRib(userMap.get("rib").toString());
                            p.setRole(userMap.get("role").toString());
                            
                            t.setP(p);
                            
                            // Date
                            Object dateObj = transactionMap.get("dateTrans");
                            if (dateObj instanceof Map) {
                                Map<?, ?> dateMap = (Map<?, ?>) dateObj;
                                Object timestampObj = dateMap.get("timestamp");
                                if (timestampObj instanceof Double) {
                                    Double timestamp = (Double) timestampObj;
                                    Date date = new Date(timestamp.longValue() * 1000);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateString = formatter.format(date);
                                    t.setDate_trans(dateString);
                                }
                            }
                            
                            // Insert data into result list
                            result.add(t);
                         }
                    }
                }
            } else {
                System.out.println("mapTransactions does not contain root key");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Throw an exception or return an error message here
        }
    }
});

NetworkManager.getInstance().addToQueueAndWait(req);

return result;

 
 }

//

}
