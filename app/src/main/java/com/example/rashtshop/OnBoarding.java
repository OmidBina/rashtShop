package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class OnBoarding extends AppCompatActivity {

    private static final String TAG = "OnBoarding";

    private AppData appData;

    private Button skip_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appData = new AppData(this);

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

        setContentView(R.layout.activity_on_boarding);

        elements();
    }

    private void elements() {

        skip_btn = findViewById(R.id.onboarding_skip_btn);

        manage_clicks();
    }

    private void manage_clicks() {

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appData.set_first_time(false);

                Intent intent = new Intent(OnBoarding.this, LoginRegister.class);
                startActivity(intent);
            }
        });

    }
}