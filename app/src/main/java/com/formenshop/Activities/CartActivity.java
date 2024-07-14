package com.formenshop.Activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CartAdapter;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.JWT.GetUserID;
import com.formenshop.Models.CartModels;
import com.formenshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemCheckListener {
    ApiService apiService;
    RecyclerView recyclerView;
    ArrayList<CartModels> cartList = new ArrayList<>();
    CartAdapter cartAdapter;
    TextView tvTotalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cartView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTotalMoney = findViewById(R.id.tvTotalMoney);

        apiService = ApiClient.getApiService(this);
        cartAdapter = new CartAdapter(this, cartList, this);
        recyclerView.setAdapter(cartAdapter);

        int userId = GetUserID.getUserIdFromToken(this);
        getCart(userId);
    }

    private void getCart(int userId) {
        apiService.getCart(userId).enqueue(new Callback<List<CartModels>>() {
            @Override
            public void onResponse(Call<List<CartModels>> call, Response<List<CartModels>> response) {
                cartList.addAll(response.body());
                cartAdapter.setCartList(cartList);

            }

            @Override
            public void onFailure(Call<List<CartModels>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public void onItemCheck(double totalPrice) {
        tvTotalMoney.setText(String.valueOf(totalPrice));
    }
}
