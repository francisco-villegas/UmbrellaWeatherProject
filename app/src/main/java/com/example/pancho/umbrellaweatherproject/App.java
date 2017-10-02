package com.example.pancho.umbrellaweatherproject;

import android.app.Application;

import com.example.pancho.umbrellaweatherproject.injection.mainactivity.MainActivityComponent;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.ContextModule;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.SharedPreferencesModule;
import com.example.pancho.umbrellaweatherproject.injection.mainactivity.DaggerMainActivityComponent;

public class App extends Application {

    private MainActivityComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMainActivityComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule())
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

    }

    public MainActivityComponent getMainActivityComponent() {
        return component;
    }
}
