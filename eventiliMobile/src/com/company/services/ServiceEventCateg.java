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
import com.company.entities.EventCateg;
import com.company.utils.Statics;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author chaim
 */
public class ServiceEventCateg {
    public static double nb_categories;
    
    private ConnectionRequest req;
    public boolean resultOK;
    public static ServiceEventCateg instance = null;
    private ArrayList<EventCateg> Categ = new ArrayList<EventCateg>();

    private ServiceEventCateg() {
        req = new ConnectionRequest();
    }

    public static ServiceEventCateg getInstance() {
        if (instance == null) {
            instance = new ServiceEventCateg();
        }
        return instance;
    }

    //--------------------------------------------------------------------------
    public boolean addCategory(EventCateg s) {

        String type = s.getType();
        String url = Statics.BASE_URL + "/category/mobile/new?type=" + type;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<EventCateg> AllCategories() {
        Categ = new ArrayList<EventCateg>();
        ArrayList<EventCateg> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/category/mobile";
        req.setUrl(url);
        System.out.println(req.getUrl());
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapCat = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    java.util.List<Map<String, Object>> listOfMaps = (java.util.List<Map<String, Object>>) mapCat.get("root");
                    nb_categories = listOfMaps.size();
                    for (Map<String, Object> obj : listOfMaps) {
                        EventCateg Ec = new EventCateg();
                        float id = Float.parseFloat(obj.get("idCateg").toString());
                        //System.out.println(id);
                        String type = obj.get("type").toString();
                        //System.out.println(type);
                        Ec.setId((int)id);
                        Ec.setType(type);
                        result.add(Ec);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    //--------------------------------------------------------------------------
    
    public boolean deleteCategory(float idCateg) {
        String url = Statics.BASE_URL + "/category/mobile/" +(int)idCateg;
        System.out.println(url);
        System.out.println(idCateg);
        req.setPost(false);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    //--------------------------------------------------------------------------
    
    public boolean modifierCategory(EventCateg Ec) {

        String url = Statics.BASE_URL + "/category/mobile/edit/" + (int) Ec.getId() + "?type=" + Ec.getType(); 
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }
}

    

