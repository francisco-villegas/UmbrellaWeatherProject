package com.example.pancho.umbrellaweatherproject.injection.mainactivitypresenter;

import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainActivityPresenterModule.class} )  //@Component(modules = 1.class,2.class) separated by commas for 2 or more modules
public interface MainActivityPresenterComponent {

    void insert(MainActivityPresenter mainActivityPresenter);
}
