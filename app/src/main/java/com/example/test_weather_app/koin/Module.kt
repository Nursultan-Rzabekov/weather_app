package com.example.test_weather_app.koin

import android.text.format.DateUtils
import androidx.room.Room
import com.example.test_weather_app.data.MainRepository
import com.example.test_weather_app.data.dataSource.MainDataSource
import com.example.test_weather_app.data.dataSource.local.ToDoDatabase
import com.example.test_weather_app.data.dataSource.local.main.MainLocalDataSource
import com.example.test_weather_app.data.dataSource.remote.MainRemoteDataSource
import com.example.test_weather_app.data.dataSource.remote.WeatherServices
import com.example.test_weather_app.data.repositories.DefaultMainRepository
import com.example.test_weather_app.ui.screens.MainViewModel
import com.example.test_weather_app.usecase.usercase.GetAllWeatherTaskUseCase
import com.example.test_weather_app.usecase.usercase.GetCurrentWeatherUseCase
import com.example.test_weather_app.utils.Constants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val module = module {

    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)

        val interceptor = HttpLoggingInterceptor()
        client.addInterceptor(interceptor)
        client.addInterceptor(ChuckInterceptor(androidContext()))
        client.build() as OkHttpClient
    }


    single {
        Retrofit.Builder()
        .baseUrl(Constants.WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(get())
        .build()
    }


    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            "Database.db"
        ).build()
    }

    single(named("taskDao")) {
        get<ToDoDatabase>().taskDao()
    }

    single(named("weatherServices")) {
        get<Retrofit>().create(WeatherServices::class.java)
    }

    single<MainDataSource>(named("localDataSource")) {
        MainLocalDataSource(tasksDao = get(named("taskDao")))
    }

    single<MainDataSource>(named("remoteDataSource")) {
        MainRemoteDataSource(apiServices = get(named("weatherServices")))
    }

    single<MainRepository> {
        DefaultMainRepository(
            tasksLocalDataSource = get(named("localDataSource")),
            tasksRemoteDataSource = get(named("remoteDataSource"))
        )
    }

    factory {
        GetCurrentWeatherUseCase(
            itemsRepository = get()
        )
    }

    factory {
        GetAllWeatherTaskUseCase(
            itemsRepository = get()
        )
    }

    viewModel {
        MainViewModel(
            getCurrentWeatherUseCase = get(),
            getAllWeatherTaskUseCase = get()
        )
    }

}

