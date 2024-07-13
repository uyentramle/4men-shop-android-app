package com.formenshop.Response;

import com.google.gson.annotations.SerializedName;

public class CartResponse {
    @SerializedName("message")
    String message;

    public CartResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
