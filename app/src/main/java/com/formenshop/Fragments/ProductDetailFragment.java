package com.formenshop.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.formenshop.Models.TrendingProducts;
import com.formenshop.R;

public class ProductDetailFragment extends BottomSheetDialogFragment {
    private Context mContext;
    private TrendingProducts trendingProducts;
    private ArrayList<TrendingProducts> savedProducts = new ArrayList<>();
    private ImageView imageView, imageView2, like;
    private Button buyBtn, report;
    private RelativeLayout parent;
    private CardView amazonIcon;
    private int click = 0;
    private TextView productName, productPrice, productDesc;

    public ProductDetailFragment(Context mContext, TrendingProducts trendingProducts) {
        this.mContext = mContext;
        this.trendingProducts = trendingProducts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container);

        imageView = view.findViewById(R.id.mainImage);
        imageView2 = view.findViewById(R.id.img1);
        productName = view.findViewById(R.id.pName);
        productPrice = view.findViewById(R.id.pPrice);
        parent = view.findViewById(R.id.parent);
        productDesc = view.findViewById(R.id.pDesc);
        like = view.findViewById(R.id.like);
        buyBtn = view.findViewById(R.id.buyBtn);
        amazonIcon = view.findViewById(R.id.amazonIcon);

        like.setOnClickListener(v -> {
            if (click == 0) {
                like.setImageResource(R.drawable.heart_filled2);
                click++;
                Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                like.setImageResource(R.drawable.heart2);
                click--;
                Toast.makeText(mContext, "Removed from cart", Toast.LENGTH_SHORT).show();
            }
        });

        productName.setText(trendingProducts.getName());
        productPrice.setText(trendingProducts.getPrice());
        productDesc.setText(trendingProducts.getDescription());

        Picasso.get().load(trendingProducts.getImage()).into(imageView);
        Picasso.get().load(trendingProducts.getImage()).into(imageView2);
        return view;
    }

    private ArrayList<TrendingProducts> getSampleProductHistory() {
        ArrayList<TrendingProducts> sampleHistory = new ArrayList<>();
        sampleHistory.add(new TrendingProducts("Sample Product 1", "100.000 đ", "Description 1", "https://example.com/image1.jpg"));
        sampleHistory.add(new TrendingProducts("Sample Product 2", "200.000 đ", "Description 2", "https://example.com/image2.jpg"));
        sampleHistory.add(new TrendingProducts("Sample Product 3", "300.000 đ", "Description 3", "https://example.com/image3.jpg"));
        sampleHistory.add(new TrendingProducts("Sample Product 4", "400.000 đ", "Description 4", "https://example.com/image4.jpg"));
        return sampleHistory;
    }
}
