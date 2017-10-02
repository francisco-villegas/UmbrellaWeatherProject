package com.example.pancho.umbrellaweatherproject.util;

import com.example.pancho.umbrellaweatherproject.R;

import java.util.HashMap;

/**
 * Created by Pancho on 9/3/2017.
 */

public class CONSTANTS {

    public static final String FLURRY_API_KEY = "67DXWDX6YXNZDMW6HXMY";

    public static final String BASE_SCHEMA_WND = "http";
    public static final String BASE_URL_WND = "api.wunderground.com";
    public static final String KEY_WND = "b73be9baafcc2f66";
    public static final String PATH_WND = "api/" + KEY_WND + "/conditions/hourly10day/q/";
    public static final String EXT_WND = ".json";
    public static final String MY_PREFS = "prefs";
    public static final String MY_PREFS_ZIP = "zip";
    public static final String MY_PREFS_UNITS = "units";
    public static final String MY_PREFS_TIME_REST = "time_rest";
    public static final String MY_PREFS_JSON = "json";

    public static final int TIME_UNTIL_NEXT_CALL = 10*60000;  //milliseconds 60000ms = 1 minute

    public static HashMap<String, String> icons = new HashMap<>();
    static {
        icons.put("chanceflurries", String.valueOf(R.string.wi_snow));
        icons.put("chancerain", String.valueOf(R.string.wi_rain));
        icons.put("chancesleet", String.valueOf(R.string.wi_sleet));
        icons.put("chancesnow", String.valueOf(R.string.wi_snow));
        icons.put("chancetstorms", String.valueOf(R.string.wi_storm_showers));
        icons.put("clear", String.valueOf(R.string.wi_day_sunny));
        icons.put("cloudy", String.valueOf(R.string.wi_cloudy));
        icons.put("flurries", String.valueOf(R.string.wi_snow));
        icons.put("fog", String.valueOf(R.string.wi_fog));
        icons.put("hazy", String.valueOf(R.string.wi_day_haze));
        icons.put("mostlycloudy", String.valueOf(R.string.wi_day_cloudy_high));
        icons.put("mostlysunny", String.valueOf(R.string.wi_day_sunny_overcast));
        icons.put("partlycloudy", String.valueOf(R.string.wi_day_cloudy));
        icons.put("sleet", String.valueOf(R.string.wi_day_sleet));
        icons.put("rain", String.valueOf(R.string.wi_rain));
        icons.put("snow", String.valueOf(R.string.wi_snow));
        icons.put("sunny", String.valueOf(R.string.wi_day_sunny));
        icons.put("tstorms", String.valueOf(R.string.wi_thunderstorm));
        icons.put("unknown", String.valueOf(R.string.wi_na));
    }

    public static final int min_color = R.color.blue_card;
    public static final int max_color = R.color.orange_card;

    public static final int RESULT_BACK = 1000;
    public static final String RESULT_BACK_VALUE = "changes";

}
