package com.example.grocery;

import android.graphics.drawable.GradientDrawable;

public class Groceries_model {
    String image,place;
    String grain_title;


    public Groceries_model() {
    }

    public Groceries_model(String image, String place, String grain_title) {
        this.image = image;
        this.place = place;
        this.grain_title = grain_title;
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

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGrain_title() {
        return grain_title;
    }

    public void setGrain_title(String grain_title) {
        this.grain_title = grain_title;
    }
}
