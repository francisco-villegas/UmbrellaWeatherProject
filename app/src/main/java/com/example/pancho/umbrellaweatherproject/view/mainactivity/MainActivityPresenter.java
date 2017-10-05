package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import com.example.pancho.umbrellaweatherproject.injection.mainactivitypresenter.DaggerMainActivityPresenterComponent;
import com.example.pancho.umbrellaweatherproject.injection.mainactivitypresenter.MainActivityPresenterModule;
import com.example.pancho.umbrellaweatherproject.injection.settingsactivity.DaggerSettingsActivityComponent;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.entities.CurrentObservation;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;
import com.example.pancho.umbrellaweatherproject.model.IRemote;
import com.example.pancho.umbrellaweatherproject.model.Remote;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import java.util.List;

import javax.inject.Inject;

public class MainActivityPresenter implements MainActivityContract.Presenter, IRemote {
    MainActivityContract.View view;
    private static final String TAG = "MainActivityPresenter";

    @Inject
    public Remote remote;

    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void attachRemote(){
        DaggerMainActivityPresenterComponent
                .builder()
                .mainActivityPresenterModule(new MainActivityPresenterModule(this))
                .build()
                .insert(this);
    }

    @Override
    public void getHeaderText(CurrentObservation currentObservation, String unit) {
        if (unit.equals("Celsius"))
            view.changeHeaderText(currentObservation.getTempC() + "°C");
        else
            view.changeHeaderText(currentObservation.getTempF() + "°F");
    }

    @Override
    public void getHeaderColor(CurrentObservation currentObservation, String unit) {
        if (Double.parseDouble(currentObservation.getTempF().toString()) > 60)
            view.changeHeaderColor(CONSTANTS.max_color);
        else
            view.changeHeaderColor(CONSTANTS.max_color);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void makeRestCall(boolean force, final MySharedPreferences prefs) {
        remote.makeRestCall(force, prefs);
    }

    @Override
    public void sendError(final String s) {
        ((MainActivity) view).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.showError(s);
            }
        });
    }

    @Override
    public void sendInvalidOrNullZip() {
        ((MainActivity) view).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.InvalidOrNullZip();
            }
        });
    }

    @Override
    public void sendCurrentWeather(final CurrentObservation weather) {
        ((MainActivity) view).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.sendCurrentWeather(weather);
            }
        });
    }

    @Override
    public void sendNextWeather(final List<HourlyForecastOrdered> hourlyForecastOrdered) {
        ((MainActivity) view).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.sendNextWeather(hourlyForecastOrdered);
            }
        });
    }
}
