package com.formenshop.Api;

import android.content.Context;

import com.formenshop.Config.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

        OkHttpClient client = SSLHelper.getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor(token)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5188/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiService.class);
    }

}
