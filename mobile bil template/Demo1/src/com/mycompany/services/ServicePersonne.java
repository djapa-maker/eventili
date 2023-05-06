/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.materialscreens.WalkthruForm;
import com.mycomany.entities.Personne;
import com.mycompany.gui.SessionManager;
import com.mycompany.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
/**
 *
 * @author msi
 */
public class ServicePersonne {
    public static ServicePersonne instance=null;
    public static boolean resultOk = true;
   private ConnectionRequest req;
   String json;
   int jsons;
   int id = 0;
   int x=0;
   int y=0;
   int z=0;
   private String response;
    public static ServicePersonne getInstance(){
       if(instance==null) 
           instance=new ServicePersonne();
       
       return instance;
    }
   
     public ServicePersonne(){
        req=new ConnectionRequest();
    }
     
     
        //findById
    public Personne find(int id) {
        
     Personne p=new Personne();
        String url = Statics.BASE_URL+"/Student/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                p.setNom_pers(obj.get("nomPers").toString());
                p.setPrenom_pers(obj.get("prenomPers").toString());
                p.setNum_tel(obj.get("numTel").toString());
                p.setEmail(obj.get("email").toString());
                p.setMdp(obj.get("mdp").toString());
                p.setAdresse(obj.get("adresse").toString()); 
                 p.setRib(obj.get("rib").toString());
                p.setRole(obj.get("role").toString());
                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return p;
        
        
    }
     
      //Sigin
    public boolean signin(TextField nomPers,TextField prenomPers,TextField email,TextField mdp, ComboBox<String> role,TextField rib,TextField numTel,TextField adresse , Resources res ) {
        
     
        String url = Statics.BASE_URL+"/signin?nomPers="+nomPers.getText().toString()+"&prenomPers="+prenomPers.getText().toString()+"&numTel="+numTel.getText().toString()+"&email="+email.getText().toString()+"&mdp="+mdp.getText().toString()+"&adresse="+adresse.getText().toString()+"&rib="+rib.getText().toString()+"&role="+role.getSelectedItem().toString(); 
       
        req.setUrl(url);
       
       
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
          return true;  
    
    }
    //login
   public void login(TextField username,TextField password, Resources rs ) {
        
        
        String url = Statics.BASE_URL+"/login?email="+username.getText().toString()+"&mdp="+password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            try {
            
            if(json.equals("failed")) {
               // Dialog.show("Echec d'authentification","Email ou mot de passe incorrecte","OK",null);
            }
            else {
                System.out.println("data =="+json);
                
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                
             
                //Session 
                float idPers = Float.parseFloat(user.get("idPers").toString());
               SessionManager.setIdPers((int)idPers);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
              SessionManager.setEmail(user.get("email").toString());
                 SessionManager.setMdp(user.get("password").toString());
                
               //photo 
                
                /*if(user.get("photo") != null)
                    SessionManager.setPhoto(user.get("photo").toString());
                
                */
                if(user.size() >0 ) 
                {
                     System.out.println("utilsateur connecté: "+SessionManager.getEmail()+", "+SessionManager.getMdp());
                    Toolbar.setGlobalToolbar(false);
            new WalkthruForm(rs).show();
            Toolbar.setGlobalToolbar(true);
                }
                    
                    }
            
            }catch(Exception ex) {
                Dialog.show("Echec d'authentification","Email ou mot de passe incorrecte","OK",null);
              }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    } 
   
   
    //Update 
    public boolean modifierUser(Personne p,int id) {
        String url = Statics.BASE_URL +"/updateStudentJSON/"+p.getId_pers()+"?nomPers="+p.getNom_pers()+"&prenomPers="+p.getPrenom_pers()+"&numTel="+p.getNum_tel()+"&email="+p.getEmail()+"&mdp="+p.getMdp()+"&adresse="+p.getAdresse()+"&rib="+p.getRib()+"&role="+p.getRole(); 
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    public void AddP(String i,int id){
        System.out.println("id: "+id+"nom image: "+i);
        String url = Statics.BASE_URL+"/addImageJSON?idPers="+id+"&last="+i+"&imagep="+i;
       
        req.setUrl(url);
       
        //Control saisi
        if(i.equals("") ) {
            
            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
           
        }
        else{
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    }
    
    public int getCodeByEmail(String email, Resources res){
        
               String url = Statics.BASE_URL +"/getcode?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
          
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
             //jsons = new int(req.getResponseData()) + "";
            
            
            try {
            
         
                System.out.println("data =="+json);
                
               Map<String, Object> response = j.parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
            id = (int) response.get("idPers");
            
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    return id;
    }
    
    //send mail
    public boolean sendMail(String email) {

        String url = Statics.BASE_URL+"/sendMail?email=" + email;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            ServicePersonne ser = new ServicePersonne();
            response = new String(con.getResponseData());
            System.out.println("response : " + response);
            x++;
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        if(x==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean verifcode(String code,String email) {

        String url = Statics.BASE_URL+"/verifcode?email=" + email+"token="+code;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            ServicePersonne ser = new ServicePersonne();
            response = new String(con.getResponseData());
            System.out.println("response : " + response);
             y++;
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        if(y==1){
            return false;
        }
        else{
            return true;
        }
    }
     public boolean changer(String mdp,String email) {

        String url = Statics.BASE_URL+"/changer?email=" + email+"&mdp="+mdp;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            ServicePersonne ser = new ServicePersonne();
            response = new String(con.getResponseData());
            System.out.println("response : " + response);
            z++;
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        if(z==1){
            return false;
        }
        else{
            return true;
        }
    }
}

