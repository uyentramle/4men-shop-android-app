package com.formenshop.Api;

import com.formenshop.Config.ITokenManager;
import com.formenshop.Config.TokenManager;
import com.formenshop.Models.Product;
import com.formenshop.Models.ProductsModel;
import com.formenshop.Request.CartRequest;
import com.formenshop.Request.LoginRequest;
import com.formenshop.Response.CartResponse;
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

    @POST("api/Authencation/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/Product/GetProductTrend")
    Call<List<Product>> getProductTrend();

    @GET("api/Product/GetProductSeller")
    Call<List<Product>> getProductSeller();

    @GET("api/Product/getProduct")
    Call<List<Product>> getAllProduct();

    @POST("api/Cart/addCart")
    Call<CartResponse> addCart(@Body CartRequest cartRequest);

}
