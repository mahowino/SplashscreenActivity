package com.example.splashscreenactivity.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.controller.StoreHelper;
import com.example.splashscreenactivity.interfaces.getItemsCallback;
import com.example.splashscreenactivity.interfaces.onCardItemClick;
import com.example.splashscreenactivity.models.Goods;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreGoodsAdapters extends RecyclerView.Adapter<StoreGoodsAdapters.ViewHolder> {

    Context mContext;
    List<Goods> goods;
    onCardItemClick onCardItemClick;


    public StoreGoodsAdapters(Context mContext, List<Goods> goods, com.example.splashscreenactivity.interfaces.onCardItemClick onCardItemClick) {
        this.mContext = mContext;
        this.goods = goods;
        this.onCardItemClick = onCardItemClick;
    }

    @NonNull
    @Override
    public StoreGoodsAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.store_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreGoodsAdapters.ViewHolder holder, int position) {
        Goods good=goods.get(position);
        StoreHelper.getGoodImages(good.getGoodName(), new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                Uri uri=(Uri)object;
                Picasso.get().load(uri) .resize(100, 100)
                        .centerCrop() .error( R.drawable.bag )
                        .placeholder( R.drawable.prograss_animator ).into(holder.productImage);
            }

            @Override
            public void onError() {
                Toast.makeText(mContext, "error loading images", Toast.LENGTH_SHORT).show();
            }
        });
        holder.productName.setText(good.getGoodName());
        holder.productDescription.setText(good.getGoodDescription());
        holder.addToCart.setOnClickListener(view -> onCardItemClick.onClick(position));

    }

    private void showDialog() {
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription;
        ImageView addToCart,productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodName);
            productDescription=itemView.findViewById(R.id.txtGoodDescription);
            addToCart=itemView.findViewById(R.id.btnAddToCart);
            productImage=itemView.findViewById(R.id.productImage);
        }
    }
}
