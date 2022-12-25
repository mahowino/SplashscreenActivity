package com.example.splashscreenactivity.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.splashscreenactivity.Adapters.StoreGoodsAdapters;
import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.controller.StoreHelper;
import com.example.splashscreenactivity.interfaces.getItemsCallback;
import com.example.splashscreenactivity.interfaces.onCardItemClick;
import com.example.splashscreenactivity.models.GoodType;
import com.example.splashscreenactivity.models.Goods;
import com.example.splashscreenactivity.views.layouts.GoodsDescriptionLayout;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class MainPage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<GoodType> cart;
    ImageBadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init_views();
        displayRecyclerView();
        setListeners();
    }

    private void setListeners() {
        badgeView.setOnClickListener(view -> viewCart());

    }

    private void viewCart() {
        Intent intent=new Intent(getApplicationContext(),MyCart.class);
        intent.putParcelableArrayListExtra("cart",cart);
        startActivity(intent);
    }

    private void displayRecyclerView() {
        StoreHelper.getAllGoods(new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Goods> goodsList=(List<Goods>)object;
                //add store goods from DB.

                StoreGoodsAdapters adapters=new StoreGoodsAdapters(getApplicationContext(), goodsList, (pos) -> {
                    GoodsDescriptionLayout layout=new GoodsDescriptionLayout(MainPage.this,goodsList.get(pos),cart,badgeView);
                    layout.startDialog();
                });
                recyclerView.setAdapter(adapters);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onError() {
                Toast.makeText(MainPage.this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init_views() {
        recyclerView=findViewById(R.id.storeRecyclerView);
        cart=new ArrayList<>();
        badgeView=findViewById(R.id.btnShowCart);
        badgeView.clearBadge();
    }
}