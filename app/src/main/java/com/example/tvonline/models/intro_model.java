package com.example.tvonline.models;

public class intro_model {

    String title ;

    String description;

    int img_id;

    public intro_model(String title, String description, int img_id) {
        this.title = title;
        this.description = description;
        this.img_id = img_id;
    }

    public intro_model() {}

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

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
