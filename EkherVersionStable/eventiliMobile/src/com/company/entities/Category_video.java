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
public class Category_video {

    private int id;
    private String title;
    private String image_path;
    private String creat_at;

    public Category_video() {
    }

    public Category_video(int id, String title, String image_path, String creat_at) {
        this.id = id;
        this.title = title;
        this.image_path = image_path;
        this.creat_at = creat_at;
    }

    public Category_video(String title, String image_path, String creat_at) {
        this.title = title;
        this.image_path = image_path;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    @Override
    public String toString() {
        return "Category_video{" + "id=" + id + ", title=" + title + ", image_path=" + image_path + ", creat_at=" + creat_at + '}';
    }

}
