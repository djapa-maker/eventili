/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Event;
import com.company.entities.EventCateg;
import com.company.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chaim
 */
public class ServiceEvent {
    
    
    public static double nb_events;
    
    public static ServiceEvent instance = null;
    private ArrayList<EventCateg> categories = new ArrayList<EventCateg>();

    public static boolean resultOk = true;

    private ConnectionRequest req;

    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }

    public ServiceEvent() {
        req = new ConnectionRequest();

    }
//---------------------------------------------------------------------
    public ArrayList<Event> affichageEvent() {

        ArrayList<Event> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/admin/video/allvideos";
        req.setUrl(url);
        System.out.println(req.getUrl());
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapEvent = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapEvent.get("root");
                    nb_events = listOfMaps.size();
                    for (Map<String, Object> obj : listOfMaps) {
                        Event e = new Event();

                        float id = Float.parseFloat(obj.get("idEv").toString());
                        //System.out.println("idV " + (int) id);
                        String title = obj.get("titre").toString();
//                        String id_categ = obj.get("idCateg").toString();
                        e.setTitle(title);
                        e.setId_ev((int)id);


                        result.add(e);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }
    //---------------------------------------------------------------------

    public ArrayList<EventCateg> getCategories() {
        return categories;
    }
//---------------------------------------------------------------------


//---------------------------------------------------------------------
    
    
    
}
