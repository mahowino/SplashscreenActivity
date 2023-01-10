package com.example.splashscreenactivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.interfaces.onCardItemClick;
import com.example.splashscreenactivity.models.Categories;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HolderView> {
    List<Categories> categories;
    Context mContext;
    com.example.splashscreenactivity.interfaces.onCardItemClick onCardItemClick;

    public CategoryAdapter(List<Categories> categories, Context mContext,onCardItemClick onCardItemClick) {
        this.categories = categories;
        this.mContext = mContext;
        this.onCardItemClick=onCardItemClick;
    }

    @NonNull
    @Override
    public CategoryAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(mContext).inflate(R.layout.category_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.HolderView holder, int position) {
        holder.categoryName.setText(categories.get(position).getName());
        holder.cardView.setOnClickListener(view -> onCardItemClick.onClick(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView categoryName;
        CardView cardView;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.txtCategoryNames);
            cardView=itemView.findViewById(R.id.card_item);
        }
    }
}
