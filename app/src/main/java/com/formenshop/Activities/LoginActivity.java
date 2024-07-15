package com.formenshop.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Config.ITokenManager;
import com.formenshop.Config.TokenManager;
import com.formenshop.MailLibrary.JavaMailAPI;
import com.formenshop.Models.CodeCheckModel;
import com.formenshop.R;
import com.formenshop.Request.LoginRequest;
import com.formenshop.Response.LoginResponse;
import com.formenshop.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    private LoginResponse loginResponse;

    private ITokenManager tokenManager;
    private ApiService apiService;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        tokenManager = new TokenManager(this);
        apiService = ApiClient.getApiService(this);
        code = CodeCheckModel.generateVerificationCode();
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString().trim();
            String password = binding.loginPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                login(email, password);
            }
        });

        binding.registerBtn.setOnClickListener(v -> goToActivity(RegisterActivity.class, ""));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void goToActivity(Class<?> classx, String codeCheck) {
        Intent intent = new Intent(this, classx);
        intent.putExtra("code", codeCheck);
        startActivity(intent);
    }

    private void sendMail(String email) {
        String message = "GENTLEMAN sends you the login authentication code:. Your authentication code: " + code;
        String subject = "ACCOUNT AUTHENTICATION";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
        javaMailAPI.execute();
        goToActivity(VerificationActivity.class, code);
    }

    private void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse = response.body();
                    if (loginResponse != null) {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        String token = loginResponse.getToken();
                        tokenManager.saveToken(token);
                        sendMail(email);



                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = "Invalid email or password";
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Login failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }
}
