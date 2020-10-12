package com.example.test_weather_app.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_weather_app.ui.base.StrongViewModel
import com.example.test_weather_app.usecase.usercase.CurrentInfo
import com.example.test_weather_app.usecase.usercase.GetCurrentWeatherUseCase

class MainViewModel constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _timeZoneName = MutableLiveData<String>()
    val timeZone: LiveData<String> get() = _timeZoneName

    fun getCurrentWeatherInfo(currentInfo: CurrentInfo){
        progress.postValue(true)
        getCurrentWeatherUseCase.setData(currentInfo)
        getCurrentWeatherUseCase.execute {
            onComplete {
                progress.postValue(false)
                _timeZoneName.postValue(it?.timezone)
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