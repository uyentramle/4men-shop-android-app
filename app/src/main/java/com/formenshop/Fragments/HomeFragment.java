package com.formenshop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Activities.MapShopActivity;
import com.formenshop.Activities.ViewProductListActivity;
import com.formenshop.Adapters.CategoriesAdapter;
import com.formenshop.Adapters.ViewProductsAdapter;
import com.formenshop.Adapters.ViewProductsGridAdapter;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ImageView go_shop;
    private ApiService apiService;

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

        // Initialize ArrayLists
        categoriesList = new ArrayList<>();
        newProductList = new ArrayList<>();
        productsAcrossVNList = new ArrayList<>();
        bestSellingList = new ArrayList<>();

        apiService = ApiClient.getApiService(getContext());

        // Check if arguments are available and fetch if not null
        if (getArguments() != null) {
            categoriesList = getArguments().getParcelableArrayList(ARG_CATEGORIES);
        }

        // Make API calls to fetch data for new products and best selling products
        fetchNewProducts();
        fetchProductsAcrossVN();
        fetchBestSellingProducts();
    }

    private void fetchNewProducts() {
        apiService.getProductTrend().enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductsModel> productList = response.body();

                    // Convert List<Product> to ArrayList<ProductsModel>
                    ArrayList<ProductsModel> convertedList = new ArrayList<>();
                    for (ProductsModel product : productList) {
                        ProductsModel model = new ProductsModel(
                                product.getId(),
                                product.getPrice(),
                                product.getProductName(),
                                product.getThumbnail(),
                                product.getDescription(),
                                product.getInventory(),
                                product.getCategoryId()
                        );
                        convertedList.add(model);
                    }
                    newProductList.clear();
                    newProductList.addAll(convertedList);

                    // Update RecyclerView adapter for new products view
                    if (mAdapter1 != null) {
                        mAdapter1.updateData(convertedList);
                    } else {
                        if (newProductList != null && !newProductList.isEmpty()) {
                            mAdapter1 = new ViewProductsGridAdapter(getContext(), newProductList, product -> {
                                // Ensure correct arguments are passed to ProductDetailFragment constructor
                                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
                            });
                            mNewProductView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            mNewProductView.setAdapter(mAdapter1);
                        }
                    }
                } else {
                    Log.e("API Call", "Failed to get new products data");
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable throwable) {
                Log.e("API Call", "Failed to fetch new products", throwable);
            }
        });
    }

    private void fetchProductsAcrossVN() {
        apiService.getProductTrend().enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductsModel> productList = response.body();

                    // Convert List<Product> to ArrayList<ProductsModel>
                    ArrayList<ProductsModel> convertedList = new ArrayList<>();
                    for (ProductsModel product : productList) {
                        ProductsModel model = new ProductsModel(
                                product.getId(),
                                product.getPrice(),
                                product.getProductName(),
                                product.getThumbnail(),
                                product.getDescription(),
                                product.getInventory(),
                                product.getCategoryId()
                        );
                        convertedList.add(model);
                    }
                    productsAcrossVNList.clear();
                    productsAcrossVNList.addAll(convertedList);

                    // Update RecyclerView adapter for products across VN view
                    if (mAdapter2 != null) {
                        mAdapter2.updateData(convertedList);
                    } else {
                        if (productsAcrossVNList != null && !productsAcrossVNList.isEmpty()) {
                            mAdapter2 = new ViewProductsAdapter(getContext(), productsAcrossVNList, product -> {
                                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
                            });

                            mProductsAcrossVNView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            mProductsAcrossVNView.setAdapter(mAdapter2);
                        }
                    }
                } else {
                    Log.e("API Call", "Failed to get products across VN data");
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable throwable) {
                Log.e("API Call", "Failed to fetch products across VN", throwable);
            }
        });
    }

    private void fetchBestSellingProducts() {
        apiService.getProductSeller().enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductsModel> productList = response.body();

                    // Convert List<Product> to ArrayList<ProductsModel>
                    ArrayList<ProductsModel> convertedList = new ArrayList<>();
                    for (ProductsModel product : productList) {
                        ProductsModel model = new ProductsModel(
                                product.getId(),
                                product.getPrice(),
                                product.getProductName(),
                                product.getThumbnail(),
                                product.getDescription(),
                                product.getInventory(),
                                product.getCategoryId()
                        );
                        convertedList.add(model);
                    }
                    bestSellingList.clear();
                    bestSellingList.addAll(convertedList);

                    // Update RecyclerView adapter for best selling products view
                    if (mAdapter4 != null) {
                        mAdapter4.updateData(convertedList);
                    } else {
                        if (bestSellingList != null && !bestSellingList.isEmpty()) {
                            mAdapter4 = new ViewProductsGridAdapter(getContext(), bestSellingList, product -> {
                                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
                            });
                            mBestSellingView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            mBestSellingView.setAdapter(mAdapter4);
                        }
                    }
                } else {
                    Log.e("API Call", "Failed to get best selling products data");
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable throwable) {
                Log.e("API Call", "Failed to fetch best selling products", throwable);
            }
        });
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
        go_shop = (ImageView)view.findViewById(R.id.go_map);
        // Set up RecyclerViews if data is available
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

        if (productsAcrossVNList != null && !productsAcrossVNList.isEmpty()) {
            mAdapter2 = new ViewProductsAdapter(getContext(), productsAcrossVNList, product -> {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
            });
            mProductsAcrossVNView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mProductsAcrossVNView.setAdapter(mAdapter2);
        }

        if (bestSellingList != null && !bestSellingList.isEmpty()) {
            mAdapter4 = new ViewProductsGridAdapter(getContext(), bestSellingList, product -> {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(getContext(), product);
                productDetailFragment.show(getFragmentManager(), productDetailFragment.getTag());
            });
            mBestSellingView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mBestSellingView.setAdapter(mAdapter4);
        }
        go_shop.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapShopActivity.class);
            startActivity(intent);
        });
        viewAllBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ViewProductListActivity.class);
            intent.putExtra("type", "all");
            startActivity(intent);
        });

        return view;
    }
}
