
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.company.entities.EventCateg;
import com.company.entities.Sousservices;
import com.company.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class ServiceSS {
    public static ServiceSS instance = null;
    private ArrayList<EventCateg> servs = new ArrayList<EventCateg>();
    public static boolean resultOk = true;
    private ConnectionRequest req;
//------------------------------------------------------------------------------
    public static ServiceSS getInstance() {
        if (instance == null) {
            instance = new ServiceSS();
        }
        return instance;
    }
//------------------------------------------------------------------------------
     public ServiceSS() {
        req = new ConnectionRequest();
    }
//------------------------------------------------------------------------------
      public void ajoutSS(Sousservices ss) {

        String url = Statics.BASE_URL + "/sousservice/newMobile?nom=" + ss.getNom() + "&description=" + ss.getDescription() + "&prix=" + ss.getPrix()+ "&note=" +0;

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
//------------------------------------------------------------------------------    
        public ArrayList<Sousservices> affichageSS() {

        ArrayList<Sousservices> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/sousservice/AllSSMobile/list";
        req.setUrl(url);
        System.out.println(req.getUrl());
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapSS = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapSS.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Sousservices v = new Sousservices();
                        float id = Float.parseFloat(obj.get("id").toString());
                        System.out.println("id " + (int) id);
                        String nom = obj.get("nom").toString();
                        String description = obj.get("description").toString();
                        String prix = obj.get("prix").toString();
                        String note = obj.get("note").toString();
                        v.setId((int) id);
                        v.setNom(nom);
                        v.setDescription(description);
                        v.setPrix(Float.parseFloat(prix) );
                        result.add(v);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
    }
//------------------------------------------------------------------------------   
     public boolean deleteSS(float id) {
        String url = Statics.BASE_URL + "/sousservice/deleteMobile/" +(int)id;
        System.out.println(url);
        System.out.println(id);
        req.setPost(false);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
//------------------------------------------------------------------------------              
}
