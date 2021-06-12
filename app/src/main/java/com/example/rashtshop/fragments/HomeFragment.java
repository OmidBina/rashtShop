package com.example.rashtshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashtshop.AppData;
import com.example.rashtshop.ProductDetail;
import com.example.rashtshop.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private View view;

    private Context context;

    private AppData appData;

    private ProgressBar last_product_progressBar;

    private LinearLayout last_product_layout;

    private TextView last_product_title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        elements();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        get_data();
    }

    private void elements() {

        context = getContext();

        appData = new AppData(context);

        last_product_progressBar = view.findViewById(R.id.home_last_products_loading);

        last_product_title = view.findViewById(R.id.home_last_products_title);
        last_product_layout = view.findViewById(R.id.home_last_products_layout);

        manage_clicks();

    }

    private void manage_clicks() {




    }

    private void get_data() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://shopapitest.prkiran.ir/products/last";

        last_product_progressBar.setVisibility(View.VISIBLE);
        last_product_layout.setVisibility(View.GONE);
        last_product_title.setVisibility(View.GONE);

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {

                        manage_data(jsonObject.getJSONArray("products"));

                        last_product_layout.setVisibility(View.VISIBLE);
                        last_product_title.setVisibility(View.VISIBLE);

                    }

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                    Toast.makeText(context, "مشکلی پیش آمده است، دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                }

                last_product_progressBar.setVisibility(View.GONE);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);
                Toast.makeText(context, "مشکلی پیش آمده است، دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();

                last_product_progressBar.setVisibility(View.GONE);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("api_token", appData.get_token());
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("counter", "5");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void manage_data(JSONArray jsonArray) {

        int arrayLength = jsonArray.length();

        if (arrayLength > 0) {

            for (int i=0; i<arrayLength; i++) {

                try {

                    JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("product");

                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("title");
                    String src = "https://elyasstore.ir/" + jsonObject.getString("src");

                    add_to_gridLayout(id, name, src);

                }catch (JSONException e) {
                    Log.e(TAG, "manage_data: ", e);
                }

            }
        }

    }

    private void add_to_gridLayout (int id, String name, String src) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_card_layout, null);

        CardView cardView = view.findViewById(R.id.category_card_lay_main_lay);
        ImageView imageView = view.findViewById(R.id.category_card_lay_image);
        TextView title = view.findViewById(R.id.category_card_lay_title);

        title.setText(name);

        Picasso.get()
                .load(src)
                .into(imageView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        last_product_layout.addView(view);
    }
}
