package online.kenya.mvitu.Adapters;

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


import online.kenya.mvitu.R;
import online.kenya.mvitu.controller.CartHelper;
import online.kenya.mvitu.controller.StoreHelper;
import online.kenya.mvitu.interfaces.getItemsCallback;
import online.kenya.mvitu.models.Cart;
import online.kenya.mvitu.models.GoodType;


import com.squareup.picasso.Picasso;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context mContext;
    Cart cart;
    CartHelper cartHelper;
    TextView goodsCost,service,total;


    public CartAdapter(Context mContext, Cart cart,TextView goodsCost,TextView service,TextView total) {
        this.mContext = mContext;
        this.cart = cart;
        this.goodsCost=goodsCost;
        this.service=service;
        this.total=total;
        cartHelper=new CartHelper(cart);
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        GoodType good=cart.getCartGoods().get(position);

        StoreHelper.getGoodImages(good.getGoodName(), new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                Uri uri=(Uri)object;
                Picasso.get().load(uri) .resize(100, 100)
                        .centerCrop() .error( R.drawable.bag )
                        .placeholder( R.drawable.prograss_animator ).into(holder.productImageView);
            }

            @Override
            public void onError() {
                Toast.makeText(mContext, "error loading images", Toast.LENGTH_SHORT).show();
            }
        });
        holder.productNumber.setText(String.valueOf(good.getNumberInCart()));
        holder.productName.setText(good.getGoodName()+good.getGoodVariantName());
        holder.productDescription.setText(good.getGoodVariantDescription());
        holder.add.setOnClickListener(view -> addNumberOfGoodInCart(position,holder));
        holder.subtract.setOnClickListener(view -> subtractNumberOfGoodInCart(position,holder));

        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));


    }

    private void addNumberOfGoodInCart(int pos,CartAdapter.ViewHolder holder) {
        cartHelper.addNumberOfGoodsInCart(cart.getCartGoods().get(pos));
        holder.productNumber.setText(String.valueOf(cart.getCartGoods().get(pos).getNumberInCart()));

        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));
    }


    private void subtractNumberOfGoodInCart(int pos,CartAdapter.ViewHolder holder) {
        if (cart.getCartGoods().get(pos).getNumberInCart()==1) {
            cartHelper.removeNumberOfGoodsInCart(cart.getCartGoods().get(pos));
            notifyItemRemoved(pos);
        }
       else {
            cartHelper.removeNumberOfGoodsInCart(cart.getCartGoods().get(pos));
            holder.productNumber.setText(String.valueOf(cart.getCartGoods().get(pos).getNumberInCart()));
        }
        goodsCost.setText(String.valueOf(cartHelper.calculateCartValue()));
        service.setText(String.valueOf(cartHelper.getServiceCharge()));
        total.setText(String.valueOf(cartHelper.getTotalCharge()));

    }
    @Override
    public int getItemCount() {
        return cart.getCartGoods().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription,productNumber;
        Button add,subtract;
        ImageView productImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodNameCart);
            productDescription=itemView.findViewById(R.id.txtGoodDescriptionCart);
            add=itemView.findViewById(R.id.btnIncreaseItemCount);
            subtract=itemView.findViewById(R.id.btnReduceItemCount);
            productNumber=itemView.findViewById(R.id.txtNumberInCart);
            productImageView=itemView.findViewById(R.id.productImage);
        }
    }
}
