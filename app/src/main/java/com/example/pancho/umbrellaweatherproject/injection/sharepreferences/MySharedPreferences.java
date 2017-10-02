package com.example.pancho.umbrellaweatherproject.injection.sharepreferences;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putString(String key, String data) {
        mSharedPreferences.edit().putString(key,data).apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public void putLong(String key, long data) {
        mSharedPreferences.edit().putLong(key,data).apply();
    }

    public void putInt(String key, int data) {
        mSharedPreferences.edit().putInt(key,data).apply();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -0);
    }
}
