package com.example.pancho.umbrellaweatherproject.injection.settingsactivity;

import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.ContextModule;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.SharedPreferencesModule;
import com.example.pancho.umbrellaweatherproject.view.settingsactivity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {SettingsActivityModule.class, ContextModule.class, SharedPreferencesModule.class} )  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface SettingsActivityComponent {

    void insert(SettingsActivity SettingsActivity);

}
