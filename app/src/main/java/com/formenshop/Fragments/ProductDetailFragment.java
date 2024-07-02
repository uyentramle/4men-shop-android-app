package com.formenshop.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

public class ProductDetailFragment extends BottomSheetDialogFragment {
    private Context mContext;
    private ProductsModel productsModel;
    private ImageView imageView, imageView2, like;
    private TextView productName, productPrice, productDesc;
    private int click = 0;

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

        productName.setText(productsModel.getName());
        productPrice.setText(productsModel.getPrice());
        productDesc.setText(productsModel.getDescription());

        Picasso.get().load(productsModel.getImage()).into(imageView);
        Picasso.get().load(productsModel.getImage()).into(imageView2);

        return view;
    }
}
