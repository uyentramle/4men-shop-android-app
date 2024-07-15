package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.formenshop.R;
import com.formenshop.Service.OrderPayment;

public class CheckOutTestActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edtSoluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        btnConfirm = findViewById(R.id.buttonConfirm);
        edtSoluong = findViewById(R.id.editTextSoluong);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoluong.getText() == null || edtSoluong.getText().toString().isEmpty()){
                    Toast.makeText(CheckOutTestActivity.this, "Nhập số lượng muốn mua", Toast.LENGTH_SHORT).show();
                    return;
                }

                String soLuongString = edtSoluong.getText().toString();
                double total = Double.parseDouble(soLuongString) * (double) 10000;
                Intent intent = new Intent(CheckOutTestActivity.this, OrderPayment.class);
                intent.putExtra("soluong", edtSoluong.getText().toString());
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

    }
}