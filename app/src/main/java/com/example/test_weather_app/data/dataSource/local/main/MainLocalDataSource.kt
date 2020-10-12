
package com.example.test_weather_app.data.dataSource.local.main

import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.usercase.CurrentInfo

class MainLocalDataSource constructor(
    private val tasksDao: MainDao
) : MainDataSource {

    override suspend fun getTasks(model: CurrentInfo): CurrentWeather {
        TODO()
    }
}
