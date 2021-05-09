package com.example.rashtshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.rashtshop.fragments.BasketFragment;
import com.example.rashtshop.fragments.CategoryFragment;
import com.example.rashtshop.fragments.HomeFragment;
import com.example.rashtshop.fragments.ProfileFragment;
import com.example.rashtshop.modelAdapter.Navigation_viewPager_adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;

    private BottomNavigationView bottomNavigationView;

    private Navigation_viewPager_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        elements();
    }

    private void elements() {

        bottomNavigationView = findViewById(R.id.main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setOnNavigationItemReselectedListener(reselectedListener);

        viewPager = findViewById(R.id.main_viewpager);

        adapter = new Navigation_viewPager_adapter(MainActivity.this.getSupportFragmentManager());

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new CategoryFragment());
        adapter.addFragment(new BasketFragment());
        adapter.addFragment(new ProfileFragment());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, false);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_category:
                    viewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_basket:
                    viewPager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener reselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

                }
            };
}