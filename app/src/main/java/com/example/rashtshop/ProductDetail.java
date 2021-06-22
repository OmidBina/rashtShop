package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {

    private static final String TAG = "ProductDetail";

    private AppData appData;

    private int product_id;

    private LinearLayout main_lay;

    private ImageView product_image;

    private TextView product_name_txt, product_desc_txt, product_cost_txt;

    private Button add_to_basket_btn;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            product_id = extras.getInt("id", 197);

        elements();

        get_data();
    }

    private void elements() {

        appData = new AppData(this);

        main_lay = findViewById(R.id.product_detail_main_lay);
        product_image = findViewById(R.id.product_detail_image);
        product_name_txt = findViewById(R.id.product_detail_title);
        product_desc_txt = findViewById(R.id.product_detail_desc);
        product_cost_txt = findViewById(R.id.product_detail_cost);
        add_to_basket_btn = findViewById(R.id.product_detail_add_to_basket_btn);

        progressBar = findViewById(R.id.product_detail_loading);

        manage_clicks();
    }

    private void manage_clicks() {

        add_to_basket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void show_loading() {

        progressBar.setVisibility(View.VISIBLE);

        main_lay.setVisibility(View.GONE);
        add_to_basket_btn.setVisibility(View.GONE);
    }

    private void hide_loading () {

        progressBar.setVisibility(View.GONE);

        main_lay.setVisibility(View.VISIBLE);
        add_to_basket_btn.setVisibility(View.VISIBLE);
    }

    private void get_data() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://shopapitest.prkiran.ir/products/detail";

        show_loading();

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {

                        manage_data(jsonObject.getJSONObject("product"));

                    }

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                    Toast.makeText(ProductDetail.this, "مشکلی پیش آمده است، دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                }

                hide_loading();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);
                Toast.makeText(ProductDetail.this, "مشکلی پیش آمده است، دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();

                hide_loading();
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
                params.put("products_id", product_id + "");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void manage_data(JSONObject data) {

        try {

            String src = "https://elyasstore.ir/" + data.getString("src");

            Picasso.get()
                    .load(src)
                    .into(product_image);

            String name = data.getString("title");

            product_name_txt.setText(name);

            String desc = data.getString("description");

            product_desc_txt.setText(Html.fromHtml(desc).toString());

            String cost = data.getString("cost");

            product_cost_txt.setText(cost + " تومان ");

        }catch (JSONException e) {

            Log.e(TAG, "manage_data: ", e);

        }
    }
}