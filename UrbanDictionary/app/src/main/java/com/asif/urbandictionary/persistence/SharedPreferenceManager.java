package com.asif.urbandictionary.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.asif.urbandictionary.utils.AppConstants;

public class SharedPreferenceManager {

    private static final String ORDER_BY_VALUE = "KEY_ORDER_BY_VALUE";
    private static SharedPreferences sharedPreferences;

    private static SharedPreferenceManager sharedPreferenceManager;
    private SharedPreferenceManager(){

    }
    public static SharedPreferenceManager getInstance(Context context){
        if(sharedPreferenceManager == null){
            sharedPreferenceManager = new SharedPreferenceManager();
        }
        try {
            sharedPreferences = context.getSharedPreferences(AppConstants.PACKAGE_NAME, Context.MODE_PRIVATE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sharedPreferenceManager;
    }

    public void storeOrderByStatus(int userId) {
        sharedPreferences.edit().putInt(ORDER_BY_VALUE, userId).apply();
    }

    public int retrieveOrderByStatus() {
        return sharedPreferences.getInt(ORDER_BY_VALUE, 0);
    }

}