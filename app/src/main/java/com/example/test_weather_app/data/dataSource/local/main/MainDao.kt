package com.example.test_weather_app.data.dataSource.local.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_weather_app.data.WeatherItem

@Dao
interface MainDao {

    @Query("SELECT * FROM weatherItem")
    fun getAllTasks(): List<WeatherItem>

    @Query("SELECT * FROM weatherItem WHERE entryid = :taskId")
    fun getTasks(taskId: String): WeatherItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: WeatherItem)

}
