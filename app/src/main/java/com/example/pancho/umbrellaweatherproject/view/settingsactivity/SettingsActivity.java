package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.pancho.umbrellaweatherproject.App;
import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.entities.Settings;
import com.example.pancho.umbrellaweatherproject.injection.sharepreferences.MySharedPreferences;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import com.example.pancho.umbrellaweatherproject.view.mainactivity.MainActivity;
import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class SettingsActivity extends AppCompatActivity implements SettingsActivityContract.View, ZipDialogClass.OnZipEventListener, UnitDialogClass.OnUnitEventListener {
    private static final String TAG = "SettingsActivity";

    @Inject
    SettingsActivityPresenter presenter;

    @Inject
    MySharedPreferences prefs;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    @BindView(R.id.recycler_settings)
    RecyclerView recycler_settings;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    List<Settings> settingsList;
    SettingsAdapter settingsAdapter;

    private HashMap<String, String> changes = new HashMap<>();
    private Preference<Boolean> zip_error;

    private CompositeDisposable disposables;

    ZipDialogClass cdd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        setupDaggerComponent();
        presenter.attachView(this);
        presenter.setContext(getApplicationContext());

        setToolbarBackPressed();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.gray));

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposables = new CompositeDisposable();
        sharePreferencesObservableZIPError();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposables.dispose();
    }

    private void sharePreferencesObservableZIPError() {
        zip_error = prefs.getBooleanPreference(CONSTANTS.MY_PREFS_ERROR, false);
        zip_error.asObservable().subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(Boolean error) {
                Log.d(TAG, "onNext: " + error);
                if(error && (cdd == null || !cdd.isShowing())) {
                    cdd = new ZipDialogClass(SettingsActivity.this);
                    cdd.show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        recycler_settings.setLayoutManager(layoutManager);
        recycler_settings.setItemAnimator(itemAnimator);
        recycler_settings.setHasFixedSize(true);
        recycler_settings.setItemViewCacheSize(20);
        recycler_settings.setDrawingCacheEnabled(true);
        recycler_settings.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        presenter.getMenu();
    }

    private void setupDaggerComponent() {
        ((App) getApplicationContext()).getSettingsActivityComponent().insert(this);
    }

    @Override
    public void showError(String s) {

    }

    private void setToolbarBackPressed(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Umbrella");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(SettingsActivity.this, MainActivity.class);
                mIntent.putExtra(CONSTANTS.RESULT_BACK_VALUE,changes);
                setResult(CONSTANTS.RESULT_BACK, mIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra(CONSTANTS.RESULT_BACK_VALUE,changes);
        setResult(CONSTANTS.RESULT_BACK, mIntent);
        super.onBackPressed();
    }

    @Override
    public void sendMenu(List<Settings> settingsList) {
        this.settingsList = settingsList;
        settingsAdapter = new SettingsAdapter(this.settingsList);
        recycler_settings.setAdapter(settingsAdapter);
        settingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void UnitUpdated(String value) {
        Log.d(TAG, "UnitUpdated: ");
        settingsList.get(1).setValue(value);
        settingsAdapter.notifyItemChanged(1);
        changes.put(CONSTANTS.MY_PREFS_UNITS,value);
    }

    @Override
    public void ZipUpdated(String value) {
        Log.d(TAG, "ZipUpdated: ");
        settingsList.get(0).setValue(value);
        settingsAdapter.notifyItemChanged(0);
        changes.put(CONSTANTS.MY_PREFS_ZIP,value);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(CONSTANTS.RESULT_BACK_VALUE, changes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        changes = (HashMap<String, String>) savedInstanceState.getSerializable(CONSTANTS.RESULT_BACK_VALUE);
    }
}
