package online.kenya.mvitu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodType extends Goods implements Parcelable {
    String GoodTypeId,good_variant_name,good_description,goodName;
    double retail_price,wholesale_price,wholesaleQuantities;
    int numberInCart;

    @Override
    public String getGoodName() {
        return goodName;
    }

    @Override
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public GoodType(String goodName){
       this.goodName=goodName;
    }


    protected GoodType(Parcel in) {
        super(in);
        GoodTypeId = in.readString();
        good_variant_name = in.readString();
        good_description = in.readString();
        goodName = in.readString();
        retail_price = in.readDouble();
        wholesale_price = in.readDouble();
        wholesaleQuantities = in.readDouble();
        numberInCart = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(GoodTypeId);
        dest.writeString(good_variant_name);
        dest.writeString(good_description);
        dest.writeString(goodName);
        dest.writeDouble(retail_price);
        dest.writeDouble(wholesale_price);
        dest.writeDouble(wholesaleQuantities);
        dest.writeInt(numberInCart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodType> CREATOR = new Creator<GoodType>() {
        @Override
        public GoodType createFromParcel(Parcel in) {
            return new GoodType(in);
        }

        @Override
        public GoodType[] newArray(int size) {
            return new GoodType[size];
        }
    };

    public double getWholesaleQuantities() {
        return wholesaleQuantities;
    }

    public void setWholesaleQuantities(double wholesaleQuantities) {
        this.wholesaleQuantities = wholesaleQuantities;
    }



    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getGoodTypeId() {
        return GoodTypeId;
    }

    public void setGoodTypeId(String goodTypeId) {
        GoodTypeId = goodTypeId;
    }

    public String getGoodVariantName() {
        return good_variant_name;
    }

    public void setGoodVariantName(String goodVariantName) {
        this.good_variant_name = goodVariantName;
    }

    public String getGoodVariantDescription() {
        return good_description;
    }

    public void setGoodVariantDescription(String goodVariantDescription) {
        this.good_description = goodVariantDescription;
    }

    public double getGoodWholesalePrice() {
        return wholesale_price;
    }

    public void setGoodWholesalePrice(double goodWholesalePrice) {
        this.wholesale_price = goodWholesalePrice;
    }

    public double getGoodRetailPrice() {
        return retail_price;
    }

    public void setGoodRetailPrice(double goodRetailPrice) {
        this.retail_price = goodRetailPrice;
    }


}
