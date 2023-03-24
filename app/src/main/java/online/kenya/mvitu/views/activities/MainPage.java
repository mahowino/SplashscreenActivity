package online.kenya.mvitu.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import online.kenya.mvitu.Adapters.CategoryAdapter;
import online.kenya.mvitu.Adapters.StoreGoodsAdapters;


import online.kenya.mvitu.R;
import online.kenya.mvitu.controller.StoreHelper;
import online.kenya.mvitu.interfaces.getItemsCallback;
import online.kenya.mvitu.models.Categories;
import online.kenya.mvitu.models.GoodType;
import online.kenya.mvitu.models.Goods;
import online.kenya.mvitu.views.layouts.GoodsDescriptionLayout;
import online.kenya.mvitu.views.layouts.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import online.kenya.mvitu.Utils.utils;
import ru.nikartm.support.ImageBadgeView;

public class MainPage extends AppCompatActivity {
    RecyclerView recyclerView,categories,expandedCategories;
    ArrayList<GoodType> cart;
    ImageBadgeView badgeView;
    LoadingDialog dialog;

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
        if (cart.size()==0){
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent=new Intent(getApplicationContext(),MyCart.class);
            intent.putParcelableArrayListExtra("cart",cart);
            startActivity(intent);
        }

    }

    private void displayRecyclerView() {
        StoreHelper.getAllGoods(new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Goods> goodsList=(List<Goods>)object;
                //add store goods from DB.

                StoreGoodsAdapters adapters=new StoreGoodsAdapters(getApplicationContext(), goodsList, (pos) -> {
                    dialog.startLoadingAlertDialog();
                    GoodsDescriptionLayout layout=new GoodsDescriptionLayout(MainPage.this,goodsList.get(pos),cart,badgeView);
                    layout.startDialog();
                    dialog.dismissDialog();
                });
                recyclerView.setAdapter(adapters);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                dialog.dismissDialog();
            }

            @Override
            public void onError() {
                Toast.makeText(MainPage.this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });
        StoreHelper.getAllGoodsCategories(new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Categories> goodsList=(List<Categories>)object;
                //add store goods from DB.

                CategoryAdapter adapters=new CategoryAdapter(goodsList,getApplicationContext(), (pos) -> {
                    dialog.startLoadingAlertDialog();
                    showFragmentDialog(goodsList.get(pos).getName(),R.layout.bottom_sheet_layout_store_iems);
                    dialog.dismissDialog();
                });
                categories.setAdapter(adapters);
                categories.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onError() {

            }
        });

    }

    private void showFragmentDialog(String category,int layout) {
        Dialog dialog= utils.createDialog(MainPage.this,layout);
        initializeVariables(dialog);
        setValues(category);

    }

    private void setValues(String categories) {
        StoreHelper.getAllGoodsInCategory(categories,new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Goods> goodsList=(List<Goods>)object;
                //add store goods from DB.

                StoreGoodsAdapters adapters=new StoreGoodsAdapters(getApplicationContext(), goodsList, (pos) -> {
                    dialog.startLoadingAlertDialog();
                    GoodsDescriptionLayout layout=new GoodsDescriptionLayout(MainPage.this,goodsList.get(pos),cart,badgeView);
                    layout.startDialog();
                    dialog.dismissDialog();
                });
                expandedCategories.setAdapter(adapters);
                expandedCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onError() {
                Toast.makeText(MainPage.this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeVariables(Dialog dialog) {
        expandedCategories=dialog.findViewById(R.id.recyclerViewItems);
    }


    private void init_views() {
        recyclerView=findViewById(R.id.storeRecyclerView);
        cart=new ArrayList<>();
        badgeView=findViewById(R.id.btnShowCart);
        categories=findViewById(R.id.categoryRecyclerView);
        badgeView.clearBadge();
        dialog=new LoadingDialog(this);
        dialog.startLoadingAlertDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        cart=new ArrayList<>();
        badgeView.clearBadge();
    }
}