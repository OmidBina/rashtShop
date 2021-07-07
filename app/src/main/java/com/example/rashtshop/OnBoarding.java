package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rashtshop.modelAdapter.Splash_adapter;

public class OnBoarding extends AppCompatActivity {

    private static final String TAG = "OnBoarding";

    private AppData appData;

    private ViewPager viewPager;

    private LinearLayout dots_layout;

    private Splash_adapter sliderAdapter;

    private TextView[] dots;

    private Button next_btn, back_btn;
    private TextView skip_btn;
    private int current_page;

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

        //appData.set_first_time(false);

        viewPager = findViewById(R.id.onboarding_viewpager);

        dots_layout = findViewById(R.id.onboarding_dots_layout);
        next_btn = findViewById(R.id.onboarding_next_btn);
        back_btn = findViewById(R.id.onboarding_back_btn);

        skip_btn = findViewById(R.id.onboarding_skip_btn);

        sliderAdapter = new Splash_adapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDotIndicator(0);

        manage_clicks();
    }

    private void manage_clicks() {

        viewPager.addOnPageChangeListener(viewPageChangeListener);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (current_page == dots.length -1){

                    Intent intent = new Intent(OnBoarding.this, LoginRegister.class);
                    startActivity(intent);
                }else {
                    viewPager.setCurrentItem(current_page + 1, true);
                }

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(current_page - 1, true);

            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding.this, LoginRegister.class);
                startActivity(intent);
            }
        });

    }

    ViewPager.OnPageChangeListener viewPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotIndicator(position);
            current_page = position;

            if (position == 0){

                next_btn.setVisibility(View.VISIBLE);
                back_btn.setVisibility(View.INVISIBLE);

                skip_btn.setVisibility(View.VISIBLE);

            }else if (position == dots.length -1){

                next_btn.setVisibility(View.VISIBLE);
                back_btn.setVisibility(View.VISIBLE);

                skip_btn.setVisibility(View.INVISIBLE);

                next_btn.setText("ورود/ثبت نام");
            }else {

                next_btn.setVisibility(View.VISIBLE);
                back_btn.setVisibility(View.VISIBLE);

                next_btn.setText("بعدی");

                skip_btn.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addDotIndicator(int position){

        dots = new TextView[3];
        dots_layout.removeAllViews();

        for (int i=0; i<dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dots[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            dots_layout.addView(dots[i]);
        }

        if (dots.length > 0){

            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}