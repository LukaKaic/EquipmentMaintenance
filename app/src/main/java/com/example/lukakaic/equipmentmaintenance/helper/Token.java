package com.example.lukakaic.equipmentmaintenance.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lukakaic on 1/15/18.
 */

public class Token {

    private final static String SHARED_PREF_NAME = "com.example.lukakaic.equipmentmaintenance.SHARED_PREF_NAME";
    private final static String TOKEN_KEY = "com.example.lukakaic.equipmentmaintenance.TOKEN_KEY";

    private String token;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

}
