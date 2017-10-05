package com.example.pancho.umbrellaweatherproject.entities;

/**
 * Created by Pancho on 9/4/2017.
 */

public class Settings {
    String name;
    String value;

    public Settings(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
