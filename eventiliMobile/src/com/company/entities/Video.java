/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.entities;

/**
 *
 * @author ahmed
 */
public class Video {

    private int id;
    private String title;
    private String description;
    private String image_path;
    private String video_path;
    private String duration;
    private Category_video categ;
    private String creat_at;

    public Video() {
    }

    public Video(int id, String title, String description, String image_path, String video_path, String duration, Category_video categ_v, String creat_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.video_path = video_path;
        this.duration = duration;
        this.categ = categ_v;
        this.creat_at = creat_at;
    }

    public Video(String title, String description, String image_path, String video_path, String duration) {
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.video_path = video_path;
        this.duration = duration;
    }

    public Video(String title, String description, String image_path, String video_path, String duration, Category_video categ_v, String creat_at) {

        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.video_path = video_path;
        this.duration = duration;
        this.categ = categ_v;
        this.creat_at = creat_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Category_video getCateg() {
        return categ;
    }

    public void setCateg(Category_video categ) {
        this.categ = categ;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", title=" + title + ", description=" + description + ", image_path=" + image_path + ", video_path=" + video_path + ", duration=" + duration + ", id_categ=" + categ.getId() +'}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Video other = (Video) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
