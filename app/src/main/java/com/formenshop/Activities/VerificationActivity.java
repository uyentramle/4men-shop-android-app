package com.formenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.formenshop.R;

public class VerificationActivity extends AppCompatActivity {
    EditText cd_1;
    EditText cd_2;
    EditText cd_3;
    EditText cd_4;
    EditText cd_5;
    EditText cd_6;
    Button btn_Cf;
    String codeCheck;
    String codeInput;
    private TextView timerTextView;
    private static final long COUNTDOWN_TIME = 60000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifi);
        timerTextView = findViewById(R.id.timerTextView);
        cd_1 = (EditText) findViewById(R.id.vrf1);
        cd_2 = (EditText) findViewById(R.id.vrf2);
        cd_3 = (EditText) findViewById(R.id.vrf3);
        cd_4 = (EditText) findViewById(R.id.vrf4);
        cd_5 = (EditText) findViewById(R.id.vrf5);
        cd_6 = (EditText) findViewById(R.id.vrf6);
        btn_Cf = (Button) findViewById(R.id.btn_vrf);
        startCountdownTimer();
        codeCheck = getIntent().getStringExtra("code");
        btn_Cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });

    }
    private void startCountdownTimer() {
        new CountDownTimer(COUNTDOWN_TIME, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerTextView.setText(String.format("00:%02d", seconds));
            }

            public void onFinish() {
                timerTextView.setText("00:00");
                BackLogin();
            }
        }.start();
    }
    private void BackLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        fileList();
    }
    private void Confirm(){
        codeInput = cd_1.getText().toString() +
                cd_2.getText().toString() +
                cd_3.getText().toString() +
                cd_4.getText().toString() +
                cd_5.getText().toString() +
                cd_6.getText().toString();
        if (codeInput.equals(codeCheck)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            fileList();

        } else {
            Toast.makeText(VerificationActivity.this, "Authentication code is incorrect!", Toast.LENGTH_SHORT).show();

        }
    }


}
