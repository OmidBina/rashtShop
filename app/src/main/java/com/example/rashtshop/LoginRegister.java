package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRegister extends AppCompatActivity {

    private static final String TAG = "LoginRegister";

    private ProgressBar progressBar;

    private LinearLayout choose_layout;
    private RelativeLayout phone_layout, code_layout;

    private EditText get_phone_et, get_name_et, get_code_et;

    private Button choose_login_btn, send_phone_btn, send_code_btn;

    private TextView choose_register_btn, edit_phone_btn;

    private boolean want_to_register = false;

    private int page_number = 1;

    private String userNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        elements();

        show_choose_layout();
    }

    private void elements() {

        progressBar = findViewById(R.id.login_register_loading);

        choose_layout = findViewById(R.id.login_register_choose_layout);
        choose_login_btn = findViewById(R.id.login_register_choose_login_btn);
        choose_register_btn = findViewById(R.id.login_register_choose_register_btn);

        phone_layout = findViewById(R.id.login_register_phone_layout);
        get_phone_et = findViewById(R.id.login_register_phone_phone_et);
        get_name_et = findViewById(R.id.login_register_phone_name_et);
        send_phone_btn = findViewById(R.id.login_register_phone_get_code_btn);

        code_layout = findViewById(R.id.login_register_code_layout);
        get_code_et = findViewById(R.id.login_register_code_et);
        send_code_btn = findViewById(R.id.login_register_code_send_btn);
        edit_phone_btn = findViewById(R.id.login_register_code_edit_phone_btn);

        manage_clicks();
    }

    @Override
    public void onBackPressed() {

        if (page_number == 2) {

            show_choose_layout();

        }else {

            super.onBackPressed();

        }
    }

    private void manage_clicks() {

        choose_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_phone_layout();

            }
        });

        choose_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                want_to_register = true;
                show_phone_layout();

            }
        });

        send_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNumber = get_phone_et.getText().toString();

                if (userNumber.length() == 11){

                    show_code_layout();

                }else {

                    get_phone_et.setError("شماره تلفن خود را به طور صحیح وارد کنید.");

                }

                if (want_to_register) {


                    String userName = get_name_et.getText().toString();

                    if (!userName.isEmpty()){

                        show_code_layout();

                    }else {

                        get_name_et.setError("نام خود را وارد کنید.");

                    }

                    register_user(userNumber, userName);

                }else {

                    login_user(userNumber);

                }

            }
        });

        send_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = get_code_et.getText().toString();

                if (code.length() == 4){

                    active_user(userNumber, code);

                }else {

                    get_code_et.setError("کد پیامک شده را به طور صحیح وارد کنید.");

                }

            }
        });

        edit_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_phone_layout();

                //get_phone_et.setText("");
            }
        });

    }

    private void show_choose_layout() {

        choose_layout.setVisibility(View.VISIBLE);
        phone_layout.setVisibility(View.GONE);
        code_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        page_number = 1;

    }

    private void show_phone_layout() {

        phone_layout.setVisibility(View.VISIBLE);
        choose_layout.setVisibility(View.GONE);
        code_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if (want_to_register)
            get_name_et.setVisibility(View.VISIBLE);
        else
            get_name_et.setVisibility(View.GONE);

        page_number = 2;
    }

    private void show_code_layout() {

        code_layout.setVisibility(View.VISIBLE);
        choose_layout.setVisibility(View.GONE);
        phone_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        page_number = 3;

    }

    private void show_loading() {

        progressBar.setVisibility(View.VISIBLE);
        code_layout.setVisibility(View.GONE);
        choose_layout.setVisibility(View.GONE);
        phone_layout.setVisibility(View.GONE);

    }

    private void register_user(String userMobile, String userName) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://shopapitest.prkiran.ir/users/create";

        show_loading();

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String activation_number = jsonObject.getJSONObject("user").getString("activation_number");

                    String message = jsonObject.getString("message");

                    Toast.makeText(LoginRegister.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginRegister.this, activation_number, Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                }

                show_code_layout();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);

                show_phone_layout();

            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userMobile);
                params.put("fullname", userName);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void login_user(String userMobile) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://shopapitest.prkiran.ir/users/login";

        show_loading();

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String activation_number = jsonObject.getJSONObject("user").getString("activation_number");

                    String message = jsonObject.getString("message");

                    Toast.makeText(LoginRegister.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginRegister.this, activation_number, Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                }

                show_code_layout();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);

                show_phone_layout();

            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userMobile);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void active_user(String userMobile, String activationNumber) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://shopapitest.prkiran.ir/users/activate";

        show_loading();

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String token = jsonObject.getJSONObject("user").getString("api_token");

                    String message = jsonObject.getString("message");

                    Toast.makeText(LoginRegister.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginRegister.this, token, Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                }

                Intent intent = new Intent(LoginRegister.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ", error);

                show_phone_layout();

            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userMobile);
                params.put("activation_number", activationNumber);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}