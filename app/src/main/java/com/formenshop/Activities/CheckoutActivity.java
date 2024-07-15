package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CheckoutAdapter;
import com.formenshop.Api.CreateOrder;
import com.formenshop.Models.CartModels;
import com.formenshop.R;


import org.json.JSONObject;

import java.util.ArrayList;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CheckoutActivity extends AppCompatActivity {

    private TextView tvTotalPrice;
    private RadioButton ZaloPay, COD;
    private EditText tenOR, diachiOr, phoneOr;
    private RadioGroup rgPaymentMethod;
    private Button PlaceOrder;
    private RecyclerView rvProductList;
    private CheckoutAdapter checkoutAdapter;
    private String orderMethod;
    private ArrayList<CartModels> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        rvProductList = findViewById(R.id.rvProductList);
        PlaceOrder = findViewById(R.id.btnPlaceOrder);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        ZaloPay = findViewById(R.id.rbZaloPay);
        COD = findViewById(R.id.rbCOD);
        tenOR=findViewById(R.id.etName);
        diachiOr=findViewById(R.id.etAddress);
        phoneOr=findViewById(R.id.etPhone);


        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(new ArrayList<>());
        rvProductList.setAdapter(checkoutAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
            double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

            if (selectedItems != null) {
                checkoutAdapter.updateItems(selectedItems);
                tvTotalPrice.setText(String.format("%.0f", totalPrice));
            }
        }
        rgPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == ZaloPay.getId()) {
                orderMethod = "ZaloPay";
            } else if (checkedId == COD.getId()) {
                orderMethod = "COD";
            }
        });
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);
        // Xử lý sự kiện khi người dùng chọn phương thức thanh toán


        // Xử lý sự kiện khi người dùng nhấn nút "Đặt hàng"
        PlaceOrder.setOnClickListener(v -> {
            if (orderMethod.equals("ZaloPay")) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(tvTotalPrice.getText().toString());
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(CheckoutActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent2 = new Intent(CheckoutActivity.this, SuccessActivity.class);
                                intent2.putParcelableArrayListExtra("selectedItems", selectedItems);
                                intent2.putExtra("result", "Thanh toán thành Công");
                                intent2.putExtra("method", orderMethod);
                                intent2.putExtra("totalPrice", tvTotalPrice.getText().toString());
                                startActivity(intent2);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent3 = new Intent(CheckoutActivity.this, SuccessActivity.class);
                                intent3.putParcelableArrayListExtra("selectedItems", selectedItems);
                                intent3.putExtra("result", "Huỷ Thanh Toán");
                                intent3.putExtra("method", orderMethod);
                                intent3.putExtra("totalPrice", tvTotalPrice.getText().toString());
                                startActivity(intent3);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent4 = new Intent(CheckoutActivity.this, SuccessActivity.class);
                                intent4.putParcelableArrayListExtra("selectedItems", selectedItems);
                                intent4.putExtra("result", "Lỗi thanh toán");
                                intent4.putExtra("method", orderMethod);
                                intent4.putExtra("totalPrice", tvTotalPrice.getText().toString());
                                startActivity(intent4);
                            }

                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Intent intent1 = new Intent(CheckoutActivity.this, SuccessActivity.class);
                intent1.putParcelableArrayListExtra("selectedItems", selectedItems);
                intent1.putExtra("result", "Thanh toán thành Công");
                intent1.putExtra("method", orderMethod);
                intent1.putExtra("totalPrice", tvTotalPrice.getText().toString());
                startActivity(intent1);
            }

        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


}