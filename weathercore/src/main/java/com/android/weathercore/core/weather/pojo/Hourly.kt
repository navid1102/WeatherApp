package com.android.weathercore.core.weather.pojo

data class Hourly(
    val clouds: Int? = null,
    val dew_point: Double? = null,
    val dt: Long? = null,
    val feels_like: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val temp: Double? = null,
    val weather: List<WeatherXX>? = null,
    val wind_deg: Int? = null,
    val wind_speed: Double? = null
)