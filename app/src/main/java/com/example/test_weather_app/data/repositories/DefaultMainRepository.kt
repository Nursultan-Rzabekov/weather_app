package com.example.test_weather_app.data.repositories

import com.example.test_weather_app.data.MainRepository
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.usecase.usercase.CurrentInfo

class DefaultMainRepository  constructor(
    private val tasksRemoteDataSource: MainDataSource,
    private val tasksLocalDataSource: MainDataSource
) : MainRepository {

    override suspend fun getTasks(model: CurrentInfo, forceTrue: Boolean): WeatherItem {
        if(forceTrue){
            tasksLocalDataSource.getTasks(model)
        }
        val tasks = tasksRemoteDataSource.getTasks(model)
        putCurrentWeatherTask(tasks)
        return tasks
    }

    override suspend fun getAllTasks(forceTrue: Boolean): List<WeatherItem> = tasksLocalDataSource.getAllTasks()

    private suspend fun putCurrentWeatherTask(weatherItem: WeatherItem) = tasksLocalDataSource.putWeatherItem(weatherItem)

}


