package online.kenya.mvitu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable {
    String goodId,good_name,good_description,category;
    public Goods(){}

    protected Goods(Parcel in) {
        goodId = in.readString();
        good_name = in.readString();
        good_description = in.readString();
        category = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return good_name;
    }

    public void setGoodName(String goodName) {
        this.good_name = goodName;
    }

    public String getGoodDescription() {
        return good_description;
    }

    public void setGoodDescription(String goodDescription) {
        this.good_description = goodDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goodId);
        parcel.writeString(good_name);
        parcel.writeString(good_description);
        parcel.writeString(category);
    }
}
