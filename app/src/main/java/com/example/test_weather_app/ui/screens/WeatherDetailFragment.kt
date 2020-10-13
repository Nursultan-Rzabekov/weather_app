package com.example.test_weather_app.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_weather_app.R
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.ui.base.StrongFragment
import kotlinx.android.synthetic.main.weather_details_fragment.*
import kotlinx.android.synthetic.main.weather_details_fragment.timeTextView
import kotlinx.android.synthetic.main.weather_details_fragment.titleTextView

class WeatherDetailFragment : StrongFragment<MainViewModel>(MainViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val weatherItem = it.getParcelable("item") ?: WeatherItem()
            titleTextView.text = weatherItem.timeZone
            timeTextView.text = context?.getString(R.string.time) + weatherItem.time
            temperatureTextView.text = context?.getString(R.string.temperature) + weatherItem.temperature
            descriptionTextView.text = getString(R.string.description) + weatherItem.description
            humidityTextView.text = getString(R.string.humidity) + weatherItem.humidity.toString()
            pressureTextView.text = getString(R.string.pressure) + weatherItem.pressure.toString()
            latitudeTextView.text = getString(R.string.latitude) + weatherItem.latitude.toString()
            longitudeTextView.text = getString(R.string.longitude) + weatherItem.longitude.toString()

        }
    }

}