package com.example.pancho.umbrellaweatherproject.injection.settingsactivity;

import com.example.pancho.umbrellaweatherproject.view.settingsactivity.SettingsActivity;

import dagger.Component;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

@Component(modules = SettingsActivityModule.class)  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface SettingsActivityComponent {

//    void inject(SettingsActivity SettingsActivity); no difference between inject or insert because is the name of the method only in here
    void insert(SettingsActivity SettingsActivity);

}
