package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CheckoutAdapter;
import com.formenshop.Adapters.PaymentAdapter;
import com.formenshop.Models.CartModels;
import com.formenshop.Models.PaymentModels;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private TextView totalAmountTextView;
    private TextView Method;
    private ImageView successIcon;
    private TextView successMessage;
    private PaymentAdapter adapter;

    private String paymentStatus;
    private ArrayList<CartModels> selectedItems;
    private Button home_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success);
        productsRecyclerView = findViewById(R.id.products_list);
        totalAmountTextView = findViewById(R.id.total_amount);
        successIcon = findViewById(R.id.success_icon);
        successMessage = findViewById(R.id.success_message);
        Method = findViewById(R.id.paymentMethod);
        home_button= findViewById(R.id.home_button);
        home_button.setOnClickListener(v -> {
            Intent intent2 = new Intent(SuccessActivity.this,MainActivity.class);
            startActivity(intent2);
            finish();
        });

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(new ArrayList<>());
        productsRecyclerView.setAdapter(adapter);


        Intent intent = getIntent();
        if (intent != null) {
            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
            totalAmountTextView.setText("Total: " + intent.getStringExtra("totalPrice"));
            paymentStatus = intent.getStringExtra("result");
            successMessage.setText(paymentStatus);
            Method.setText("Method: " + intent.getStringExtra("method"));

            if (selectedItems != null) {
                adapter.setProductList(selectedItems);
                adapter.notifyDataSetChanged();
            }
        }
        if (paymentStatus.equals("Huỷ Thanh Toán") || paymentStatus.equals("Lỗi thanh toán")) {
            successIcon.setImageResource(R.drawable.fail);
        } else {
            successIcon.setImageResource(R.drawable.check_mark);
        }


        // Tính tổng số tiền

    }
}