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
import com.formenshop.Models.CartModels;
import com.formenshop.Models.PaymentModels;
import com.formenshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private ArrayList<CartModels> productList;

    public PaymentAdapter( ArrayList<CartModels> productList) {

        this.productList = productList;
    }

    public void setProductList(ArrayList<CartModels> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView nameTextView;
        TextView quantityTextView;
        TextView priceTextView;

        ViewHolder( View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            nameTextView = itemView.findViewById(R.id.product_name);
            quantityTextView = itemView.findViewById(R.id.product_quantity);
            priceTextView = itemView.findViewById(R.id.product_price);
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartModels product = productList.get(position);
        holder.nameTextView.setText(product.getProductName());
        holder.quantityTextView.setText("Quantity: " + product.getQuantity());
        holder.priceTextView.setText("$" + product.getPrice());
        Context context = holder.itemView.getContext();
        Glide.with(context).load(product.getThumbnail()).into(holder.productImageView);
    }

}
