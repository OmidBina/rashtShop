package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProductDetail extends AppCompatActivity {

    private int product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            product_id = extras.getInt("id", 197);



    }
}