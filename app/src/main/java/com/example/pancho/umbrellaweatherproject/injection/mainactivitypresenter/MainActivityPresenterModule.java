package com.example.pancho.umbrellaweatherproject.injection.mainactivitypresenter;

import com.example.pancho.umbrellaweatherproject.model.Remote;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

@Module
public class MainActivityPresenterModule {

    private MainActivityPresenter mainActivityPresenter;

    public MainActivityPresenterModule(MainActivityPresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;
    }

    @Provides
    Remote providesRemote(){

        return new Remote(mainActivityPresenter);
    }
}
