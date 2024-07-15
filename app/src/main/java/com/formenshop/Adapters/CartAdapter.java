package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.formenshop.Models.CartModels;
import com.formenshop.R;
import com.formenshop.Response.CartCount;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private ArrayList<CartModels> cartList;
    private List<Boolean> selectedItems;
    private OnItemCheckListener onItemCheckListener;


    public CartAdapter(Context context, ArrayList<CartModels> cartList, OnItemCheckListener onItemCheckListener) {
        this.context = context;
        this.cartList = cartList;
        this.selectedItems = new ArrayList<>(cartList.size());
        for (int i = 0; i < cartList.size(); i++) {
            selectedItems.add(false);
        }
        this.onItemCheckListener = onItemCheckListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModels cartItem = cartList.get(position);
        holder.cPname.setText(cartItem.getProductName());
        holder.cPrice.setText(String.valueOf(cartItem.getPrice()));
        holder.cQuantity.setText(String.valueOf(cartItem.getQuantity()));
        // Load image using Glide or Picasso
         Glide.with(context).load(cartItem.getThumbnail()).into(holder.pCart);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedItems.set(position, isChecked);
            onItemCheckListener.onItemCheck(getTotalPrice());
        });

        holder.checkBox.setChecked(selectedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setCartList(ArrayList<CartModels> cartList) {
        this.cartList = cartList;
        this.selectedItems = new ArrayList<>(cartList.size());
        for (int i = 0; i < cartList.size(); i++) {
            selectedItems.add(false);
        }
        notifyDataSetChanged();
    }

    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < cartList.size(); i++) {
            if (selectedItems.get(i)) {
                total += cartList.get(i).getPrice();
            }
        }
        return total;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView pCart;
        TextView cPrice, cQuantity, cPname;
        CheckBox checkBox;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            pCart = itemView.findViewById(R.id.pImageCart);
            cPname = itemView.findViewById(R.id.pNameCart);
            cQuantity = itemView.findViewById(R.id.txtQuantityCart);
            cPrice = itemView.findViewById(R.id.pPriceCart);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface OnItemCheckListener {
        void onItemCheck(double totalPrice);
    }
}
