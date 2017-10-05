
package com.example.pancho.umbrellaweatherproject.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("conditions")
    @Expose
    private Integer conditions;
    @SerializedName("hourly10day")
    @Expose
    private Integer hourly10day;

    public Integer getConditions() {
        return conditions;
    }

    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

    public Integer getHourly10day() {
        return hourly10day;
    }

    public void setHourly10day(Integer hourly10day) {
        this.hourly10day = hourly10day;
    }

}
