package com.task.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.weatherapp.R;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.data.local.table.WeatherTable;
import com.task.weatherapp.databinding.AdapterCityBinding;
import com.task.weatherapp.databinding.AdapterHistoricallCityBinding;
import com.task.weatherapp.interfaces.ClickedItem;

import java.util.ArrayList;

public class HistoricallCitiesAdapter extends RecyclerView.Adapter<HistoricallCitiesAdapter.ViewHolder>    {

    Context context;
    ClickedItem clickedItem;
    ArrayList<WeatherTable> weatherTables;
    public HistoricallCitiesAdapter(Context context, ArrayList<WeatherTable> weatherTables, ClickedItem clickedItem) {
        this.context = context;
        this.weatherTables=weatherTables;
        this.clickedItem=clickedItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
       AdapterHistoricallCityBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.adapter_historicall_city,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final WeatherTable result=weatherTables.get(position);
        holder.binding.setResult(result);
        holder.binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.Item(1,position,0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return weatherTables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterHistoricallCityBinding binding;
        public ViewHolder(@NonNull     AdapterHistoricallCityBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
