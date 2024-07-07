package com.formenshop.Api;

import android.content.Context;

import com.formenshop.Config.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiService apiService;

    public static ApiService getApiService(Context context) {
        if (apiService == null) {
            apiService = createApiService(context);
        }
        return apiService;
    }

    private static ApiService createApiService(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

        // Get token from TokenManager
        TokenManager tokenManager = new TokenManager(context);
        String token = tokenManager.getToken();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(token))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5188/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiService.class);
    }
}
