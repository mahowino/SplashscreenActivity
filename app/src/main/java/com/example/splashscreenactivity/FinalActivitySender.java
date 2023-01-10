package com.example.splashscreenactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenactivity.Utils.utils;
import com.example.splashscreenactivity.models.GoodType;
import com.example.splashscreenactivity.views.activities.MainPage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FinalActivitySender extends AppCompatActivity {

    TextView code;
    Button finish;
    FloatingActionButton share;
    List<GoodType> goodTypes;
    String voucherCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sender);
        initViews();
        setListeners();
    }

    private void setListeners() {
        share.setOnClickListener(v -> utils.shareCode(voucherCode,goodTypes,FinalActivitySender.this));
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
        share=findViewById(R.id.imgShareBtn);
        voucherCode=getIntent().getStringExtra("VoucherID");
        goodTypes=getIntent().getParcelableArrayListExtra("goods");
        code.setText(voucherCode);
    }
}