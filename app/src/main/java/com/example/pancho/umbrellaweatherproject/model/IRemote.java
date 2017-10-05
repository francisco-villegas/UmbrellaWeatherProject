package com.example.pancho.umbrellaweatherproject.model;

import com.example.pancho.umbrellaweatherproject.entities.CurrentObservation;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;

import java.util.List;

/**
 * Created by Pancho on 10/5/2017.
 */

public interface IRemote {
    void sendError(String s);
    void sendInvalidOrNullZip();
    void sendCurrentWeather(CurrentObservation weather);
    void sendNextWeather(List<HourlyForecastOrdered> hourlyForecastOrdered);
}
