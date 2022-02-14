package com.task.weatherapp.data.network.model;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIEndPoint {
    @GET("weather")
    Single<WeatherResponse> Weather(
            @Query("appid") String appid,
            @Query("q") String q
    );
}
