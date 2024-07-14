package com.formenshop.Api;

import com.formenshop.Models.CartModels;
import com.formenshop.Models.ProductsModel;
import com.formenshop.Request.CartRequest;
import com.formenshop.Request.LoginRequest;
import com.formenshop.Request.RegisterRequest;
import com.formenshop.Response.CartCount;
import com.formenshop.Response.CartInfor;
import com.formenshop.Response.CartResponse;
import com.formenshop.Response.LoginResponse;
import com.formenshop.Response.RegisterReponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/Authencation/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/Product/GetProductTrend")
    Call<List<ProductsModel>> getProductTrend();

    @GET("api/Product/GetProductSeller")
    Call<List<ProductsModel>> getProductSeller();

    @GET("api/Product/getProduct")
    Call<List<ProductsModel>> getAllProduct();

    @POST("api/Cart/addCart")
    Call<CartResponse> addCart(@Body CartRequest cartRequest);
    @POST("api/Authencation/regiter")
    Call<RegisterReponse> Register(@Body RegisterRequest register);
    @GET("api/Cart/getCart/{userId}")
    Call<List<CartModels>> getCart(@Path("userId") int userId);

    @GET("api/Cart/countCart/{userID}")
    Call<CartCount> countCart(@Path("userID") int userID);
    @GET("api/Cart/getCart/{userId}")
    Call<CartInfor> totalCart(@Path("userId") int userId);
}
