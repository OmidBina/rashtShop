package com.example.rashtshop;

import android.content.Context;
import android.content.SharedPreferences;

public class AppData {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppData(Context context) {

        sharedPreferences = context.getSharedPreferences("appData", 0);
        editor = sharedPreferences.edit();
    }

    public void set_first_time(boolean first_time) {
        editor.putBoolean("first_time", first_time);
        editor.commit();
    }

    public boolean get_first_time() {
        return sharedPreferences.getBoolean("first_time", true);
    }

    public void set_which_part(String which_part) {
        editor.putString("which_part", which_part);
        editor.commit();
    }

    public String get_which_part() {
        return sharedPreferences.getString("which_part", "onBoarding");
    }

    public void set_token(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public String get_token() {
        return sharedPreferences.getString("token", "");
    }
}
