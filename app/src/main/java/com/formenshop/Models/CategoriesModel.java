package com.formenshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoriesModel implements Parcelable {
    private String name;
    private String image;

    // Constructor, getters, and setters
    public CategoriesModel(String image, String name) {
        this.image = image;
        this.name = name;
    }

    protected CategoriesModel(Parcel in) {
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<CategoriesModel> CREATOR = new Creator<CategoriesModel>() {
        @Override
        public CategoriesModel createFromParcel(Parcel in) {
            return new CategoriesModel(in);
        }

        @Override
        public CategoriesModel[] newArray(int size) {
            return new CategoriesModel[size];
        }
    };

    public String getCName() {
        return name;
    }

    public String getCImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
    }
}
