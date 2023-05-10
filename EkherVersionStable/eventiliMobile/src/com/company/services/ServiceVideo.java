/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import com.company.entities.Category_video;
import com.company.entities.Video;

import com.company.utils.Statics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ahmed
 */
public class ServiceVideo {

    public static ServiceVideo instance = null;
    private ArrayList<Category_video> categories = new ArrayList<Category_video>();

    public static boolean resultOk = true;

    private ConnectionRequest req;

    public static ServiceVideo getInstance() {
        if (instance == null) {
            instance = new ServiceVideo();
        }
        return instance;
    }

    public ServiceVideo() {
        req = new ConnectionRequest();

    }

    public void ajoutVideo(Video video) {

        String url = Statics.BASE_URL + "/admin/video/addVideoJSON/new?title=" + video.getTitle() + "&description=" + video.getDescription() + "&image_path=" + video.getImage_path() + "&video_path=" + video.getVideo_path() + "&duration=" + video.getDuration() + "&id_categ=" + video.getCateg().getId(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public ArrayList<Video> affichageVideos() {
//        categories = new ArrayList<Category_video>();
        ArrayList<Video> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/admin/video/allvideos";
        req.setUrl(url);
        System.out.println(req.getUrl());
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapVideo = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapVideo.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Video v = new Video();

                        float id = Float.parseFloat(obj.get("idV").toString());
                        System.out.println("idV " + (int) id);
                        String title = obj.get("title").toString();
                        String description = obj.get("description").toString();
                        String image_path = obj.get("imagePath").toString();
                        String video_path = obj.get("videoPath").toString();
                        String duration = obj.get("duration").toString();
//                        String id_categ = obj.get("idCateg").toString();

                        v.setId((int) id);
                        v.setTitle(title);
                        v.setDescription(description);
                        v.setImage_path(image_path);
                        v.setVideo_path(video_path);
                        v.setDuration(duration);
//                        v.setDescription(id_categ);

                        String DateConverter = obj.get("creationDate").toString().substring(obj.get("creationDate").toString().indexOf("timestamp") + 10, obj.get("creationDate").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        v.setCreat_at(dateString);

                        result.add(v);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }

    public ArrayList<Category_video> getCategories() {
        return categories;
    }

    public void findAllCategories() {
//        categories = new ArrayList<Category_video>();
        String url = Statics.BASE_URL + "/admin/video/AllCategories";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    //System.out.println(mapReclamations);
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamations.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Category_video category = new Category_video();

                        //
                        float id = Float.parseFloat(obj.get("idCateg").toString());
                        System.out.println("idcat " + (int) id);
                        String title = obj.get("title").toString();
                        String imageUrl = obj.get("imageUrl").toString();

                        category.setId((int) id);
                        category.setTitle(title);
                        category.setImage_path(imageUrl);

                        String DateConverter = obj.get("createDate").toString().substring(obj.get("createDate").toString().indexOf("timestamp") + 10, obj.get("createDate").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        category.setCreat_at(dateString);

                        // Add the category to the list of categories
                        categories.add(category);

//                        System.out.println(categories);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public Video DetailVideo(int id, Video v) {

        String url = Statics.BASE_URL + "/admin/video/video/" + id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {

                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                String title = obj.get("title").toString();
                String description = obj.get("description").toString();
                String image_path = obj.get("imagePath").toString();
                String video_path = obj.get("videoPath").toString();
                String duration = obj.get("duration").toString();
                String id_categ = obj.get("idCateg").toString();

                v.setId((int) id);
                v.setTitle(title);
                v.setDescription(description);
                v.setDescription(image_path);
                v.setDescription(video_path);
                v.setDescription(duration);
                v.setDescription(id_categ);

            } catch (IOException ex) {
                System.out.println("error related to sql :( " + ex.getMessage());
            }

            System.out.println("data === " + str);

        }));

        NetworkManager.getInstance().addToQueueAndWait(req);

        return v;

    }

    public boolean deleteVideo(int id) {
        String url = Statics.BASE_URL + "/admin/video/deleteVideoJSON/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean modifierVideo(Video video) {

        String url = Statics.BASE_URL + "/admin/video/updateVideo/video?idV=" + video.getId() + "&title=" + video.getTitle() + "&description=" + video.getDescription() + "&image_path=" + video.getImage_path() + "&video_path=" + video.getVideo_path() + "&duration=" + video.getDuration() + "&id_categ=" + video.getCateg().getId(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;  // Code respons
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

}
