package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formenshop.Adapters.CheckoutAdapter;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Api.CreateOrder;
import com.formenshop.Models.CartModels;
import com.formenshop.R;
import com.formenshop.Request.OrderRequest;
import com.formenshop.Response.CartResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private ApiService apiService;

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
        tenOR = findViewById(R.id.etName);
        diachiOr = findViewById(R.id.etAddress);
        phoneOr = findViewById(R.id.etPhone);
        apiService = ApiClient.getApiService(this);

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        PlaceOrder.setOnClickListener(v -> {
            String name = tenOR.getText().toString().trim();
            String phone = phoneOr.getText().toString().trim();
            String address = diachiOr.getText().toString().trim();


            if (name.isEmpty()) {
                tenOR.setError("nhap ten nhan hang");
                tenOR.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                phoneOr.setError("nhap so dien thoai");
                phoneOr.requestFocus();
                return;
            }
            if (phone.length() <= 9) {
                phoneOr.setError("so dien thoai khong dung");
                phoneOr.requestFocus();
                return;
            }

            if (address.isEmpty()) {
                diachiOr.setError("nhap dia chi");
                diachiOr.requestFocus();
                return;
            }

            if (orderMethod != null && !orderMethod.isEmpty() && name.length() > 0 && phone.length() > 0 && address.length() > 0) {
                List<OrderRequest.OrderItem> orderItems = new ArrayList<>();
                for (CartModels cartModel : selectedItems) {
                    OrderRequest.OrderItem orderItem = new OrderRequest.OrderItem(cartModel.getProductId(), cartModel.getQuantity());
                    orderItems.add(orderItem);
                }

                OrderRequest orderRequest = new OrderRequest(name, phone, address, orderMethod, orderItems);
                createOrder(orderRequest);
            } else {
                Toast.makeText(CheckoutActivity.this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createOrder(OrderRequest orderRequest) {
        apiService.createOrder(orderRequest).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (orderMethod.equals("ZaloPay")) {
                        processZaloPayPayment();
                    } else {
                        navigateToSuccessActivity("Thanh toán thành công");
                    }
                } else {
                    Toast.makeText(CheckoutActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processZaloPayPayment() {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(tvTotalPrice.getText().toString());
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(CheckoutActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String transactionId, String transToken, String appTransId) {
                        navigateToSuccessActivity("Thanh toán thành công");
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransId) {
                        navigateToSuccessActivity("Hủy thanh toán");
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransId) {
                        navigateToSuccessActivity("Lỗi thanh toán");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToSuccessActivity(String result) {
        Intent intent = new Intent(CheckoutActivity.this, SuccessActivity.class);
        intent.putParcelableArrayListExtra("selectedItems", selectedItems);
        intent.putExtra("result", result);
        intent.putExtra("method", orderMethod);
        intent.putExtra("totalPrice", tvTotalPrice.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
