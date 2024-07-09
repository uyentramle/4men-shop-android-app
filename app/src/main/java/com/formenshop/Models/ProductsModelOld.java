package com.formenshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductsModelOld implements Parcelable {
    private String name;
    private String price;
    private String description;
    private int image;

    public ProductsModelOld(String name, String price, String description, int image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    protected ProductsModelOld(Parcel in) {
        name = in.readString();
        price = in.readString();
        description = in.readString();
        image = in.readInt();
    }

    public static final Creator<ProductsModelOld> CREATOR = new Creator<ProductsModelOld>() {
        @Override
        public ProductsModelOld createFromParcel(Parcel in) {
            return new ProductsModelOld(in);
        }

        @Override
        public ProductsModelOld[] newArray(int size) {
            return new ProductsModelOld[size];
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
        dest.writeInt(image);
    }
}
