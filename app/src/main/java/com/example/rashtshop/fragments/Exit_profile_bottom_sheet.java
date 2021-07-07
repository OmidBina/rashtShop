package com.example.rashtshop.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rashtshop.AppData;
import com.example.rashtshop.OnBoarding;
import com.example.rashtshop.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class Exit_profile_bottom_sheet extends BottomSheetDialogFragment {

    private static final String TAG = "Exit_profile_bsh";

    private View view;

    private Context context;

    private AppData appData;

    private Button yes_btn, no_btn;

    public Exit_profile_bottom_sheet(Context context) {

        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exit_profile_layout, null);
        elements();
        return view;
    }

    private void elements() {

        appData = new AppData(context);

        yes_btn = view.findViewById(R.id.exit_profile_yes_btn);
        no_btn = view.findViewById(R.id.exit_profile_no_btn);

        manage_clicks();
    }

    private void manage_clicks() {

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exit();

            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void exit() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://shopapitest.prkiran.ir/users/logout";

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("لطفا صبر کنید ...");
        progressDialog.show();

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.e(TAG, "onResponse: " + response);

                appData.del_all();
                Intent intent = new Intent(context, OnBoarding.class);
                context.startActivity(intent);
                getActivity().finishAffinity();

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);
                progressDialog.dismiss();

            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("api_token", appData.get_token());
                return header;
            }
        };

        requestQueue.add(stringRequest);
    }


}
