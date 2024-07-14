package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Request.RegisterRequest;
import com.formenshop.Response.RegisterReponse;
import com.formenshop.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize API Service
        apiService = ApiClient.getApiService(this);

        // Set up button click listener
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.registerName.getText().toString().trim();
                String email = binding.registerEmail.getText().toString().trim();
                String password = binding.registerPassword.getText().toString().trim();

                if (validateInput(username, email, password)) {
                    registerUser(username, email, password);


                }
            }
        });
    }

    private boolean validateInput(String username, String email, String password) {
        if (username.isEmpty()) {
            binding.registerName.setError("Username is required");
            binding.registerName.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            binding.registerEmail.setError("Email is required");
            binding.registerEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            binding.registerPassword.setError("Password is required");
            binding.registerPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void registerUser(String fullname, String email, String password) {
        RegisterRequest registerRequest = new RegisterRequest(fullname, email, password);
        Call<RegisterReponse> call = apiService.Register(registerRequest);

        call.enqueue(new Callback<RegisterReponse>() {
            @Override
            public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this,message, Toast.LENGTH_LONG).show();
                    if (status.equals("success")) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterReponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }
}
