package com.formenshop.Activities;

import static com.formenshop.Api.ApiClient.apiService;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.formenshop.Request.CartRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemCheckListener {
    ApiService apiService;
    RecyclerView recyclerView;
    ArrayList<CartModels> cartList = new ArrayList<>() ;
    CartAdapter cartAdapter;
    TextView tvTotalMoney;

    Button buttonCheckout;




    ImageView btnAddQuantity, btnMinusQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cartView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        buttonCheckout = findViewById(R.id.btnCheckout1);

        apiService = ApiClient.getApiService(this);
        cartAdapter = new CartAdapter(this, cartList, this);
        recyclerView.setAdapter(cartAdapter);

        int userId = GetUserID.getUserIdFromToken(this);
        getCart(userId);
//        buttonCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CartActivity.this, SuccessActivity.class);
//                startActivity(intent);
//            }

        buttonCheckout.setOnClickListener(v -> {
            goToCheckout();
        });
    }

    private void goToCheckout() {
        ArrayList<CartModels> selectedItems = cartAdapter.getCheckoutList();

        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
        //intent.putParcelableArrayListExtra("selectedItems",  selectedItems);
        intent.putParcelableArrayListExtra("selectedItems", selectedItems);
        intent.putExtra("totalPrice", cartAdapter.getTotalPrice());
        startActivity(intent);
    }

    public void deleteProductFromCart(int productId, CartModels position,int userID) {
        apiService.deleteCart(userID,productId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body() == true) {

                    cartList.remove(position);
                    cartAdapter.setCartList(cartList);
                    cartAdapter.notifyDataSetChanged();

                    Toast.makeText(CartActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
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
