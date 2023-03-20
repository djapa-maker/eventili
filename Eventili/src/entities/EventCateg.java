package entities;

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
    public String toString() {
        return "CategEvent{" + "id=" + id_cat + ", type=" + type + '}';
    }

}
