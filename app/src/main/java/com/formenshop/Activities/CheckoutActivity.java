package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.formenshop.Adapters.CheckoutAdapter;
import com.formenshop.Models.CartModels;
import com.formenshop.R;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private TextView tvTotalPrice;
    private RecyclerView rvProductList;
    private CheckoutAdapter checkoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        rvProductList = findViewById(R.id.rvProductList);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        RadioGroup rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        RadioButton rbZaloPay = findViewById(R.id.rbZaloPay);
        RadioButton rbCOD = findViewById(R.id.rbCOD);

        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(new ArrayList<>());
        rvProductList.setAdapter(checkoutAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<CartModels> selectedItems = intent.getParcelableArrayListExtra("selectedItems");
            double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

            if (selectedItems != null) {
                checkoutAdapter.updateItems(selectedItems);
                tvTotalPrice.setText(String.format("%.0f VND", totalPrice));
            }
        }

        // Xử lý sự kiện khi người dùng chọn phương thức thanh toán
        rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == rbZaloPay.getId()) {
                // Xử lý thanh toán bằng ZaloPay
            } else if (checkedId == rbCOD.getId()) {
                // Xử lý thanh toán khi nhận hàng (COD)
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Đặt hàng"
        btnPlaceOrder.setOnClickListener(v -> {
            // Xử lý đặt hàng
        });
    }
}