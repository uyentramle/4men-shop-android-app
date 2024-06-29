package com.formenshop.Models;

public class TrendingProducts {
    private String name;
    private String price;
    private String description;
    private String image;

    public TrendingProducts(String name, String price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
