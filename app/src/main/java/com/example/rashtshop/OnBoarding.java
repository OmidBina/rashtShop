package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OnBoarding extends AppCompatActivity {

    private AppData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        elements();

        boolean is_user_first_time = appData.get_first_time();

        if (!is_user_first_time) {

            String token = appData.get_token();

            Intent intent;
            if (!token.isEmpty()) {

                intent = new Intent(OnBoarding.this, MainActivity.class);

            }else {

                intent = new Intent(OnBoarding.this, LoginRegister.class);

            }
            startActivity(intent);

        }
    }

    private void elements() {

        appData = new AppData(this);

    }
}