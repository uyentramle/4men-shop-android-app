package com.formenshop.Config;

public interface ITokenManager {
    void saveToken(String token);
    String getToken();
    void clearToken();
}
