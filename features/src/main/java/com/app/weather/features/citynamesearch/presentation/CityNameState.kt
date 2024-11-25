package com.app.weather.features.citynamesearch.presentation

import com.app.weather.features.currentweather.data.WeatherData

sealed class CityNameState {
    object Idle : CityNameState()
    object Loading : CityNameState()
    data class Success(val weatherData: WeatherData) : CityNameState()
    data class Error(val message: String) : CityNameState()
}
