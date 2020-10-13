
package com.example.test_weather_app.data.dataSource.remote

import android.annotation.SuppressLint
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.usecase.usercase.CurrentInfo
import java.text.SimpleDateFormat
import java.util.*

class MainRemoteDataSource(private val apiServices: WeatherServices) : MainDataSource {

    override suspend fun getAllTasks(): List<WeatherItem> {
        TODO()
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun getTasks(model: CurrentInfo): WeatherItem {
        val task = apiServices.getCurrentWeatherInfo(lat = model.lat, lon = model.lon).await()
        return WeatherItem(
            timeZone = task.timezone,
            time = SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().time),
            temperature = task.daily.first().temp.toString(),
            pressure = task.current.pressure,
            humidity = task.current.humidity,
            description = task.daily.first().weather.first().description,
            latitude = model.lat,
            longitude = model.lon
        )
    }

    override suspend fun putWeatherItem(weatherItem: WeatherItem) {
        TODO("Not yet implemented")
    }
}
