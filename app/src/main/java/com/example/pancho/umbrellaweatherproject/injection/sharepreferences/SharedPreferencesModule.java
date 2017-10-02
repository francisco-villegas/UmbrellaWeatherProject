package com.example.pancho.umbrellaweatherproject.injection.sharepreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.pancho.umbrellaweatherproject.util.CONSTANTS.MY_PREFS;

@Module
public class SharedPreferencesModule {

    @Provides
    @Singleton
    @Inject
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);
    }
}
