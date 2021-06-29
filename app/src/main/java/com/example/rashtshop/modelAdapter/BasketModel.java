package com.example.rashtshop.modelAdapter;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BasketModel extends RealmObject {

    private int product_id;
    private int product_count;

    public BasketModel() {}

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }
}
