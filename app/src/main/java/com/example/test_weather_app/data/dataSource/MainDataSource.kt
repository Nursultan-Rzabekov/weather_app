
package com.example.test_weather_app.data.dataSource

import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.usecase.usercase.CurrentInfo

interface MainDataSource {
    suspend fun getAllTasks(): List<WeatherItem>
    suspend fun getTasks(model: CurrentInfo): WeatherItem
    suspend fun putWeatherItem(weatherItem: WeatherItem)
}
