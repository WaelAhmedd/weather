package com.app.weather.features.weatherlist.presentation

sealed class WeatherIntent {
    object LoadWeather : WeatherIntent()
    data class SelectDay(val date: String) : WeatherIntent()
}
