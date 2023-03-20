package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Event {

    private int id_ev, participantLimit;
    private float price;
    private String title, description, image,type,visibilite;
    private LocalDateTime date_debut, date_fin;
    private EventCateg c;
    private Personne pers;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event() {
    }

    public Event(int participantLimit, float price, String title, String description, String image, String type, String visibilite, LocalDateTime date_debut, LocalDateTime date_fin, EventCateg c, Personne pers) {
        this.participantLimit = participantLimit;
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.visibilite = visibilite;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.c = c;
        this.pers = pers;
    }

    
    public Event(int id_ev, int participantLimit, float price, String title, String description, String image, String type, String visibilite, LocalDateTime date_debut, LocalDateTime date_fin, EventCateg c , Personne pers) {
        this.id_ev = id_ev;
        this.participantLimit = participantLimit;
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.visibilite = visibilite;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.c = c;
        this.pers = pers;
    }

    public int getId_ev() {
        return id_ev;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
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
        return "Event{" + "id_ev=" + id_ev + ", participantLimit=" + participantLimit + ", price=" + price + ", title=" + title + ", description=" + description + ", image=" + image + ", type=" + type + ", visibilite=" + visibilite + ", date_debut=" + date_debut.format(formatter) + ", date_fin=" + date_fin.format(formatter) + ", c=" + c.getType() + ", per=" +pers.getId_pers()+'}';
    }
    
    
    
  



}
