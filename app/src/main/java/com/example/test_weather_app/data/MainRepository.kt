package com.example.test_weather_app.data

import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.usercase.CurrentInfo


/**
 * Interface to the data layer.
 */
interface MainRepository {

    suspend fun getTasks(model: CurrentInfo): CurrentWeather
}
