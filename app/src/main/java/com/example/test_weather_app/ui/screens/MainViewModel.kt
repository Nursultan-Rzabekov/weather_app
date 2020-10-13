package com.example.test_weather_app.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.ui.base.StrongViewModel
import com.example.test_weather_app.usecase.usercase.CurrentInfo
import com.example.test_weather_app.usecase.usercase.GetAllWeatherTaskUseCase
import com.example.test_weather_app.usecase.usercase.GetCurrentWeatherUseCase

class MainViewModel constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getAllWeatherTaskUseCase: GetAllWeatherTaskUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _timeZoneName = MutableLiveData<List<WeatherItem>>()
    val timeZone: LiveData<List<WeatherItem>> get() = _timeZoneName

    fun getCurrentWeatherInfo(currentInfo: CurrentInfo){
        progress.postValue(true)
        getCurrentWeatherUseCase.setData(currentInfo)
        getCurrentWeatherUseCase.execute {
            onComplete {
                progress.postValue(false)
                getAllCurrentWeather()
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getAllCurrentWeather(){
        progress.postValue(true)
        getAllWeatherTaskUseCase.execute {
            onComplete {
                progress.postValue(false)
                _timeZoneName.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }
}