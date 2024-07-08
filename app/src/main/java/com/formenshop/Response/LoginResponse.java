package com.formenshop.Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")

    String token ;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
