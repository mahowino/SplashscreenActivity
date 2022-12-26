package com.example.splashscreenactivity.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenactivity.Adapters.CartAdapter;
import com.example.splashscreenactivity.Adapters.StoreGoodsAdapters;
import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.constants.FirebaseInit;
import com.example.splashscreenactivity.controller.CartHelper;
import com.example.splashscreenactivity.controller.FirebaseAuth;
import com.example.splashscreenactivity.models.Cart;
import com.example.splashscreenactivity.models.GoodType;
import com.example.splashscreenactivity.views.layouts.GoodsDescriptionLayout;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    Cart cart;
    RecyclerView recyclerView;
    ArrayList<GoodType> cartList;
    TextView goodsCost,service,total;
    Button confirm;
    CartHelper cartHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        initViews();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        confirm.setOnClickListener(view -> payForCartGoods());
    }

    private void payForCartGoods() {
        cartHelper=new CartHelper(cart);
        Intent intent=new Intent(getApplicationContext(),SendGoodsActivity.class);
        intent.putExtra("cart",cart);

        intent.putExtra("amount",String.valueOf(Math.round(cartHelper.getTotalCharge())));
        //may produce null pointer
        intent.putExtra("number", FirebaseInit.mAuth.getCurrentUser().getPhoneNumber());
        startActivity(intent);
    }

    private void setAdapter() {
        CartAdapter adapters=new CartAdapter(getApplicationContext(),cart,goodsCost,service,total);

        recyclerView.setAdapter(adapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void initViews() {
        cartList=getIntent().getParcelableArrayListExtra("cart");
        cart=new Cart(cartList);
        recyclerView=findViewById(R.id.cartRecyclerView);
        goodsCost=findViewById(R.id.txtGoodCost);
        service=findViewById(R.id.txtServiceCharge);
        total=findViewById(R.id.txtTotalCharge);
        confirm=findViewById(R.id.btnConfirmCart);


    }
}