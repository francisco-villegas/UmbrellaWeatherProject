package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pancho.umbrellaweatherproject.entities.Settings;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

public class SettingsActivityPresenter implements SettingsActivityContract.Presenter {
    SettingsActivityContract.View view;
    private static final String TAG = "SettingsActivityPresenter";
    private Context context;

    @Override
    public void attachView(SettingsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void detachView() {
        this.view = null;
    }


    @Override
    public void getMenu() {
        List<Settings> settingsList = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE);
        String zip = prefs.getString(CONSTANTS.MY_PREFS_ZIP, "No zip code");
        String units = prefs.getString(CONSTANTS.MY_PREFS_UNITS, "Fahrenheit");

        settingsList.add(new Settings("Zip", zip));
        settingsList.add(new Settings("Units", units));
        view.sendMenu(settingsList);
    }
}
