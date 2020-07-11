package com.android.weathercore.core.weather.pojo

import com.android.weathercore.core.weather.pojo.daily.Daily

data class WeatherDataResponse(
    val current: Current,
    val daily: ArrayList<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)