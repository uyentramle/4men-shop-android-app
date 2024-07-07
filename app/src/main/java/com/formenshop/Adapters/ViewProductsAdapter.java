package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;

public class ViewProductsAdapter extends RecyclerView.Adapter<ViewProductsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productList;

    public ViewProductsAdapter(Context context, ArrayList<ProductsModel> productList) {
        this.context = context;
        this.productList = productList;
    }
    public void updateData(ArrayList<ProductsModel> newList) {
        productList.clear();
        productList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.pName);
            productPrice = itemView.findViewById(R.id.pPrice);
            productDescription = itemView.findViewById(R.id.pDesc);
            productImage = itemView.findViewById(R.id.pImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsModel product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productDescription.setText(product.getDescription());
        Glide.with(context).load(product.getImage()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}