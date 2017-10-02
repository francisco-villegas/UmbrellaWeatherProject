package com.example.pancho.umbrellaweatherproject.injection.mainactivity;

import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

@Module
public class MainActivityModule {

    @Provides
//    @Singleton this is going to make the class as singleton
    MainActivityPresenter providesMainActivityPresenter(){

        return new MainActivityPresenter();
    }
}
