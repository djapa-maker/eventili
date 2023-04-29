package entities;

import java.util.ArrayList;

public class ServiceReservation {

    private int id_res;
    private ArrayList<SousServices> list = new ArrayList<>();
    private Event e;
    private boolean status;

    public ServiceReservation() {

    }

    public ServiceReservation(int id_res, Boolean status, Event e, ArrayList<SousServices> list) {
        this.id_res = id_res;
        this.e = e;
        this.list = list;
        this.status = status;
    }

    public ServiceReservation(Event e, boolean status) {
        this.e = e;
        this.status = status;
    }
    

    public ServiceReservation(Event e, boolean status, ArrayList<SousServices> list) {
        this.e = e;
        this.status = status;
        this.list = list;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId_res() {
        return id_res;
    }

    public ArrayList<SousServices> getList() {
        return list;
    }

    public void setList(ArrayList<SousServices> list) {
        this.list = list;
    }

    public Event getE() {
        return e;
    }

    public void setE(Event e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "ServiceReservation{" + "id_res=" + id_res + ", list=" + list + ", event=" + e + ", status=" + status + '}';
    }

}
