package com.formenshop.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager implements ITokenManager{
    private SharedPreferences sharedPreferences;
    private static final String TOKEN_KEY = "token";

    public TokenManager() {
    }

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }
    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    @Override
    public String getToken() {
         return sharedPreferences.getString(TOKEN_KEY, "");
    }

    @Override
    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
}
