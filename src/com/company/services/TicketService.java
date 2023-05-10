package com.company.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.company.entities.Evenement;
import com.company.entities.Ticket;
import com.company.entities.Transaction;
import com.company.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketService {

    public static TicketService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Ticket> listTickets;


    private TicketService() {
        cr = new ConnectionRequest();
    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public ArrayList<Ticket> getAll() {
        listTickets = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/mobile/ticket");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listTickets = getList();
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

        return listTickets;
    }

    private ArrayList<Ticket> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Ticket ticket = new Ticket(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeTransaction((Map<String, Object>) obj.get("transaction")),
                        (int) Float.parseFloat(obj.get("nbTick").toString()),
                        Float.parseFloat(obj.get("prix").toString()),
                        (String) obj.get("status"),
                        makeEvenement((Map<String, Object>) obj.get("evenement"))

                );

                listTickets.add(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listTickets;
    }
    
    
    /*public int addqrcode(Ticket ticket) {
        
    }*/

    public Transaction makeTransaction(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId((int) Float.parseFloat(obj.get("id").toString()));
        transaction.setMode((String) obj.get("mode"));
        return transaction;
    }

    public Evenement makeEvenement(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Evenement evenement = new Evenement();
        evenement.setId((int) Float.parseFloat(obj.get("id").toString()));
        evenement.setTitre((String) obj.get("titre"));
        evenement.setPrix(Float.parseFloat(obj.get("prix").toString()));
        return evenement;
    }

    public int add(Ticket ticket) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/mobile/ticket/add");

        cr.addArgument("transaction", String.valueOf(ticket.getTransaction().getId()));
        cr.addArgument("nbTick", String.valueOf(ticket.getNbTick()));
        cr.addArgument("prix", String.valueOf(ticket.getPrix()));
        cr.addArgument("status", ticket.getStatus());
        cr.addArgument("evenement", String.valueOf(ticket.getEvenement().getId()));


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

    public int edit(Ticket ticket) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/mobile/ticket/edit");
        cr.addArgument("id", String.valueOf(ticket.getId()));

        cr.addArgument("transaction", String.valueOf(ticket.getTransaction().getId()));
        cr.addArgument("nbTick", String.valueOf(ticket.getNbTick()));
        cr.addArgument("prix", String.valueOf(ticket.getPrix()));
        cr.addArgument("status", ticket.getStatus());
        cr.addArgument("evenement", String.valueOf(ticket.getEvenement().getId()));


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

    public int delete(int ticketId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/mobile/ticket/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(ticketId));

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
