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
import com.formenshop.R;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private ArrayList<CartModels> items;

    public CheckoutAdapter(ArrayList<CartModels> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void updateItems(ArrayList<CartModels> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView pNameCart, pPriceCart, txtQuantity;
        ImageView pImageCart;

        CheckoutViewHolder(View itemView) {
            super(itemView);
            pNameCart = itemView.findViewById(R.id.pNameCart);
            pPriceCart = itemView.findViewById(R.id.pPriceCart);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            pImageCart = itemView.findViewById(R.id.pImageCart);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        CartModels item = items.get(position);
        holder.pNameCart.setText(item.getProductName());
        holder.pPriceCart.setText(String.format("%.0f VND", item.getPrice()));
        holder.txtQuantity.setText(String.valueOf(item.getQuantity()));

        Context context = holder.itemView.getContext();
        Glide.with(context)
                .load(item.getThumbnail())
                .into(holder.pImageCart);
    }
}