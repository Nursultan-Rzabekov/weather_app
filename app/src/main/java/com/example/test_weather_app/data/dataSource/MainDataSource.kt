
package com.example.test_weather_app.data.dataSource

import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.usercase.CurrentInfo

interface MainDataSource {
    suspend fun getTasks(model: CurrentInfo): CurrentWeather
}
