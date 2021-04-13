package com.example.rashtshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginRegister extends AppCompatActivity {

    private static final String TAG = "LoginRegister";

    private RelativeLayout phone_layout, code_layout;

    private EditText get_phone_et, get_code_et;

    private Button send_phone_btn, send_code_btn;

    private TextView edit_phone_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        elements();

        show_phone_layout();
    }

    private void elements() {

        phone_layout = findViewById(R.id.login_register_phone_layout);
        get_phone_et = findViewById(R.id.login_register_phone_phone_et);
        send_phone_btn = findViewById(R.id.login_register_phone_get_code_btn);

        code_layout = findViewById(R.id.login_register_code_layout);
        get_code_et = findViewById(R.id.login_register_code_et);
        send_code_btn = findViewById(R.id.login_register_code_send_btn);
        edit_phone_btn = findViewById(R.id.login_register_code_edit_phone_btn);

        manage_clicks();
    }

    private void manage_clicks() {

        send_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = get_phone_et.getText().toString();

                if (phoneNumber.length() == 11){

                    show_code_layout();

                }else {

                    get_phone_et.setError("شماره تلفن خود را به طور صحیح وارد کنید.");

                }

            }
        });

        send_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = get_code_et.getText().toString();

                if (code.length() == 5){

                    Intent intent = new Intent(LoginRegister.this, MainActivity.class);
                    startActivity(intent);
                    finish();

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

    private void show_phone_layout() {

        phone_layout.setVisibility(View.VISIBLE);
        code_layout.setVisibility(View.GONE);

    }

    private void show_code_layout() {

        code_layout.setVisibility(View.VISIBLE);
        phone_layout.setVisibility(View.GONE);

    }
}