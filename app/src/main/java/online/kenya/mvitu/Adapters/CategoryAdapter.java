package online.kenya.mvitu.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import online.kenya.mvitu.R;
import online.kenya.mvitu.controller.StoreHelper;
import online.kenya.mvitu.interfaces.getItemsCallback;
import online.kenya.mvitu.interfaces.onCardItemClick;
import online.kenya.mvitu.models.Categories;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HolderView> {
    List<Categories> categories;
    Context mContext;
    online.kenya.mvitu.interfaces.onCardItemClick onCardItemClick;

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
        StoreHelper.getCategoryImages(categories.get(position).getName(), new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                Uri uri=(Uri)object;
                Picasso.get().load(uri) .resize(100, 100)
                        .centerCrop() .error( R.drawable.bag )
                        .placeholder( R.drawable.prograss_animator ).into(holder.imageView);
            }

            @Override
            public void onError() {
                Toast.makeText(mContext, "error loading images", Toast.LENGTH_SHORT).show();
            }
        });
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
            imageView=itemView.findViewById(R.id.imgCategory);
        }
    }
}
