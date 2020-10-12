package com.example.test_weather_app.ui.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test_weather_app.R
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherItemAdapter(
    private val context: Context,
    private val itemSelector: ItemSelector
) : RecyclerView.Adapter<WeatherItemAdapter.WeatherViewHolder>(){

    private var items = listOf<String>()

    fun setItems(items: List<String>){
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.weather_item,parent,false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(items[position], itemSelector)
    }

    class WeatherViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        fun bind(item: String, itemSelector: ItemSelector) = with(itemView){
            itemView.titleTextView.text = item
            itemView.timeTextView.text = item
            itemView.temperatureTextView.text = item

            itemView.enterArrowBtn.setOnClickListener {
                itemSelector.onItemSelected("")
            }

        }
    }

    interface ItemSelector{
        fun onItemSelected(item: String)
    }
}