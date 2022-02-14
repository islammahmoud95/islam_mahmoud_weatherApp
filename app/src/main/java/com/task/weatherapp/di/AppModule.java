package com.task.weatherapp.di;

import android.content.Context;

import com.task.weatherapp.app.WeatherApp;
import com.task.weatherapp.repository.WeatherRepository;


public class AppModule {

    public WeatherRepository WeatherRepository(Context context){
        return new WeatherRepository();
    }
}
