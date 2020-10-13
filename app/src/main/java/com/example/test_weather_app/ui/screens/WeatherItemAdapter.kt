package com.example.test_weather_app.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test_weather_app.R
import com.example.test_weather_app.data.WeatherItem
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherItemAdapter(
    private val context: Context,
    private val itemSelector: ItemSelector
) : RecyclerView.Adapter<WeatherItemAdapter.WeatherViewHolder>(){

    private var items = listOf<WeatherItem>()

    fun setItems(items: List<WeatherItem>){
        this.items = items
        notifyDataSetChanged()
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
        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherItem, itemSelector: ItemSelector) = with(itemView){
            itemView.titleTextView.text = item.timeZone
            itemView.timeTextView.text = context.getString(R.string.time) + item.time
            itemView.temperatureTextView.text = context.getString(R.string.temperature) + item.temperature

            itemView.setOnClickListener {
                itemSelector.onItemSelected(item)
            }

        }
    }

    interface ItemSelector{
        fun onItemSelected(item: WeatherItem)
    }
}