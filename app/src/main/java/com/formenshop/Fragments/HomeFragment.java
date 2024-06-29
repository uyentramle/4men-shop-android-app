package com.formenshop.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CategoriesAdapter;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.TrendingProducts;
import com.formenshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ImageView bannerHome, midBanner1, midBanner2, botBanner;
    private Button signin, viewAllBtn;
    private RecyclerView nearMeView, categoriesView, trendingView, productsAcrossVietNam;

    private CategoriesAdapter mAdapter3;

    private ArrayList<TrendingProducts> trendingList = new ArrayList<>();
    private ArrayList<CategoriesModel> categoriesList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize(view);

        // Load data into views
        loadBanner();
        addCategories();
        loadTrendingData();
        setupAdapters();

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement view all functionality
            }
        });

        return view;
    }

    private void initialize(View view) {
        bannerHome = view.findViewById(R.id.bannerHome);
        midBanner1 = view.findViewById(R.id.midBanner1);
        midBanner2 = view.findViewById(R.id.midBanner2);
        botBanner = view.findViewById(R.id.botBanner);
        signin = view.findViewById(R.id.signin);
        viewAllBtn = view.findViewById(R.id.viewAllBtn);
        nearMeView = view.findViewById(R.id.nearMeView);
        categoriesView = view.findViewById(R.id.categoriesView);
        trendingView = view.findViewById(R.id.trendingView);
        productsAcrossVietNam = view.findViewById(R.id.productsAcrossVietNam);

        bannerHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement banner click functionality
            }
        });
    }

    private void setupAdapters() {
    }

    private void loadBanner() {
        Picasso.get().load(R.drawable.poster1).placeholder(R.drawable.poster1).into(bannerHome);
        Picasso.get().load(R.drawable.img3).placeholder(R.drawable.img3).into(midBanner1);
        Picasso.get().load(R.drawable.poster3).placeholder(R.drawable.poster3).into(midBanner2);
        Picasso.get().load(R.drawable.poster2).placeholder(R.drawable.poster2).into(botBanner);
    }

    private void loadTrendingData() {
        trendingList.clear();
    }

    private void addCategories() {
        categoriesList.clear();
        categoriesList.add(new CategoriesModel("https://m.media-amazon.com/images/I/81oBlS3rKXL._UY575_.jpg", "Jewellery"));
        categoriesList.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41lICpaGo9L._SX300_SY300_QL70_FMwebp_.jpg", "Home Decor"));
        categoriesList.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41wKsI9yrZL._SY300_SX300_QL70_FMwebp_.jpg", "Ayurvedic"));
        categoriesList.add(new CategoriesModel("https://m.media-amazon.com/images/I/911EKUNq1+L._SL1500_.jpg", "Furniture"));
    }
}
