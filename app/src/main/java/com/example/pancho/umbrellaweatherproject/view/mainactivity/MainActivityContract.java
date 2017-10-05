package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import com.example.pancho.umbrellaweatherproject.BasePresenter;
import com.example.pancho.umbrellaweatherproject.BaseView;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.entities.CurrentObservation;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;

import java.util.List;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

public interface MainActivityContract {

    interface View extends BaseView {
        void sendCurrentWeather(CurrentObservation weather);

        void sendNextWeather(List<HourlyForecastOrdered> hourlyForecastOrdered);

        void InvalidOrNullZip();

        void changeHeaderText(String s);

        void changeHeaderColor(int max_color);
    }

    interface Presenter extends BasePresenter<View>{

        void attachRemote();

        void getHeaderText(CurrentObservation currentObservation, String unit);

        void getHeaderColor(CurrentObservation currentObservation, String unit);

        void makeRestCall(boolean force, MySharedPreferences prefs);
    }
}
