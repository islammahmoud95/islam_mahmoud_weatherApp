package com.task.weatherapp.repository;

import com.task.weatherapp.common.Const;
import com.task.weatherapp.data.network.APIClient;
import com.task.weatherapp.data.network.model.APIEndPoint;
import com.task.weatherapp.data.network.model.WeatherResponse;

import io.reactivex.rxjava3.core.Single;

public class WeatherRepository {

    APIEndPoint apiEndPoint;
    public WeatherRepository(){
         apiEndPoint = APIClient.getClient().create(APIEndPoint.class);
    }


    public Single<WeatherResponse> getWeather(String city){
        return apiEndPoint.Weather(Const.APIKEY,city);
    }
}
