package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.formenshop.Activities.ViewProductListActivity;
import com.formenshop.Fragments.ProductDetailFragment;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;

public class ViewProductsAdapter extends RecyclerView.Adapter<ViewProductsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productList;
    private OnItemClickListener listener;

    public ViewProductsAdapter(ViewProductListActivity context, ArrayList<ProductsModel> productList) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductsModel product);
    }

    public ViewProductsAdapter(Context context, ArrayList<ProductsModel> productList, OnItemClickListener listener) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
        this.listener = listener;
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

        public void bind(final ProductsModel product, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(product));
            productName.setText(product.getName());
            productPrice.setText(product.getPrice());
            productDescription.setText(product.getDescription());
            Glide.with(itemView).load(product.getImage()).into(productImage);
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

        holder.bind(product, listener);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ProductDetailFragment productDetail = new ProductDetailFragment(context, product);
                productDetail.show(manager, productDetail.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }
}