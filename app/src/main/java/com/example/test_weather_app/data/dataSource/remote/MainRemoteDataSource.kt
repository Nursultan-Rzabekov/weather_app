
package com.example.test_weather_app.data.dataSource.remote

import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.usercase.CurrentInfo

class MainRemoteDataSource(private val apiServices: WeatherServices) : MainDataSource {

    override suspend fun getTasks(model: CurrentInfo): CurrentWeather {
        // Simulate network by delaying the execution.
        return apiServices.getCurrentWeatherInfo(lat = model.lat, lon = model.lon).await()
    }
}
