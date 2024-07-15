package com.formenshop.Activities;

import android.os.Bundle;
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

import com.formenshop.Adapters.PaymentAdapter;
import com.formenshop.Models.PaymentModels;
import com.formenshop.Models.ProductsModel;
import com.formenshop.R;

import java.util.ArrayList;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private TextView totalAmountTextView;
    private ImageView successIcon;
    private TextView successMessage;
    private List<PaymentModels> productList;
    private double totalAmount;
    private String paymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success);
        productsRecyclerView = findViewById(R.id.products_list);
        totalAmountTextView = findViewById(R.id.total_amount);
        successIcon = findViewById(R.id.success_icon);
        successMessage = findViewById(R.id.success_message);

        paymentStatus ="fail";

        if (paymentStatus != null && paymentStatus.equals("fail")) {
            successIcon.setImageResource(R.drawable.fail);
            successMessage.setText(R.string.payment_failed);
        } else {
            successIcon.setImageResource(R.drawable.check_mark);
            successMessage.setText(R.string.payment_successful);
        }

        productList = new ArrayList<>();
        productList.add(new PaymentModels("https://icons.veryicon.com/png/o/education-technology/mobile-campus/fail-53.png", "Product 1", 1, 10.00));
        productList.add(new PaymentModels("https://icons.veryicon.com/png/o/education-technology/mobile-campus/fail-53.png", "Product 2", 2, 15.00));


        // Tính tổng số tiền
        totalAmount = 0;
        for (PaymentModels product : productList) {
            totalAmount += product.getPrice() * product.getQuantity();
        }
        PaymentAdapter adapter = new PaymentAdapter(this, productList);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsRecyclerView.setAdapter(adapter);

        // Hiển thị tổng số tiền
        totalAmountTextView.setText("Total: $" + totalAmount);
    }
}