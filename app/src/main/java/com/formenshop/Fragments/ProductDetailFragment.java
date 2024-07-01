package com.formenshop.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ImageView imageView, imageView2, like;
    private TextView productName, productPrice, productDesc;
    private int click = 0;

    public ProductDetailFragment(Context mContext, TrendingProducts trendingProducts) {
        this.mContext = mContext;
        this.trendingProducts = trendingProducts;
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
}
