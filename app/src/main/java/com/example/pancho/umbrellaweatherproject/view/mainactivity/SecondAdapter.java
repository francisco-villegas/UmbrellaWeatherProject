package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.entities.HourlyNeeded;
import com.example.pancho.umbrellaweatherproject.util.CONSTANTS;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 8/27/2017.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder>{
    private static final String TAG = "Adapter";
    private final int minp;
    private final int maxp;
    List<HourlyNeeded> hourlyNeeded;
    Context context;

    private String unit;

    public SecondAdapter(List<HourlyNeeded> hourlyNeeded, int minp, int maxp) {
        this.hourlyNeeded = hourlyNeeded;
        this.minp = minp;
        this.maxp = maxp;
    }

    public void setUnits(String unit){
        this.unit = unit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HourlyNeeded item = hourlyNeeded.get(position);

        holder.tvhour.setText(item.getHour());
        if (unit.equals("Celsius")) {
            holder.tvdegree_sub.setText(item.getCelsius());
        } else {
            holder.tvdegree_sub.setText(item.getFahrenheit());
        }
        holder.imageView.setIconResource(context.getResources().getString(Integer.parseInt(item.getUrl())));
        if (minp != maxp){
            if (position == minp) {
                holder.imageView.setIconColor(ContextCompat.getColor(context, CONSTANTS.min_color));
            } else if (position == maxp) {
                holder.imageView.setIconColor(ContextCompat.getColor(context, CONSTANTS.max_color));
            }
        }
    }

    @Override
    public int getItemCount() {
        return hourlyNeeded.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tvhour)
        TextView tvhour;

        @Nullable
        @BindView(R.id.tvdegree_sub)
        TextView tvdegree_sub;

        @Nullable
        @BindView(R.id.imageView)
        WeatherIconView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
