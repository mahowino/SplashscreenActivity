package com.example.splashscreenactivity.models;

public class Goods {
    String goodId,good_name,good_description,category;

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
}
