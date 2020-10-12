package com.example.test_weather_app

import androidx.multidex.MultiDexApplication

import com.example.test_weather_app.koin.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use Koin logger
            printLogger()
            androidContext(this@BaseApplication)
            // declare modules
            modules(module)
        }
    }
}
