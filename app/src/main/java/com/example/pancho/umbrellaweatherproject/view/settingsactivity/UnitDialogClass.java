package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by FRANCISCO on 28/08/2017.
 */

public class UnitDialogClass extends Dialog {

    public interface OnUnitEventListener {
        void UnitUpdated(String fieldOne);
    }

    public Activity c;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rFahrenheit)
    RadioButton rFahrenheit;
    @BindView(R.id.rCelsius)
    RadioButton rCelsius;

    private OnUnitEventListener onUnitEventListener;

    public UnitDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.units_dialog);

        ButterKnife.bind(this);

        SharedPreferences prefs = c.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE);
        String units = prefs.getString(CONSTANTS.MY_PREFS_UNITS, "Fahrenheit");
        if (units.equals("Celsius")) {
            rCelsius.setChecked(true);
        } else {
            rFahrenheit.setChecked(true);
        }
    }

    @OnClick({R.id.btnClose, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnSave:
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                String units = "";
                switch (radioButtonID) {
                    case R.id.rCelsius:
                        units = "Celsius";
                        break;
                    case R.id.rFahrenheit:
                        units = "Fahrenheit";
                        break;
                }

                SharedPreferences prefs = c.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE);
                String units_old = prefs.getString(CONSTANTS.MY_PREFS_UNITS, "");

                if(!units_old.equals(units)) {
                    SharedPreferences.Editor editor = c.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE).edit();
                    editor.putString(CONSTANTS.MY_PREFS_UNITS, units);
                    editor.commit();
                    Toast.makeText(c, "Units changed successfully", Toast.LENGTH_SHORT).show();
                    ((OnUnitEventListener) c).UnitUpdated(units);
                    dismiss();
                } else{
                    Toast.makeText(c, "No changes were made", Toast.LENGTH_SHORT).show();
//                    ((OnUnitEventListener) c).UnitUpdated("");
                }
                break;
        }
    }
}