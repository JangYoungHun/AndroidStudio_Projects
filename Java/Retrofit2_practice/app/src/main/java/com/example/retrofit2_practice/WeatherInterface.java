package com.example.retrofit2_practice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {



    @GET("data/2.5/weather")
    Call<WeatherInfo> getWeatherInfo(@Query("q") String city_name,
                                     @Query("appid") String api_key);
}
