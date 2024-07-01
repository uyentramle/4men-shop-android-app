package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoriesModel categoriesModel = categoriesList.get(position);
        holder.categoryName.setText(categoriesModel.getName());
        Glide.with(context).load(categoriesModel.getImage()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}