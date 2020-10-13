package com.example.test_weather_app.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_weather_app.R
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.ui.base.StrongFragment
import com.example.test_weather_app.usecase.usercase.CurrentInfo
import com.example.test_weather_app.utils.Constants.Companion.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.weather_fragment.*
import timber.log.Timber


class WeatherFragment: StrongFragment<MainViewModel>(MainViewModel::class), LocationListener, WeatherItemAdapter.ItemSelector {

    private var mLocationPermissionGranted: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val weatherItemAdapter: WeatherItemAdapter by lazy {
        return@lazy WeatherItemAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getLocationPermission()
        updateLocationUI()
        configViewModel()
        initRecyclerView()

        save_btn.setOnClickListener {
            viewModel.getCurrentWeatherInfo(
                CurrentInfo(
                    latitude,
                    longitude
                )
            )
        }


        viewModel.getAllCurrentWeather()

    }

    private fun configViewModel(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            timeZone.observe(viewLifecycleOwner, Observer {
                weatherItemAdapter.setItems(it)
            })
        }
    }

    private fun initRecyclerView(){
        weather_items_recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherItemAdapter
        }
    }

    override fun onLocationChanged(p0: Location) {
        latitude = p0.latitude
        longitude = p0.longitude
        Timber.e(" latitude + ${latitude} , longitude + ${longitude}")
    }

    override fun onItemSelected(item: WeatherItem) {
        val bundle = bundleOf("item" to item)
        findNavController().navigate(R.id.action_weatherFragment_to_weatherDetailFragment, bundle)
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }


    private fun updateLocationUI(){
        try {
            if (mLocationPermissionGranted) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        location?.let {
                            latitude = location.latitude
                            longitude = location.longitude
                        }
                    }
            }
        } catch (e: SecurityException) {
            Timber.e("Exception: %s", e.message)
        }
    }

}