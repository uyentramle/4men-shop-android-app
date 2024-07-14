package com.formenshop.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.R;
import com.formenshop.Request.CartRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        CartRequest cartRequest = new CartRequest();
        addProductToCart(cartRequest);
    }

    private void addProductToCart(CartRequest cartRequest) {
        apiService.addCart(cartRequest).enqueue(new Callback<CartRequest>() {
            @Override
            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartRequest> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
