package com.example.splashscreenactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenactivity.views.activities.MainPage;

public class FinalActivitySender extends AppCompatActivity {

    TextView code;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sender);
        initViews();
        setListeners();
    }

    private void setListeners() {
        finish.setOnClickListener(view -> redirect());
    }

    private void redirect() {
        Intent intent=new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
        finish();
    }

    private void initViews() {
        code=findViewById(R.id.txtVoucherID);
        finish=findViewById(R.id.btnRedrectToStore);
        code.setText(getIntent().getStringExtra("VoucherID"));
    }
}