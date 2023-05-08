/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.utils.Statics;

/**
 *
 * @author bitri
 */
public class ReclamationService {
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
        String url = Statics.BASE_URL + "/reclamation/m/ajouterRec?userId=" + r.getPers().getId_pers() + "&titre=" + r.getTitre() +"&description="+ r.getDescription();
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
        String url = Statics.BASE_URL + "/reclamation/m/update/"+r.getId();
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
}  
