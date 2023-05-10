package com.company.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Evenement;
import com.company.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EvenementService {

    public static EvenementService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Evenement> listEvenements;


    private EvenementService() {
        cr = new ConnectionRequest();
    }

    public static EvenementService getInstance() {
        if (instance == null) {
            instance = new EvenementService();
        }
        return instance;
    }

    public ArrayList<Evenement> getAll() {
        listEvenements = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/mobile/evenement");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listEvenements = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEvenements;
    }

    private ArrayList<Evenement> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Evenement evenement = new Evenement(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("titre"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateDebut")),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateFin")),
                        (String) obj.get("descriptionEv"),
                        (String) obj.get("type"),
                        (String) obj.get("visibilite"),
                        (int) Float.parseFloat(obj.get("limiteparticipant").toString()),
                        Float.parseFloat(obj.get("prix").toString())

                );

                listEvenements.add(evenement);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listEvenements;
    }

    public int add(Evenement evenement) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/mobile/evenement/add");

        cr.addArgument("titre", evenement.getTitre());
        cr.addArgument("dateDebut", new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateDebut()));
        cr.addArgument("dateFin", new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateFin()));
        cr.addArgument("descriptionEv", evenement.getDescriptionEv());
        cr.addArgument("type", evenement.getType());
        cr.addArgument("visibilite", evenement.getVisibilite());
        cr.addArgument("limiteparticipant", String.valueOf(evenement.getLimiteparticipant()));
        cr.addArgument("prix", String.valueOf(evenement.getPrix()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int edit(Evenement evenement) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/mobile/evenement/edit");
        cr.addArgument("id", String.valueOf(evenement.getId()));

        cr.addArgument("titre", evenement.getTitre());
        cr.addArgument("dateDebut", new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateDebut()));
        cr.addArgument("dateFin", new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDateFin()));
        cr.addArgument("descriptionEv", evenement.getDescriptionEv());
        cr.addArgument("type", evenement.getType());
        cr.addArgument("visibilite", evenement.getVisibilite());
        cr.addArgument("limiteparticipant", String.valueOf(evenement.getLimiteparticipant()));
        cr.addArgument("prix", String.valueOf(evenement.getPrix()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int evenementId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/mobile/evenement/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(evenementId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
