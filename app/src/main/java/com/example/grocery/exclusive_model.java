package com.example.grocery;

public class exclusive_model {
    int image;
    String fruit_title,price_title;

    public exclusive_model(int image, String fruit_title, String price_title) {
        this.image = image;
        this.fruit_title = fruit_title;
        this.price_title = price_title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFruit_title() {
        return fruit_title;
    }

    public void setFruit_title(String fruit_title) {
        this.fruit_title = fruit_title;
    }

    public String getPrice_title() {
        return price_title;
    }

    public void setPrice_title(String price_title) {
        this.price_title = price_title;
    }
}
