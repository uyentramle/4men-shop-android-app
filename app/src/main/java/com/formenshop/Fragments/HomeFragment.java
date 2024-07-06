package com.formenshop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Activities.ViewProductListActivity;
import com.formenshop.Adapters.CategoriesAdapter;
import com.formenshop.Adapters.ViewProductsAdapter;
import com.formenshop.Adapters.ViewProductsGridAdapter;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_CATEGORIES = "arg_categories";
    private static final String ARG_PRODUCTS = "arg_products";

    private ArrayList<CategoriesModel> categoriesList;
    private ArrayList<ProductsModel> newProductList;
    private ArrayList<ProductsModel> productsAcrossVNList;
    private ArrayList<ProductsModel> bestSellingList;

    private RecyclerView mCategoriesView;
    private RecyclerView mNewProductView;
    private RecyclerView mProductsAcrossVNView;
    private RecyclerView mBestSellingView;

    private CategoriesAdapter mAdapter3;
    private ViewProductsGridAdapter mAdapter1;
    private ViewProductsAdapter mAdapter2;
    private ViewProductsGridAdapter mAdapter4;

    Button viewAllBtn;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(ArrayList<ProductsModel> products, ArrayList<CategoriesModel> categories) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CATEGORIES, categories);
        args.putParcelableArrayList(ARG_PRODUCTS, products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoriesList = getArguments().getParcelableArrayList(ARG_CATEGORIES);
            newProductList = getArguments().getParcelableArrayList(ARG_PRODUCTS);
            productsAcrossVNList = getArguments().getParcelableArrayList(ARG_PRODUCTS);
            bestSellingList = getArguments().getParcelableArrayList(ARG_PRODUCTS);
        }
        if (categoriesList == null) {
            categoriesList = new ArrayList<>();
        }
        if (newProductList == null) {
            newProductList = new ArrayList<>();
        }
        if (productsAcrossVNList == null) {
            productsAcrossVNList = new ArrayList<>();
        }
        if (bestSellingList == null) {
            bestSellingList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mCategoriesView = view.findViewById(R.id.categoriesView);
        mNewProductView = view.findViewById(R.id.trendingView);
        mProductsAcrossVNView = view.findViewById(R.id.productsAcrossVNView);
        mBestSellingView = view.findViewById(R.id.bestSellerProductsView);
        viewAllBtn = view.findViewById(R.id.viewAllBtn);

        if (categoriesList != null && !categoriesList.isEmpty()) {
            mAdapter3 = new CategoriesAdapter(getContext(), categoriesList);
            mCategoriesView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mCategoriesView.setAdapter(mAdapter3);
        }

        if (newProductList != null && !newProductList.isEmpty()) {
            mAdapter1 = new ViewProductsGridAdapter(getContext(), newProductList, product -> {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
            });
            mNewProductView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mNewProductView.setAdapter(mAdapter1);
        }

        if (mProductsAcrossVNView != null && !productsAcrossVNList.isEmpty()) {
//            mAdapter2 = new ViewProductsAdapter(getContext(), productsAcrossVNList);
            mAdapter2 = new ViewProductsAdapter(getContext(), productsAcrossVNList, product -> {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
            });
            mProductsAcrossVNView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mProductsAcrossVNView.setAdapter(mAdapter2);
        }

        if (mBestSellingView != null && !bestSellingList.isEmpty()) {
            mAdapter4 = new ViewProductsGridAdapter(getContext(), bestSellingList, product -> {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
            });
            mBestSellingView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mBestSellingView.setAdapter(mAdapter4);
        }

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewProductListActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
            }
        });

        return view;
    }
}