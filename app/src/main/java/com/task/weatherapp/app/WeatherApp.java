package com.task.weatherapp.app;

import android.app.Application;

import com.task.weatherapp.data.local.helper.DatabaseManager;
import com.task.weatherapp.di.AppModule;
import com.task.weatherapp.repository.WeatherRepository;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public WeatherRepository weatherRepository=new AppModule().WeatherRepository(this);
}
