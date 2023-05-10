/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Reclamation;
import com.company.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bitri
 */
public class ReclamationService {
    
    public ArrayList<Reclamation> reclamations;
    private ConnectionRequest req;
    public boolean resultOK;
    public static ReclamationService instance = null;
    public ReclamationService(){
        this.req = new ConnectionRequest();
    }
    public static ReclamationService getInstance(){
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }
    public boolean addReclamation(Reclamation r){
        String url = Statics.BASE_URL + "/reclamation/m/ajouter/Rec?userId=" + r.getPers() + "&titre=" + r.getTitre() +"&description="+ r.getDescription();
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
    public boolean supprimerRec(Reclamation r){
        String url = Statics.BASE_URL + "/reclamation/m/supprimerRec/"+r.getId();
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
    public boolean cloturerRec(Reclamation r){
        String url = Statics.BASE_URL + "/reclamation/m/cloturerRec/"+r.getId();
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
    public boolean modifierRec(Reclamation r){
        String url = Statics.BASE_URL + "/reclamation/m/update/" + r.getId() + "?titre="+r.getTitre()+"&description=" +r.getDescription()+"&status="+r.getStatus();
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
    public ArrayList<Reclamation> parseReclamations(String jsonText) {
        try {
            reclamations = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reclamation r = new Reclamation();
                r.setId((int) Float.parseFloat(obj.get("idRec").toString()));
                r.setDescription(obj.get("description").toString());
                r.setTitre(obj.get("titre").toString());
                r.setStatus(obj.get("status").toString());
                reclamations.add(r);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }

    public ArrayList<Reclamation> getAllReclamations() {
        String url = Statics.BASE_URL + "/reclamation/m";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }
    public ArrayList<Reclamation> getAllReclamations(int idPers) {
        String url = Statics.BASE_URL + "/reclamation/m/"+idPers;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }
}  
