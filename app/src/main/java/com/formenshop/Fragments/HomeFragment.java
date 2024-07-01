package com.formenshop.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CategoriesAdapter;
import com.formenshop.Adapters.ViewProductsAdapter;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.TrendingProducts;
import com.formenshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View mMainView;
    RecyclerView mNewProductView, mPopularProductView, mBestSellerProductView, mCategoriesView;
    NestedScrollView nestedScrollView;
    ViewProductsAdapter mAdapter1;
    ViewProductsAdapter mAdapter2;
    CategoriesAdapter mAdapter3;

    ArrayList<TrendingProducts> newProductList = new ArrayList<>();
    ArrayList<TrendingProducts> popularProductList = new ArrayList<>();
    ArrayList<TrendingProducts> bestSellerProductList = new ArrayList<>();
    ArrayList<CategoriesModel> categoriesList = new ArrayList<>();
    ArrayList<TrendingProducts> allProducts = new ArrayList<>();

    private ImageView bannerHome, midBanner1, midBanner2, botBanner;
    private Button signin, viewAllBtn;
    private RecyclerView nearMeView, categoriesView, trendingView, productsAcrossVietNam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {
        mNewProductView = (RecyclerView) mMainView.findViewById(R.id.trendingView);
        mPopularProductView = (RecyclerView) mMainView.findViewById(R.id.productsAcrossVietNam);
        mCategoriesView = (RecyclerView) mMainView.findViewById(R.id.categoriesView);
        mBestSellerProductView = (RecyclerView) mMainView.findViewById(R.id.bestSellerProduct);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();

        Handler handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //categories adapter
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    mCategoriesView.setLayoutManager(linearLayoutManager2);

                    addCategories();
                    mAdapter3 = new CategoriesAdapter(getContext(), categoriesList);
                    mCategoriesView.setAdapter(mAdapter3);
                }
            }, 1000);
        }

        return mMainView;
    }

    private void addCategories() {
        categoriesList.clear();
        categoriesList.add(new CategoriesModel("https://m.media-amazon.com/images/I/81oBlS3rKXL._UY575_.jpg", "Jewellery"));
        categoriesList.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41lICpaGo9L._SX300_SY300_QL70_FMwebp_.jpg", "Home Decor"));
        categoriesList.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41wKsI9yrZL._SY300_SX300_QL70_FMwebp_.jpg", "Ayurvedic"));
        categoriesList.add(new CategoriesModel("https://m.media-amazon.com/images/I/911EKUNq1+L._SL1500_.jpg", "Furniture"));
    }

//    private void initialize(View view) {
//        bannerHome = view.findViewById(R.id.bannerHome);
//        midBanner1 = view.findViewById(R.id.midBanner1);
//        midBanner2 = view.findViewById(R.id.midBanner2);
//        botBanner = view.findViewById(R.id.botBanner);
//        signin = view.findViewById(R.id.signin);
//        viewAllBtn = view.findViewById(R.id.viewAllBtn);
//        nearMeView = view.findViewById(R.id.nearMeView);
//        categoriesView = view.findViewById(R.id.categoriesView);
//        trendingView = view.findViewById(R.id.trendingView);
//        productsAcrossVietNam = view.findViewById(R.id.productsAcrossVietNam);
//
//        bannerHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Implement banner click functionality
//            }
//        });
//    }

    private void loadBanner() {
        Picasso.get().load(R.drawable.poster1).placeholder(R.drawable.poster1).into(bannerHome);
        Picasso.get().load(R.drawable.img3).placeholder(R.drawable.img3).into(midBanner1);
        Picasso.get().load(R.drawable.poster3).placeholder(R.drawable.poster3).into(midBanner2);
        Picasso.get().load(R.drawable.poster2).placeholder(R.drawable.poster2).into(botBanner);
    }


}
