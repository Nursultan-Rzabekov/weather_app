package com.example.test_weather_app.data.dataSource.remote

import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.utils.Constants.Companion.WEATHER_APP_SECRET_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface WeatherServices {

    @GET("onecall")
    fun getCurrentWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = WEATHER_APP_SECRET_KEY
    ): Deferred<CurrentWeather>


}