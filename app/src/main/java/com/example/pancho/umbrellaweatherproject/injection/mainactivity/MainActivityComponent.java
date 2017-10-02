package com.example.pancho.umbrellaweatherproject.injection.mainactivity;

import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.ContextModule;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.SharedPreferencesModule;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainActivityModule.class, ContextModule.class, SharedPreferencesModule.class} )  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface MainActivityComponent {

//    void inject(MainActivity mainActivity); no difference between inject or insert because is the name of the method only in here
    void insert(MainActivity mainActivity);
}
