package com.company.entities;

import java.util.Objects;

public class EventCateg {

    private int idcat;
    private String type;

    public EventCateg(int id, String type) {
        this.idcat = id;
        this.type = type;
    }

    public EventCateg(String type) {
        this.type = type;
    }

    public EventCateg() {
    }

    public void setId(int id) {
        this.idcat = id;
    }

    
    public int getId() {
        return idcat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventCateg other = (EventCateg) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "CategEvent{" + "id=" + idcat + ", type=" + type + '}';
    }

}
