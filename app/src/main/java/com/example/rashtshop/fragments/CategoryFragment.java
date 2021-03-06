package com.example.rashtshop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
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
import com.example.rashtshop.LoginRegister;
import com.example.rashtshop.R;
import com.nex3z.flowlayout.FlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";

    private View view;

    private Context context;

    private AppData appData;

    private ProgressBar progressBar;

    private FlowLayout flowLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_fragment_layout, container, false);
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

        progressBar = view.findViewById(R.id.category_loading);
        flowLayout = view.findViewById(R.id.category_flowlayout);

        manage_clicks();

    }

    private void manage_clicks() {




    }

    private void get_data() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://shopapitest.prkiran.ir/products/categories";

        progressBar.setVisibility(View.VISIBLE);
        flowLayout.setVisibility(View.GONE);

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("status")) {

                        manage_data(jsonObject.getJSONArray("categories"));

                        flowLayout.setVisibility(View.VISIBLE);

                    }

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                    Toast.makeText(context, "?????????? ?????? ???????? ???????? ???????????? ???????? ????????.", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);
                Toast.makeText(context, "?????????? ?????? ???????? ???????? ???????????? ???????? ????????.", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){


            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("api_token", appData.get_token());
                return header;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void manage_data(JSONArray jsonArray) {

        int arrayLength = jsonArray.length();

        if (arrayLength > 0) {

            for (int i=0; i<arrayLength; i++) {

                try {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");

                    add_to_gridLayout(id, name);

                }catch (JSONException e) {
                    Log.e(TAG, "manage_data: ", e);
                }

            }
        }

    }

    private void add_to_gridLayout (int id, String name) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_card_layout, null);

        CardView cardView = view.findViewById(R.id.category_card_lay_main_lay);
        TextView title = view.findViewById(R.id.category_card_lay_title);

        title.setText(name);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        flowLayout.addView(view);
    }
}
