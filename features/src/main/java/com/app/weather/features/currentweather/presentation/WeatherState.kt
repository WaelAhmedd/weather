package com.app.weather.features.currentweather.presentation

import com.app.weather.features.currentweather.data.WeatherData

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weatherData: WeatherData) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
