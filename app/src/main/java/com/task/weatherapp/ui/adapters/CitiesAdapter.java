package com.task.weatherapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.weatherapp.R;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.databinding.AdapterCityBinding;
import com.task.weatherapp.interfaces.ClickedItem;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder>    {

    Context context;
    ClickedItem clickedItem;
    ArrayList<CityTable> cityTables;
    public CitiesAdapter(Context context, ArrayList<CityTable> cityTables,ClickedItem clickedItem) {
        this.context = context;
        this.cityTables=cityTables;
        this.clickedItem=clickedItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
       AdapterCityBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.adapter_city,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CityTable result=cityTables.get(position);
        holder.binding.setResult(result);
        holder.binding.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.Item(1,position,Integer.parseInt(result.getId().toString()));

            }
        });
        holder.binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.Item(2,position,Integer.parseInt(result.getId().toString()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return cityTables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterCityBinding binding;
        public ViewHolder(@NonNull     AdapterCityBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
