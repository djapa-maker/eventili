/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Reponse;
import com.mycompany.gui.SessionManager;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author bitri
 */
public class ReponseService {
    private ArrayList<Reponse> rep;
    private ConnectionRequest req;
    public boolean resultOK;
    public static ReponseService instance = null;
    public ReponseService(){
        this.req = new ConnectionRequest();
    }
    public static ReponseService getInstance(){
        if (instance == null) {
            instance = new ReponseService();
        }
        return instance;
    }
    public boolean ajouterRep(Reponse r){
        String url = Statics.BASE_URL + "/reclamation/m/ajouterRep/"+ SessionManager.getId() + "/" + r.getRec() + "?message="+r.getMessage();
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
    public boolean updateRep(Reponse r){
        String url = Statics.BASE_URL + "/reclamation/m/updateRep/"+r.getId() + "?message="+r.getMessage();
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
    public boolean supprimerRep(Reponse r){
        String url = Statics.BASE_URL + "/reclamation/m/removeRep/" + r.getId();
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
    
    public ArrayList<Reponse> parseReponse(String jsonText) {
        try {
            rep = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reponse r = new Reponse();
                r.setId((int) Float.parseFloat(obj.get("idRep").toString()));
                r.setMessage(obj.get("message").toString());
                
                rep.add(r);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return rep;
    }

    public ArrayList<Reponse> getAllReponses(int idRec) {
        String url = Statics.BASE_URL + "/reclamation/m/" + idRec + "/consulter";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rep = parseReponse(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rep;
    }
}
