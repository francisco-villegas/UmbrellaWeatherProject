package com.example.pancho.umbrellaweatherproject.view.settingsactivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.model.Settings;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 8/27/2017.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{
    private static final String TAG = "Adapter";
    List<Settings> settingsList;
    Context context;
    private int lastPosition = -1;

    public SettingsAdapter(List<Settings> settingsList) {
        this.settingsList = settingsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_settings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Settings item = settingsList.get(position);

        holder.tvNameSettings.setText(item.getName());
        holder.tvValueSettings.setText(item.getValue());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getName().equals("Zip")) {
                    ZipDialogClass cdd = new ZipDialogClass((Activity) context);
                    cdd.show();
                } else if(item.getName().equals("Units")){
                    UnitDialogClass cdd = new UnitDialogClass((Activity) context);
                    cdd.show();
                }
            }
        });
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tvNameSettings)
        TextView tvNameSettings;

        @Nullable
        @BindView(R.id.tvValueSettings)
        TextView tvValueSettings;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
