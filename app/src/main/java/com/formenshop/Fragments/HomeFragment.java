package com.formenshop.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CategoriesAdapter;
import com.formenshop.Adapters.ViewProductsAdapter;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.TrendingProducts;
import com.formenshop.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_PRODUCTS = "arg_products";
    private static final String ARG_CATEGORIES = "arg_categories";

    private ArrayList<TrendingProducts> newProductList;
    private ArrayList<CategoriesModel> categoriesList;
    private RecyclerView mNewProductView;
    private RecyclerView mCategoriesView;
    private ViewProductsAdapter mAdapter1;
    private CategoriesAdapter mAdapter3;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(ArrayList<TrendingProducts> products, ArrayList<CategoriesModel> categories) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCTS, products);
        args.putParcelableArrayList(ARG_CATEGORIES, categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newProductList = getArguments().getParcelableArrayList(ARG_PRODUCTS);
            categoriesList = getArguments().getParcelableArrayList(ARG_CATEGORIES);
        }
        if (newProductList == null) {
            newProductList = new ArrayList<>();
        }
        if (categoriesList == null) {
            categoriesList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mNewProductView = view.findViewById(R.id.trendingView);
        mCategoriesView = view.findViewById(R.id.categoriesView);

        if (newProductList != null && !newProductList.isEmpty()) {
            mAdapter1 = new ViewProductsAdapter(getContext(), newProductList);
            mNewProductView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mNewProductView.setAdapter(mAdapter1);
        }

        if (categoriesList != null && !categoriesList.isEmpty()) {
            mAdapter3 = new CategoriesAdapter(getContext(), categoriesList);
            mCategoriesView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mCategoriesView.setAdapter(mAdapter3);
        }

        return view;
    }
}