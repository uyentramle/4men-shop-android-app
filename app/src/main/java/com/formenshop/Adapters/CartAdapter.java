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

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private ArrayList<CartModels> cartList;
    private ArrayList<CartModels> checkoutList = new ArrayList<>();
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
        //this.checkoutList = checkoutList;
    }

    public CartAdapter(Context context, ArrayList<CartModels> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public ArrayList<CartModels> getCheckoutList() {
        return checkoutList;
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
            addProductCheckout(cartList.get(position), isChecked); // Gọi addProductCheckout
            if (onItemCheckListener != null) {
                onItemCheckListener.onItemCheck(getTotalPrice());
            }
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


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView pCart;
        TextView cPrice, cQuantity, cPname;
        CheckBox checkBox;

        ImageView Minus,AddQ;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            pCart = itemView.findViewById(R.id.pImageCart);
            cPname = itemView.findViewById(R.id.pNameCart);
            cQuantity = itemView.findViewById(R.id.txtQuantity);
            cPrice = itemView.findViewById(R.id.pPriceCart);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface OnItemCheckListener {
        void onItemCheck(double totalPrice);
    }
    private void addProductCheckout(CartModels product, boolean isChecked) {
        int existingIndex = -1;
        for (int i = 0; i < checkoutList.size(); i++) {
            if (checkoutList.get(i).getProductId() == product.getProductId()) {
                existingIndex = i;
                break;
            }
        }

        if (isChecked) {
            if (existingIndex == -1) {
                checkoutList.add(product);
            } else {
                // Cập nhật số lượng (nếu cần)
                // ...
            }
        } else {
            if (existingIndex != -1) {
                checkoutList.remove(existingIndex);
            }
        }
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
}
