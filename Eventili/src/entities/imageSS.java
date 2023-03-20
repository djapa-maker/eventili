package entities;

/**
 *
 * @author HP
 */
public class imageSS {
    private int idimgss;
    private String img;
    private SousServices s;
    public imageSS() {
    }

    public imageSS(int idimgss, String img,SousServices s) {
        this.idimgss = idimgss;
        this.img = img;
        this.s=s;
    }

    public int getIdimgss() {
        return idimgss;
    }

    public void setIdimgss(int idimgss) {
        this.idimgss = idimgss;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public SousServices getS() {
        return s;
    }

    public void setS(SousServices s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "imageSS{" + "idimgss=" + idimgss + ", img=" + img + ", s=" + s + '}';
    }


}
