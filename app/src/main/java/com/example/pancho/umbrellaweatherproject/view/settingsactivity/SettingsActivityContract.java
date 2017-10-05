package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.content.Context;

import com.example.pancho.umbrellaweatherproject.BasePresenter;
import com.example.pancho.umbrellaweatherproject.BaseView;
import com.example.pancho.umbrellaweatherproject.entities.Settings;

import java.util.List;

/**
 * Created by FRANCISCO on 22/08/2017.
 */

public interface SettingsActivityContract {

    interface View extends BaseView {

        void sendMenu(List<Settings> settingsList);
    }

    interface Presenter extends BasePresenter<View>{
        void getMenu();
        void setContext(Context context);
    }
}
