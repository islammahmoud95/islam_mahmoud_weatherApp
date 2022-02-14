package com.task.weatherapp.ui.fragments.moreinfo;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.task.weatherapp.R;
import com.task.weatherapp.app.WeatherApp;
import com.task.weatherapp.common.Const;
import com.task.weatherapp.data.local.helper.DatabaseManager;
import com.task.weatherapp.data.local.table.WeatherTable;
import com.task.weatherapp.data.network.model.ErrorsMessage;
import com.task.weatherapp.data.network.model.WeatherResponse;
import com.task.weatherapp.databinding.FragmentMoreInfoBinding;
import com.task.weatherapp.ui.activities.MainActivity;
import com.task.weatherapp.ui.activities.main.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MoreInfoFragment extends Fragment {

    private MainViewModel homeViewModel;
    private FragmentMoreInfoBinding binding;
    private FragmentActivity activity;
    String name ="";
    int id =0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getActivity()!=null)
            activity=getActivity();
        name= MoreInfoFragmentArgs.fromBundle(getArguments()).getName();
        id= MoreInfoFragmentArgs.fromBundle(getArguments()).getId();
        activity.setTitle(name);
        homeViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = FragmentMoreInfoBinding.inflate(inflater, container, false);
        homeViewModel.setName(name);

        homeViewModel.Init(((WeatherApp) activity.getApplication()).weatherRepository, DatabaseManager.getInstance(activity));
        InitObservable();
        if (MoreInfoFragmentArgs.fromBundle(getArguments()).getWeatherTable()==null)
        homeViewModel.getWeather(name);
        else
            homeViewModel.weatherTable= MoreInfoFragmentArgs.fromBundle(getArguments()).getWeatherTable();
        binding.setViewModel(homeViewModel);

        return binding.getRoot();
    }
    private void InitObservable(){
        homeViewModel.errorMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorsMessage) {
                Toast.makeText(activity, errorsMessage, Toast.LENGTH_LONG).show();
            }
        });
        homeViewModel.insertMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
              //  homeViewModel.getCityList();
            }
        });
        homeViewModel.weatherResponse.observe(getViewLifecycleOwner(), new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                WeatherTable weatherTable= new WeatherTable();
                weatherTable.setCity(id);
                weatherTable.setIcon(weatherResponse.getWeather().get(0).getIcon());
                weatherTable.setTemp(weatherResponse.getMain().getTemp().toString());
                weatherTable.setSpeed(weatherResponse.getWind().getSpeed().toString());
                weatherTable.setHumidity(weatherResponse.getMain().getHumidity().toString());
                weatherTable.setDescription(weatherResponse.getWeather().get(0).getDescription());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yyyy - hh:mm",new Locale("en"));

                weatherTable.setCreated_at(simpleDateFormat.format(Calendar.getInstance().getTime()).toString());
                homeViewModel.setWeatherTable(weatherTable);
                binding.setViewModel(homeViewModel);
                homeViewModel.InsertWeatherList(weatherTable);
            }
        });

        homeViewModel.showProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean it) {
                ((MainActivity)activity).ShowProgress(it);
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}