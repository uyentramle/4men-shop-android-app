package com.formenshop.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.formenshop.Activities.LoginActivity;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Config.ITokenManager;
import com.formenshop.Config.TokenManager;
import com.formenshop.Request.CartRequest;
import com.formenshop.Response.CartResponse;
import com.formenshop.Response.LoginResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends BottomSheetDialogFragment {
    private static final String TAG = "ProductDetailFragment";

    private Context mContext;
    private ProductsModel productsModel;
    private ImageView imageView, imageView2, like;
    private TextView productName, productPrice, productDesc;

    private ITokenManager tokenManager;
    private int click = 0;

    private ApiService apiService;

    public ProductDetailFragment(Context mContext, ProductsModel productsModel) {
        this.mContext = mContext;
        this.productsModel = productsModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        imageView = view.findViewById(R.id.mainImage);
        imageView2 = view.findViewById(R.id.img1);
        productName = view.findViewById(R.id.pName);
        productPrice = view.findViewById(R.id.pPrice);
        productDesc = view.findViewById(R.id.pDesc);
        like = view.findViewById(R.id.like);
        apiService = ApiClient.getApiService(getContext());
        tokenManager = new TokenManager(getActivity().getApplicationContext());

        like.setOnClickListener(v -> {
            if (click == 0) {
                like.setImageResource(R.drawable.heart_filled2);
                click++;
                CartRequest cartRequest = new CartRequest();
                cartRequest.setProductId(productsModel.getId());  // Use the product ID from the model
                cartRequest.setQuantity(1);
                addToCart(cartRequest);
            } else {
                like.setImageResource(R.drawable.heart2);
                click--;
                Toast.makeText(mContext, "Removed from cart", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Removed from cart");
            }
        });

        productName.setText(productsModel.getProductName());
        productPrice.setText(productsModel.getPrice()+"");
        productDesc.setText(productsModel.getDescription());
        Picasso.get().load(productsModel.getThumbnail()).into(imageView);
        Picasso.get().load(productsModel.getThumbnail()).into(imageView2);



        Log.d(TAG, "Product details loaded: " + productsModel);

        return view;
    }


    private void addToCart(CartRequest cartRequest) {

        apiService.addCart(cartRequest).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(mContext, "Please,log in before", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to add to cart: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable throwable) {
                Toast.makeText(mContext, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error adding to cart", throwable);
            }
        });

    }
    private void redirectToLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }
}
