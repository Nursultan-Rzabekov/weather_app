package com.example.test_weather_app.data.repositories

import com.example.test_weather_app.data.MainRepository
import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.usercase.CurrentInfo

class DefaultMainRepository  constructor(
    private val tasksRemoteDataSource: MainDataSource,
    private val tasksLocalDataSource: MainDataSource
) : MainRepository {

    override suspend fun getTasks(model: CurrentInfo): CurrentWeather {
        val tasks = tasksRemoteDataSource.getTasks(model)
        return tasks
    }
}
