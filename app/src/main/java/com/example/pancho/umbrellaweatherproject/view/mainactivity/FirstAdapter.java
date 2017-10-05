package com.example.pancho.umbrellaweatherproject.view.mainactivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pancho.umbrellaweatherproject.R;
import com.example.pancho.umbrellaweatherproject.entities.HourlyForecastOrdered;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 8/27/2017.
 */

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder>{
    private static final String TAG = "Adapter";
    List<HourlyForecastOrdered> hourlyForecastOrdered;
    Context context;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private String unit;

    public FirstAdapter(List<HourlyForecastOrdered> hourlyForecastOrdered) {
        this.hourlyForecastOrdered = hourlyForecastOrdered;
    }

    public void setUnits(String unit){
        this.unit = unit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HourlyForecastOrdered item = hourlyForecastOrdered.get(position);

        holder.tvLabel.setText(item.getLabel());

        layoutManager = new GridLayoutManager(context,4);
        itemAnimator = new DefaultItemAnimator();
        holder.sub_recycler.setLayoutManager(layoutManager);
        holder.sub_recycler.setItemAnimator(itemAnimator);
        holder.sub_recycler.setHasFixedSize(true);
        holder.sub_recycler.setItemViewCacheSize(20);
        holder.sub_recycler.setDrawingCacheEnabled(true);
        holder.sub_recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        SecondAdapter secondAdapter = new SecondAdapter(item.getHourlyForecastOrdered(), item.getMinp(), item.getMaxp());
        holder.sub_recycler.setAdapter(secondAdapter);
        secondAdapter.setUnits(unit);
        secondAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hourlyForecastOrdered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tvLabel)
        TextView tvLabel;

        @Nullable
        @BindView(R.id.sub_recycler)
        RecyclerView sub_recycler;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
