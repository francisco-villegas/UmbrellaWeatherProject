package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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

public class ZipDialogClass extends Dialog {

    public interface OnZipEventListener {
        void ZipUpdated(String value);
    }

    public Activity c;
    @BindView(R.id.etValue)
    EditText etValue;

    public ZipDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zip_dialog);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnClose, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnSave:
                String zip = etValue.getText().toString();

                SharedPreferences prefs = c.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE);
                String zip_old = prefs.getString(CONSTANTS.MY_PREFS_ZIP, "");

                if(!zip_old.equals(zip)) {
                    SharedPreferences.Editor editor = c.getSharedPreferences(CONSTANTS.MY_PREFS, MODE_PRIVATE).edit();
                    editor.putString(CONSTANTS.MY_PREFS_ZIP, zip);
                    editor.commit();
                    Toast.makeText(c, "Zip code saved successfully", Toast.LENGTH_SHORT).show();
                    ((OnZipEventListener) c).ZipUpdated(zip);
                } else{
                    Toast.makeText(c, "No changes were made", Toast.LENGTH_SHORT).show();
//                    ((OnZipEventListener) c).ZipUpdated("");
                }
                dismiss();
                break;
        }
    }
}