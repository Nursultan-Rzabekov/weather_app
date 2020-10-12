package com.example.test_weather_app.ui.screens

import android.os.Bundle
import com.example.test_weather_app.R
import com.example.test_weather_app.ui.base.StrongActivity

class MainActivity : StrongActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}