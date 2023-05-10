package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Services;
import com.company.utils.Statics;
import java.util.ArrayList;
import java.util.Map;


/**
 *
 * @author HP
 */
public class ServiceService {

    private ConnectionRequest req;
    public boolean resultOK;
    public static ServiceService instance = null;
    private ArrayList<Services> serv = new ArrayList<Services>();

    private ServiceService() {
        req = new ConnectionRequest();
    }
//------------------------------------------------------------------------------
    public static ServiceService getInstance() {
        if (instance == null) {
            instance = new ServiceService();
        }
        return instance;
    }
//------------------------------------------------------------------------------
    public boolean addService(Services s) {
        String nom = s.getNom();
        String url = Statics.BASE_URL + "/service/newMobile?nom=" + nom;
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
//------------------------------------------------------------------------------
    public ArrayList<Services> AllServices() {
        serv = new ArrayList<Services>();
        ArrayList<Services> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/service/mobile";
        req.setUrl(url);
        System.out.println(req.getUrl());
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapServ = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    System.out.println(mapServ);
                    java.util.List<Map<String, Object>> listOfMaps = (java.util.List<Map<String, Object>>) mapServ.get("root");
                    System.out.println(mapServ);
                    for (Map<String, Object> obj : listOfMaps) {
                        Services s = new Services();
                        float id = Float.parseFloat(obj.get("idService").toString());
                        String nom = obj.get("nom").toString();
                        s.setIdService((int) id);
                        s.setNom(nom);
                        result.add(s);
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
    public boolean deleteService(float idService) {
        String url = Statics.BASE_URL + "/service/deleteMobile/" +(int)idService;
        System.out.println(url);
        System.out.println(idService);
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
//------------------------------------------------------------------------------
    public boolean modifierService(Services s) {

        String url = Statics.BASE_URL + "/service/editMobile/" + (int) s.getIdService() + "?nom=" + s.getNom(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation

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
//------------------------------------------------------------------------------
}
