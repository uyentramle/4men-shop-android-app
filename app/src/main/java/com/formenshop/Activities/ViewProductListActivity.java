package com.formenshop.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.R;

import java.util.ArrayList;
import java.util.List;

public class ViewProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_product_list);

//        recyclerView = findViewById(R.id.allProductsView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Thêm dữ liệu mẫu
//        productList = new ArrayList<>();
//        productList.add(new Product("Product 1", "$10", "Description for Product 1", "https://via.placeholder.com/150"));
//        productList.add(new Product("Product 2", "$20", "Description for Product 2", "https://via.placeholder.com/150"));
//        productList.add(new Product("Product 3", "$30", "Description for Product 3", "https://via.placeholder.com/150"));
//
//        productAdapter = new ProductAdapter(this, productList);
//        recyclerView.setAdapter(productAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}