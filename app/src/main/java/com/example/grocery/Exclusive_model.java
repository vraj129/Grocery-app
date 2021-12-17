package com.example.grocery;

public class Exclusive_model {

    String fruit_title;
    String image;

    public Exclusive_model() {
    }

    public Exclusive_model(String image, String fruit_title) {
        this.image = image;
        this.fruit_title = fruit_title;

    }

    public String getFruit_title() {
        return fruit_title;
    }

    public void setFruit_title(String fruit_title) {
        this.fruit_title = fruit_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
