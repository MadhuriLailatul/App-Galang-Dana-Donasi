package com.azhar.donasi.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesHelper {
    private static SharedPreferencesHelper mInstance;

    private SharedPreferences sharedPreferences;

    private SharedPreferencesHelper(Activity activity) {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public String getUsernamePreferences() {
        return sharedPreferences.getString(USERNAME_PREFERENCES, USERNAME_PREFERENCES);
    }

    public void writeUsernamePreferences(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_PREFERENCES, username);
        editor.apply();
    }

    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static synchronized SharedPreferencesHelper getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesHelper(activity);
        }
        return mInstance;
    }

    public static final String USERNAME_PREFERENCES = "username_preferences";
}
