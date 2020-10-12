

package com.example.test_weather_app.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_weather_app.data.Task
import com.example.test_weather_app.data.dataSource.local.main.MainDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): MainDao
}

