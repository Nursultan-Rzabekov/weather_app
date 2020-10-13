package com.example.test_weather_app.usecase.usercase


import com.example.test_weather_app.data.MainRepository
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.BaseCoroutinesUseCase

class GetAllWeatherTaskUseCase  constructor(
    private val itemsRepository: MainRepository
) : BaseCoroutinesUseCase<List<WeatherItem>?>() {

    override suspend fun executeOnBackground(): List<WeatherItem>? = itemsRepository.getAllTasks()
}

