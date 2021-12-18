package com.example.grocery.Model;

public class AllStoreModel {

    String name,image,place;

    public AllStoreModel() {
    }

    public AllStoreModel(String name, String image, String place) {
        this.name = name;
        this.image = image;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String places) {
        this.place = places;
    }
}
