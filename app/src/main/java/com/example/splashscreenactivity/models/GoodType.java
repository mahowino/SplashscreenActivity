package com.example.splashscreenactivity.models;

public class GoodType extends Goods{
    String GoodTypeId,goodVariantName,goodVariantDescription;
    double goodWholesalePrice,goodRetailPrice;
    int numberInCart;

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
        return goodVariantName;
    }

    public void setGoodVariantName(String goodVariantName) {
        this.goodVariantName = goodVariantName;
    }

    public String getGoodVariantDescription() {
        return goodVariantDescription;
    }

    public void setGoodVariantDescription(String goodVariantDescription) {
        this.goodVariantDescription = goodVariantDescription;
    }

    public double getGoodWholesalePrice() {
        return goodWholesalePrice;
    }

    public void setGoodWholesalePrice(double goodWholesalePrice) {
        this.goodWholesalePrice = goodWholesalePrice;
    }

    public double getGoodRetailPrice() {
        return goodRetailPrice;
    }

    public void setGoodRetailPrice(double goodRetailPrice) {
        this.goodRetailPrice = goodRetailPrice;
    }
}
