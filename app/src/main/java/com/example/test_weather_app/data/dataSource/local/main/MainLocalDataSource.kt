
package com.example.test_weather_app.data.dataSource.local.main

import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.usecase.usercase.CurrentInfo

class MainLocalDataSource constructor(
    private val tasksDao: MainDao
) : MainDataSource {

    override suspend fun getAllTasks(): List<WeatherItem> {
        return tasksDao.getAllTasks()
    }

    override suspend fun getTasks(model: CurrentInfo): WeatherItem {
        TODO()
    }

    override suspend fun putWeatherItem(weatherItem: WeatherItem) {
        tasksDao.insertTask(weatherItem)
    }

}
