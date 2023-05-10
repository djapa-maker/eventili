package com.company.entities;




public class Event {

    private int id_ev, participantLimit;
    private float price;
    private String title, description,type,visibilite;
    private EventCateg c;
    private Personne pers;

    public Event() {
    }

    public Event(int participantLimit, float price, String title, String description, String type, String visibilite,EventCateg c, Personne pers) {
        this.participantLimit = participantLimit;
        this.price = price;
        this.title = title;
        this.description = description;
        this.type = type;
        this.visibilite = visibilite;
        this.c = c;
        this.pers = pers;
    }

    
     public Event(int id_ev,String title,EventCateg c) {
        this.id_ev = id_ev;
        this.title = title;
        this.c = c;

    }
     
    public Event(int id_ev, int participantLimit, float price, String title, String description, String type, String visibilite, EventCateg c , Personne pers) {
        this.id_ev = id_ev;
        this.participantLimit = participantLimit;
        this.price = price;
        this.title = title;
        this.description = description;
        this.type = type;
        this.visibilite = visibilite;
        this.c = c;
        this.pers = pers;
    }

    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public int getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(int participantLimit) {
        this.participantLimit = participantLimit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventCateg getC() {
        return c;
    }

    public Personne getPers() {
        return pers;
    }
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }

    @Override
    public String toString() {
        return "Event{" + "id_ev=" + id_ev + ", participantLimit=" + participantLimit + ", price=" + price + ", title=" + title + ", description=" + description + ", type=" + type + ", visibilite=" + visibilite + ", c=" + c.getType() + ", per=" +pers.getId_pers()+'}';
    }
    
   
    
  



}
