package com.formenshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CartModels implements Parcelable {
    private int productId;
    private int quantity;
    private double price;
    private String productName;
    private String thumbnail;
    private int inventory;

    public CartModels(int productId, int quantity, double price, String productName, String thumbnail, int inventory) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.inventory = inventory;
    }

    protected CartModels(Parcel in) {
        productId = in.readInt();
        quantity = in.readInt();
        price = in.readDouble();
        productName = in.readString();
        thumbnail = in.readString();
        inventory = in.readInt();
    }

    public static final Creator<CartModels> CREATOR = new Creator<CartModels>() {
        @Override
        public CartModels createFromParcel(Parcel in) {
            return new CartModels(in);
        }

        @Override
        public CartModels[] newArray(int size) {
            return new CartModels[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeString(productName);
        dest.writeString(thumbnail);
        dest.writeInt(inventory);
    }

}
