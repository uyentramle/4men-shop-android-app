package com.formenshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.formenshop.Activities.ViewProductListActivity;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoriesModel> categoriesList;

    public CategoriesAdapter(Context context, ArrayList<CategoriesModel> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for categories
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesModel category = categoriesList.get(position);
        holder.categoryName.setText(category.getCName());
        Glide.with(context).load(category.getCImage()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event on category item
                Intent intent = new Intent(context, ViewProductListActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", category.getCName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
