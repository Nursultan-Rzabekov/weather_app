

package com.example.test_weather_app.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_weather_app.data.WeatherItem
import com.example.test_weather_app.data.dataSource.local.main.MainDao

@Database(entities = [WeatherItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): MainDao
}

