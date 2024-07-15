package com.formenshop.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.formenshop.Adapters.ViewProductsAdapter;
import com.formenshop.Adapters.ViewProductsGridAdapter;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;
import com.formenshop.databinding.ActivityViewProductListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProductListActivity extends AppCompatActivity {

    private String type;
    private ActivityViewProductListBinding binding;
    private ArrayList<ProductsModel> products;
    private ArrayList<ProductsModel> categoryList;
    private ViewProductsAdapter productAdapter;
    private ViewProductsGridAdapter gridAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ArrayLists
        products = new ArrayList<>();
        categoryList = new ArrayList<>();

        // Get the type from the intent
        type = getIntent().getStringExtra("type");

        // Initialize ApiService
        apiService = ApiClient.getApiService(this);

        // Setup RecyclerView and adapters based on the type
        setupRecyclerView();

        // Set padding for the main view to accommodate system bars
        View mainView = binding.getRoot();
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Setup RecyclerView and adapters
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.allProductsView.setLayoutManager(linearLayoutManager);

        if (type != null) {
            if (type.equalsIgnoreCase("all")) {
                loadAllProducts();
            } else if (type.equalsIgnoreCase("category")) {
                getCategoryProducts();
                gridAdapter = new ViewProductsGridAdapter(this, categoryList, this::onProductClicked);
                binding.allProductsView.setAdapter(gridAdapter);
            }
        }
    }

    private void loadAllProducts() {
        apiService.getAllProduct().enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.clear();
                    for (ProductsModel product : response.body()) {
                        products.add(new ProductsModel(
                                product.getId(),
                                product.getPrice(),
                                product.getProductName(),
                                product.getThumbnail(),
                                product.getDescription(),
                                product.getInventory(),
                                product.getCategoryId()
                        ));
                    }
                    productAdapter = new ViewProductsAdapter(ViewProductListActivity.this,
                            products, ViewProductListActivity.this::onProductClicked);
                    binding.allProductsView.setAdapter(productAdapter);
                } else {
                    Toast.makeText(ViewProductListActivity.this, "Failed to load products",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                Toast.makeText(ViewProductListActivity.this, "Failed to load products: "
                        + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get products method (if needed)
    private void getProducts() {
        products.clear(); // Clear existing products

//        // Add hardcoded products
//        products.add(new ProductsModel("Product 1", "100.000 đ", "Description 1", R.drawable.img2));
//        products.add(new ProductsModel("Product 2", "100.000 đ", "Description 2", R.drawable.img2a));
//        products.add(new ProductsModel("Product 3", "100.000 đ", "Description 3", R.drawable.img2b));
//        products.add(new ProductsModel("Product 4", "100.000 đ", "Description 4", R.drawable.img2));

        // Notify adapter of data change
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
    }

    // Get category products method
    private void getCategoryProducts() {
        categoryList.clear(); // Clear existing category products

//        if (type.equals("category1")) {
//            categoryList.add(new ProductsModel("Product 1", "100.000 đ", "Description 1", R.drawable.img2));
//            categoryList.add(new ProductsModel("Product 2", "100.000 đ", "Description 2", R.drawable.img2a));
//            categoryList.add(new ProductsModel("Product 3", "100.000 đ", "Description 3", R.drawable.img2b));
//            categoryList.add(new ProductsModel("Product 4", "100.000 đ", "Description 4", R.drawable.img2));
//        } else if (type.equals("category2")) {
//            categoryList.add(new ProductsModel("Product 5", "100.000 đ", "Description 5", R.drawable.img2a));
//            categoryList.add(new ProductsModel("Product 6", "100.000 đ", "Description 6", R.drawable.img2b));
//        }

        // Notify adapter of data change
        if (gridAdapter != null) {
            gridAdapter.notifyDataSetChanged();
        }
    }

    // Handle item click
    private void onProductClicked(ProductsModel product) {
        // Handle click action here, e.g., show product details
    }

}
