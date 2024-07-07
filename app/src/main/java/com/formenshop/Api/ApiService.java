package com.formenshop.Api;

import com.formenshop.Models.Product;
import com.formenshop.Models.ProductsModel;
import com.formenshop.Request.LoginRequest;
import com.formenshop.Response.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    OkHttpClient client = SSLHelper.getUnsafeOkHttpClient();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5188/") // Ensure the URL ends with a slash
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // ApiService instance
    ApiService apiService = retrofit.create(ApiService.class);
    //Authen
    @POST("api/Authencation/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //Product
    @GET("api/Product/GetProductTrend")
    Call<List<Product>> getProductTrend();

    @GET("api/Product/GetProductSeller")
    Call<List<Product>> getProductSeller();

}
