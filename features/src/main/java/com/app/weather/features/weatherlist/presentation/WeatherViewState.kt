package com.app.weather.features.weatherlist.presentation

import com.app.weather.features.weatherlist.DailyWeatherGroup
import com.app.weather.features.weatherlist.HourlyWeather

data class WeatherViewState(
    val isLoading: Boolean = false,
    val weatherGroups: List<DailyWeatherGroup> = emptyList(),
    val filteredWeather: List<HourlyWeather> = emptyList(),
    val errorMessage: String? = null
)
