package com.example.test_weather_app.data

import com.example.test_weather_app.usecase.usercase.CurrentInfo

/**
 * Interface to the data layer.
 */
interface MainRepository {

    suspend fun getTasks(model: CurrentInfo, forceTrue: Boolean = false): WeatherItem

    suspend fun getAllTasks(forceTrue: Boolean = false): List<WeatherItem>
}
