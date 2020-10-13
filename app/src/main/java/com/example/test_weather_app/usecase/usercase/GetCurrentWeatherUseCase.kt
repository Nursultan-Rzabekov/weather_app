package com.example.test_weather_app.usecase.usercase


import com.example.test_weather_app.data.MainRepository
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.remote.response.CurrentWeather
import com.example.test_weather_app.usecase.BaseCoroutinesUseCase

class GetCurrentWeatherUseCase  constructor(
    private val itemsRepository: MainRepository
) : BaseCoroutinesUseCase<WeatherItem?>() {

    private var model: CurrentInfo? = null

    fun setData(model: CurrentInfo) {
        this.model = model
    }

    override suspend fun executeOnBackground(): WeatherItem? =
        this.model?.let { itemsRepository.getTasks(it) }
}

data class CurrentInfo(val lat: Double,val lon: Double)