package com.formenshop.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Api.ApiService;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.R;

import java.util.List;

public class CateroryActivity extends AppCompatActivity {
    private ApiService apiService;
    List<CategoriesModel> categories;

    RecyclerView recyclerView;

    TextView tvCateName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_categories);
        tvCateName = findViewById(R.id.categoryName);
    }
}
