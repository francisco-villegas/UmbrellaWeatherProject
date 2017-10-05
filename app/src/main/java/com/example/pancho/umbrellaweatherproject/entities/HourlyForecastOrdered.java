package com.example.pancho.umbrellaweatherproject.entities;

import java.util.List;

/**
 * Created by Pancho on 9/3/2017.
 */

public class HourlyForecastOrdered {
    String label = "";
    int minp, maxp;
    List<HourlyNeeded> hourlyForecastOrdered;


    public HourlyForecastOrdered(String label, int minp, int maxp, List<HourlyNeeded> hourlyForecastOrdered) {
        this.label = label;
        this.minp = minp;
        this.maxp = maxp;
        this.hourlyForecastOrdered = hourlyForecastOrdered;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMinp() {
        return minp;
    }

    public void setMinp(int minp) {
        this.minp = minp;
    }

    public int getMaxp() {
        return maxp;
    }

    public void setMaxp(int maxp) {
        this.maxp = maxp;
    }

    public List<HourlyNeeded> getHourlyForecastOrdered() {
        return hourlyForecastOrdered;
    }

    public void setHourlyForecastOrdered(List<HourlyNeeded> hourlyForecastOrdered) {
        this.hourlyForecastOrdered = hourlyForecastOrdered;
    }
}
