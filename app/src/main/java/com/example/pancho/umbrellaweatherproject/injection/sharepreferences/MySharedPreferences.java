package com.example.pancho.umbrellaweatherproject.injection.sharepreferences;

import android.content.SharedPreferences;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;
    private RxSharedPreferences rxSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
        this.rxSharedPreferences = RxSharedPreferences.create(mSharedPreferences);
    }

    public void putString(String key, String data) {
        mSharedPreferences.edit().putString(key,data).apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public Preference<String> getStringPreference(String key, String defaultValue){
        return rxSharedPreferences.getString(key, defaultValue);
    }

    public void putLong(String key, long data) {
        mSharedPreferences.edit().putLong(key,data).apply();
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public Preference<Long> getLongPreference(String key, long defaultValue){
        return rxSharedPreferences.getLong(key, defaultValue);
    }

    public void putInt(String key, int data) {
        mSharedPreferences.edit().putInt(key,data).apply();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -0);
    }

    public Preference<Integer> getIntPreference(String key, int defaultValue){
        return rxSharedPreferences.getInteger(key, defaultValue);
    }

    public void putBoolean(String key, boolean data) {
        mSharedPreferences.edit().putBoolean(key,data).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public Preference<Boolean> getBooleanPreference(String key, boolean defaultValue){
        return rxSharedPreferences.getBoolean(key, defaultValue);
    }
}
