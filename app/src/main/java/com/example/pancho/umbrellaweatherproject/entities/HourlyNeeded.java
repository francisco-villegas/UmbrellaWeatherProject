package com.example.pancho.umbrellaweatherproject.entities;

/**
 * Created by Pancho on 9/4/2017.
 */

public class HourlyNeeded {
    String hour;
    String celsius;
    String fahrenheit;
    String url;

    public HourlyNeeded(String hour, String celsius, String fahrenheit, String url) {
        this.hour = hour;
        this.celsius = celsius;
        this.fahrenheit = fahrenheit;
        this.url = url;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }

    public String getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(String fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
