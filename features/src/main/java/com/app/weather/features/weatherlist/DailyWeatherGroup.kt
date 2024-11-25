package com.app.weather.features.weatherlist

data class DailyWeatherGroup(
    val date: String,
    val forecasts: List<HourlyWeather>
)

data class HourlyWeather(
    val time: String,
    val temperature: Double,
    val condition: String,
    val iconUrl: String
)
