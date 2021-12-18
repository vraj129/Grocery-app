package com.example.grocery;

public class Exclusive_model {

    String fruit_title,place;
    String image;

    public Exclusive_model() {
    }

    public Exclusive_model(String fruit_title, String place, String image) {
        this.fruit_title = fruit_title;
        this.place = place;
        this.image = image;
    }

    public String getFruit_title() {
        return fruit_title;
    }

    public void setFruit_title(String fruit_title) {
        this.fruit_title = fruit_title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
