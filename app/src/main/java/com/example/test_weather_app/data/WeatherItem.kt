package com.example.test_weather_app.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "weatherItem")
data class WeatherItem @JvmOverloads constructor(
    @ColumnInfo(name = "timeZone") var timeZone: String = "",
    @ColumnInfo(name = "time") var time: String = "",
    @ColumnInfo(name = "temperature") var temperature: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "pressure") var pressure: Double = 0.0,
    @ColumnInfo(name = "humidity") var humidity: Double = 0.0,
    @ColumnInfo(name = "latitude") var latitude: Double = 0.0,
    @ColumnInfo(name = "longitude") var longitude: Double = 0.0,
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) : Parcelable
