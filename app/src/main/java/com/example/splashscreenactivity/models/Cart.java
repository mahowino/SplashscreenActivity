package com.example.splashscreenactivity.models;

import java.util.List;

public class Cart {
    List<GoodType> cartGoods;

    public List<GoodType> getCartGoods() {
        return cartGoods;
    }

    public void setCartGoods(List<GoodType> cartGoods) {
        this.cartGoods = cartGoods;
    }
}
