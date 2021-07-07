package com.example.rashtshop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rashtshop.AppData;
import com.example.rashtshop.R;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private View view;

    private Context context;

    private AppData appData;

    private TextView exit_profile_btn;

    private Animation animation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        elements();
        return view;
    }

    private void elements() {

        context = getContext();

        appData = new AppData(context);

        exit_profile_btn = view.findViewById(R.id.profile_exit_btn);

        animation = AnimationUtils.loadAnimation(context, R.anim.test);

        manage_clicks();
    }

    private void manage_clicks() {

        exit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exit_profile_btn.startAnimation(animation);

            }
        });

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Exit_profile_bottom_sheet exit_profile_bottom_sheet = new Exit_profile_bottom_sheet(context);
                exit_profile_bottom_sheet.show(getActivity().getSupportFragmentManager(), "exitBottomSheet");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}