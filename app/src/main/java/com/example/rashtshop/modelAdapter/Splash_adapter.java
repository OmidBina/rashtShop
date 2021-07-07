package com.example.rashtshop.modelAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.rashtshop.R;

public class Splash_adapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public Splash_adapter(Context context){

        this.context = context;
    }

    private int[] slide_images = {

            R.drawable.ic_baseline_shopping_basket_24,
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_person_24

    };

    private String[] slide_heading = {

            "دوباره رو خبر کن!",
            "تفکیک کنید",
            "از اعتبارت خرج کن"

    };

    private String[] slide_descriptions = {

            "از طریق بخش تحویل دوباره یه زمان و مکان انتخاب کن که ماشین های دوباره بیان پیشتون و بسته هاتو تحویل بده",
            "زباله های خشک خود را با استفاده از آموزش های درون برنامه، تفکیک و نگه داری کنید",
            "علاوه بر برداشت نقدی اعتبار،امکان استفاده از خدمات شهری،کوپن های تخفیف و فروشگاه سلامت را خواهید داشت"

    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.onboarding_slide_lay, container, false);

        ImageView imageView = view.findViewById(R.id.onboarding_slide_lay_image);
        TextView heading = view.findViewById(R.id.onboarding_slide_lay_heading);
        TextView desc = view.findViewById(R.id.onboarding_slide_lay_desc);

        imageView.setImageResource(slide_images[position]);
        heading.setText(slide_heading[position]);
        desc.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }
}