package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.pancho.umbrellaweatherproject.injection.mainactivity.DaggerMainActivityComponent;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.ContextModule;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.SharedPreferencesModule;
import com.example.pancho.umbrellaweatherproject.model.CurrentObservation;
import com.example.pancho.umbrellaweatherproject.model.HourlyForecastOrdered;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import com.example.pancho.umbrellaweatherproject.view.settingsactivity.SettingsActivity;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

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
    }

    private void setupDaggerComponent() {
        ((App) getApplicationContext()).getMainActivityComponent().insert(this);
    }

    /**
     * Validate if the result received is valid or not
     * If is valid
     *      If the zip code was changed make a rest call
     *      If the degrees were changed just change the adapter
     *  **/
    private void CheckResultBackSettings(Intent data) {
        String zip = prefs.getString(CONSTANTS.MY_PREFS_ZIP, "");
        if (zip.equals("")) {
            finish();
        } else {
            HashMap<String, String> changes = (HashMap<String, String>) data.getSerializableExtra(CONSTANTS.RESULT_BACK_VALUE);
            String zip_changes = changes.get(CONSTANTS.MY_PREFS_ZIP);
            String unit_changes = changes.get(CONSTANTS.MY_PREFS_UNITS);
            if (zip_changes != null && !zip_changes.equals(""))
                presenter.makeRestCall(true, prefs);
            else if (unit_changes != null && !unit_changes.equals("")) {
                firstAdapter.setUnits(unit_changes);
                firstAdapter.notifyDataSetChanged();
                presenter.getHeaderText(currentObservation, unit_changes);
                presenter.getHeaderColor(currentObservation, unit_changes);
            }
        }
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
                this.startActivityForResult(intent, CONSTANTS.RESULT_BACK);
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
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setAction("forceZip");
        this.startActivityForResult(intent, CONSTANTS.RESULT_BACK);
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
                CheckResultBackSettings(data);
                break;
        }
    }
}
