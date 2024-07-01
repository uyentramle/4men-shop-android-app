package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.TrendingProducts;
import com.formenshop.R;

import java.util.List;

public class ViewProductsAdapter extends RecyclerView.Adapter<ViewProductsAdapter.ViewHolder> {
    private Context context;
    private List<TrendingProducts> productList;

    public ViewProductsAdapter(Context context, List<TrendingProducts> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.apdapter_view_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingProducts product = productList.get(position);
        // Bind product data to view holder
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
        }
    }
}