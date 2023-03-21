package entities;

import java.util.Objects;

public class EventCateg {

    private int id_cat;
    private String type;

    public EventCateg(int id, String type) {
        this.id_cat = id;
        this.type = type;
    }

    public EventCateg(String type) {
        this.type = type;
    }

    public EventCateg() {
    }

    public int getId() {
        return id_cat;
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
        return "CategEvent{" + "id=" + id_cat + ", type=" + type + '}';
    }

}
