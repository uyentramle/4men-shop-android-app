package com.formenshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ViewProductsGridAdapter extends RecyclerView.Adapter<ViewProductsGridAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productList;
    private ViewProductsAdapter.OnItemClickListener listener;

    public ViewProductsGridAdapter(Context context, ArrayList<ProductsModel> productList, ViewProductsAdapter.OnItemClickListener listener) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
        this.listener = listener;
    }

    public ViewProductsGridAdapter(ViewProductListActivity context, ArrayList<ProductsModel> categoryList, Object onProductClicked) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductsModel product);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_products_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsModel product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        Glide.with(context).load(product.getImage()).into(holder.productImage);

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