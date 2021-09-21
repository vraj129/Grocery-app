package com.example.grocery;

import android.graphics.drawable.GradientDrawable;

public class Groceries_model {
    int image;
    String grain_title;
    GradientDrawable color;

    public Groceries_model(int image, String grain_title, GradientDrawable color) {
        this.image = image;
        this.grain_title = grain_title;
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getGrain_title() {
        return grain_title;
    }

    public void setGrain_title(String grain_title) {
        this.grain_title = grain_title;
    }

    public GradientDrawable getColor() {
        return color;
    }

    public void setColor(GradientDrawable color) {
        this.color = color;
    }
}
