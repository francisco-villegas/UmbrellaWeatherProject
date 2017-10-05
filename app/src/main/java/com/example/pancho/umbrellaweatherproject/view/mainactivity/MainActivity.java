package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.example.pancho.umbrellaweatherproject.App;
import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.entities.CurrentObservation;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import com.example.pancho.umbrellaweatherproject.view.settingsactivity.SettingsActivity;
import com.f2prateek.rx.preferences2.Preference;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.R.attr.data;
import static com.example.pancho.umbrellaweatherproject.util.CONSTANTS.*;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private static final String TAG = "MainActivity";

    @Inject
    MainActivityPresenter presenter;

    @Inject
    MySharedPreferences prefs;

    @BindView(R.id.tvTemperatureTop)
    TextView tvTemperatureTop;
    @BindView(R.id.tvConditionTop)
    TextView tvConditionTop;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    @BindView(R.id.toolbar_header_view)
    LinearLayout toolbarHeaderView;

    private Toolbar myToolbar;
    private FirstAdapter firstAdapter;

    private CurrentObservation currentObservation;

    boolean zip_flag = false;
    boolean unit_flag = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Answers(), new Crashlytics());
        setContentView(R.layout.activity_main);
        setTitle("");

        ButterKnife.bind(this);

        initToolbar();

        initFlurry();

        initNotificationBar();

        setupDaggerComponent();

        initPresenter();

        presenter.makeRestCall(false, prefs);

        sharePreferencesObservableZipCode();
        sharePreferencesObservableUnits();
    }

    private void sharePreferencesObservableZipCode() {
        Preference<String> zip = prefs.getStringPreference(CONSTANTS.MY_PREFS_ZIP, "");
        zip.asObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String zip) {
                if (zip_flag) {
                    if (zip.equals("")) {
                        finish();
                    }
                    presenter.makeRestCall(true, prefs);
                } else
                    zip_flag = true;
            }


            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    private void sharePreferencesObservableUnits() {
        Preference<String> units = prefs.getStringPreference(CONSTANTS.MY_PREFS_UNITS, "Fahrenheit");
        units.asObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(String unit_changes) {
                if(unit_flag && firstAdapter != null) {
                    firstAdapter.setUnits(unit_changes);
                    firstAdapter.notifyDataSetChanged();
                    presenter.getHeaderText(currentObservation, unit_changes);
                    presenter.getHeaderColor(currentObservation, unit_changes);
                } else
                    unit_flag = true;
            }


            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
        });

    }

    private void initToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void initFlurry() {
        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, FLURRY_API_KEY);
    }

    /** Enable the notification bar, is not appearing in some android versions and with this we forced it **/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initNotificationBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.gray));
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRemote();
    }

    private void setupDaggerComponent() {
        ((App) getApplicationContext()).getMainActivityComponent().insert(this);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /** Create action bar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /** Options for action bar **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, CONSTANTS.RESULT_BACK);
                break;
        }
        return true;
    }

    /** This method is called when we receive the current weather result from makeRestCall
     * We set the text and colors **/
    @Override
    public void sendCurrentWeather(CurrentObservation currentObservation) {
        this.currentObservation = currentObservation;
        tvConditionTop.setText(currentObservation.getWeather());
        setTitle(currentObservation.getDisplayLocation().getFull());

        String unit = prefs.getString(CONSTANTS.MY_PREFS_UNITS, "Fahrenheit");
        presenter.getHeaderText(currentObservation, unit);
        presenter.getHeaderColor(currentObservation, unit);
        prefs.putBoolean(CONSTANTS.MY_PREFS_ERROR, false);
    }

    /** This is the content for the recyclerviews **/
    @Override
    public void sendNextWeather(List<HourlyForecastOrdered> hourlyForecastOrdered) {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(itemAnimator);
        recycler.setHasFixedSize(true);
        recycler.setItemViewCacheSize(20);
        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        String unit = prefs.getString(CONSTANTS.MY_PREFS_UNITS, "Fahrenheit");
        firstAdapter = new FirstAdapter(hourlyForecastOrdered);
        firstAdapter.setUnits(unit);
        recycler.setAdapter(firstAdapter);
        firstAdapter.notifyDataSetChanged();
    }

    /** If the zip is invalid we open the settings activity with the dialog **/
    @Override
    public void InvalidOrNullZip() {
        //Clean the flag and then set the error to fire the event
        prefs.putBoolean(CONSTANTS.MY_PREFS_ERROR, false);
        prefs.putBoolean(CONSTANTS.MY_PREFS_ERROR, true);

        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, CONSTANTS.RESULT_BACK);
    }

    @Override
    public void changeHeaderText(String s) {
        tvTemperatureTop.setText(s);
    }

    @Override
    public void changeHeaderColor(int color) {
        myToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), color));
        toolbarHeaderView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), color));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CONSTANTS.RESULT_BACK:
                CheckResultBackSettings();
                break;
        }
    }

    /**
     * Validate if we have an invalid zip code
     * If is not valid
     *      exit application
     *  **/
    private void CheckResultBackSettings() {
        boolean error = prefs.getBoolean(CONSTANTS.MY_PREFS_ERROR, false);
        Log.d(TAG, "CheckResultBackSettings: " + error);
        if(error)
            finish();
    }
}
