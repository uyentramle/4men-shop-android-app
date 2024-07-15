package com.formenshop.Models;

public class PaymentModels {
    private String imageUrl;
    private String name;
    private int quantity;
    private double price;

    public PaymentModels(String imageUrl, String name, int quantity, double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
