package entities;

/**
 *
 * @author chaim
 */
public class imageEv {   
    private int idimgev;
    private String img;
    private Event ev;

    public imageEv() {
    }

    public imageEv(String img, Event ev) {
        this.img = img;
        this.ev = ev;
    }
    
    public imageEv(int idimgev, String img, Event ev) {
        this.idimgev = idimgev;
        this.img = img;
        this.ev = ev;
    }

    public int getIdimgev() {
        return idimgev;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Event getEv() {
        return ev;
    }

    public void setEv(Event ev) {
        this.ev = ev;
    }

    @Override
    public String toString() {
        return "imageEv{" + "idimgev=" + idimgev + ", img=" + img + ", ev=" + ev + '}';
    }  
}
