package com.formenshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TrendingProducts implements Parcelable {
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

    protected TrendingProducts(Parcel in) {
        name = in.readString();
        price = in.readString();
        description = in.readString();
        image = in.readString();
    }

    public static final Creator<TrendingProducts> CREATOR = new Creator<TrendingProducts>() {
        @Override
        public TrendingProducts createFromParcel(Parcel in) {
            return new TrendingProducts(in);
        }

        @Override
        public TrendingProducts[] newArray(int size) {
            return new TrendingProducts[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(image);
    }
}
