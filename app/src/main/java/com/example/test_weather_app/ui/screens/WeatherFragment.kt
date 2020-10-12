package com.example.test_weather_app.ui.screens

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_weather_app.R
import com.example.test_weather_app.ui.base.StrongFragment
import com.example.test_weather_app.usecase.usercase.CurrentInfo
import kotlinx.android.synthetic.main.weather_fragment.*
import timber.log.Timber

class WeatherFragment: StrongFragment<MainViewModel>(MainViewModel::class), LocationListener, WeatherItemAdapter.ItemSelector {

    private val weatherItemAdapter: WeatherItemAdapter by lazy {
        return@lazy WeatherItemAdapter(requireContext(),this)
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

        configViewModel()
        initRecyclerView()

        save_btn.setOnClickListener {
            viewModel.getCurrentWeatherInfo(
                CurrentInfo(
                    55.55,
                    55.55
                )
            )
        }

    }

    private fun configViewModel(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            timeZone.observe(viewLifecycleOwner, Observer {
                weatherItemAdapter.setItems(listOf("hello","wqeqweqw","qweqwe"))
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
        val lat = p0.latitude
        val lng = p0.longitude
        Timber.e( " latitude + ${lat} , longitude + ${lng}")
    }

    override fun onItemSelected(item: String) {
        val bundle = bundleOf("item" to item)
        findNavController().navigate(R.id.action_weatherFragment_to_weatherDetailFragment, bundle)
    }

}