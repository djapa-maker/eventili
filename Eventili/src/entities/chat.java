package entities;

import java.sql.Timestamp;

public class chat {
    private int id_chat;
    private String message;
    private Timestamp timeStamp;
    private Personne p;
    private Event e;
    public chat(){
        
    }
    public chat(int id_chat,Timestamp timeStamp, String message,Personne p,Event e)
    {
        this.id_chat = id_chat;
        this.timeStamp = timeStamp;
        this.p=p;
        this.e=e;
        this.message = message;
    }
    public chat(int id_chat, String message,Personne p,Event e)
    {
        this.id_chat = id_chat;
        this.p=p;
        this.e=e;
        this.message = message;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }
    public int getId(){
        return this.id_chat;
    }
   
    public Timestamp getTimestamp(){
        return this.timeStamp;
    }
    public String getMessage(){
        return this.message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    public void setTimeStamp(Timestamp timeStamp){
        this.timeStamp = timeStamp;
    }

    public Personne getP() {
        return p;
    }
   
    public Event getE() {
        return e;
    }

    @Override
    public String toString() {
        return "chat{" + "id_chat=" + id_chat + ", message=" + message + ", timeStamp=" + timeStamp + ", p=" + p + ", e=" + e + '}';
    }
    
    

    
    
}
