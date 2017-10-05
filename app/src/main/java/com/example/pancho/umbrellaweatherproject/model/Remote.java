package com.example.pancho.umbrellaweatherproject.model;

import android.util.Log;

import com.example.pancho.umbrellaweatherproject.entities.HourlyForecast;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;
import com.example.pancho.umbrellaweatherproject.entities.HourlyNeeded;
import com.example.pancho.umbrellaweatherproject.entities.Weather;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivity;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivityPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.pancho.umbrellaweatherproject.util.CONSTANTS.EXT_WND;

public class Remote {

    private static final String TAG = "Remote";
    private IRemote iremote;

    public Remote(IRemote iremote){
        this.iremote = iremote;
    }

    public void makeRestCall(boolean force, final MySharedPreferences prefs) {
        final Date currentTime = Calendar.getInstance().getTime();

        Long longd = prefs.getLong(CONSTANTS.MY_PREFS_TIME_REST, -1);
        Date old_time;
        if (longd == -1) {
            old_time = currentTime;
        } else {
            old_time = new Date(longd);
        }

        Log.d(TAG, "onResponse: " + currentTime + " " + old_time);

        if (true || (currentTime.compareTo(old_time) >= 0)) {
            String zip = prefs.getString(CONSTANTS.MY_PREFS_ZIP, "");
            Log.i(TAG, "ZIPCode: " + zip);
            if (zip.equals("")) {
                iremote.sendError("Empty zip code");
                iremote.sendInvalidOrNullZip();
            } else {
                OkHttpClient client = new OkHttpClient();
                HttpUrl url = new HttpUrl.Builder()
                        .scheme(CONSTANTS.BASE_SCHEMA_WND)
                        .host(CONSTANTS.BASE_URL_WND)
                        .addPathSegments(CONSTANTS.PATH_WND + zip + EXT_WND)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Log.i(TAG, "URL: " + url.toString());
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        String r = response.body().string();
                        try {
                            final Weather weather = gson.fromJson(r, Weather.class);

                            //UpdateUI
                            updateUIRest(weather);

                            if (weather.getCurrentObservation() != null) {
                                //Save cache
                                Date current_plus10 = new Date(currentTime.getTime() + CONSTANTS.TIME_UNTIL_NEXT_CALL);
                                Log.d(TAG, "onResponse: " + current_plus10);
                                prefs.putLong(CONSTANTS.MY_PREFS_TIME_REST, current_plus10.getTime());

                                //Save json
                                prefs.putString(CONSTANTS.MY_PREFS_JSON, r);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.d(TAG, "onResponse: " + r);
                            iremote.sendError("Failed");
                        }
                    }
                });
            }
        } else {
            iremote.sendError("Data in cache");

            //Get Json from cache
            Weather weather = WeatherFromCache(prefs);

            //UpdateUI
            updateUIRest(weather);
        }

    }

    private void updateUIRest(final Weather weather) {
        if (weather.getCurrentObservation() == null) {
            iremote.sendError("Invalid zip code");
            iremote.sendInvalidOrNullZip();
        } else {
            iremote.sendCurrentWeather(weather.getCurrentObservation());
            OrderHourlyForecast(weather.getHourlyForecast());
        }
    }

    public Weather WeatherFromCache(MySharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(CONSTANTS.MY_PREFS_JSON, null);
        Type type = new TypeToken<Weather>() {
        }.getType();
        Weather weather = gson.fromJson(json, type);

        return weather;
    }

    /**
     * This is ordering the daily weather,
     * so we create a list of lists in this with at most 24 elements for the 24 hours in a day
     * for the first list, the second list is only containing 10 elements (10 days) with at most 24 elements(hours)
     **/
    public void OrderHourlyForecast(List<HourlyForecast> hourlyForecast) {
        List<HourlyForecastOrdered> hourlyForecastOrdered_list = new ArrayList<>();

        List<HourlyNeeded> hourlyForecasts_to_order = new ArrayList<>();
        int maxp = 0;
        int minp = 0;
        for (int i = 0; i < hourlyForecast.size(); i++) {
            HourlyNeeded hourlyNeeded = new HourlyNeeded(hourlyForecast.get(i).getFCTTIME().getCivil(), hourlyForecast.get(i).getTemp().getMetric(), hourlyForecast.get(i).getTemp().getEnglish(), CONSTANTS.icons.get(hourlyForecast.get(i).getIcon()));
            hourlyForecasts_to_order.add(hourlyNeeded);
            if (Double.parseDouble(hourlyForecast.get(i).getTemp().getEnglish()) > Double.parseDouble(hourlyForecasts_to_order.get(maxp).getFahrenheit()))
                maxp = hourlyForecasts_to_order.size() - 1;
            if (Double.parseDouble(hourlyForecast.get(i).getTemp().getEnglish()) < Double.parseDouble(hourlyForecasts_to_order.get(minp).getFahrenheit()))
                minp = hourlyForecasts_to_order.size() - 1;

            if (hourlyForecast.get(i).getFCTTIME().getCivil().equals("11:00 PM")) {
                String label = hourlyForecast.get(i).getFCTTIME().getMonPadded() + "/" + hourlyForecast.get(i).getFCTTIME().getMdayPadded() + "/" + hourlyForecast.get(i).getFCTTIME().getYear() + "-" + hourlyForecast.get(i).getFCTTIME().getWeekdayName();
                hourlyForecastOrdered_list.add(new HourlyForecastOrdered(label, minp, maxp, hourlyForecasts_to_order));
                hourlyForecasts_to_order = new ArrayList<>();
                maxp = 0;
                minp = 0;
            }


        }

        if (!hourlyForecasts_to_order.isEmpty()) {
            int i = hourlyForecast.size() - 1;
            String label = hourlyForecast.get(i).getFCTTIME().getMonPadded() + "/" + hourlyForecast.get(i).getFCTTIME().getMdayPadded() + "/" + hourlyForecast.get(i).getFCTTIME().getYear() + "-" + hourlyForecast.get(i).getFCTTIME().getWeekdayName();
            hourlyForecastOrdered_list.add(new HourlyForecastOrdered(label, minp, maxp, hourlyForecasts_to_order));
        }

        hourlyForecastOrdered_list.get(0).setLabel("Today");
        hourlyForecastOrdered_list.get(1).setLabel("Tomorrow");
        iremote.sendNextWeather(hourlyForecastOrdered_list);
    }
}
