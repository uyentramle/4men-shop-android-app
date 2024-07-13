package com.formenshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductsModel implements Parcelable {
    private int id;
    private double price;
    private String productName;
    private String thumbnail;
    private String description;
    private int inventory;
    private int categoryId;

    // Default constructor
    public ProductsModel() {
    }

    // Parameterized constructor
    public ProductsModel(int id, double price, String productName, String thumbnail, String description, int inventory, int categoryId) {
        this.id = id;
        this.price = price;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.inventory = inventory;
        this.categoryId = categoryId;
    }

    protected ProductsModel(Parcel in) {
        id = in.readInt();
        price = in.readDouble();
        productName = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        inventory = in.readInt();
        categoryId = in.readInt();
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>() {
        @Override
        public ProductsModel createFromParcel(Parcel in) {
            return new ProductsModel(in);
        }

        @Override
        public ProductsModel[] newArray(int size) {
            return new ProductsModel[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", inventory=" + inventory +
                ", categoryId=" + categoryId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(price);
        dest.writeString(productName);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeInt(inventory);
        dest.writeInt(categoryId);
    }
}
