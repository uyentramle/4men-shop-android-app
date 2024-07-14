package com.formenshop.Api;

import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.ProductsModel;
import com.formenshop.Request.CartRequest;
import com.formenshop.Request.LoginRequest;
import com.formenshop.Request.RegisterRequest;
import com.formenshop.Response.CartResponse;
import com.formenshop.Response.LoginResponse;
import com.formenshop.Response.RegisterReponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    Call<CartRequest> addCart(@Body CartRequest cartRequest);

    @POST("api/Authencation/regiter")
    Call<RegisterReponse> Register(@Body RegisterRequest register);

    @GET("api/Category/getCategory")
    Call<List<CategoriesModel>> getAllCategory();
  
    @GET("api/Cart/getCart")
    Call<List<CartResponse>> getCart();



}
