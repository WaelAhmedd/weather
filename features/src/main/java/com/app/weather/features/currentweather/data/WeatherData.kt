package com.app.weather.features.currentweather.data

data class WeatherData(
    val cityName: String,
    val temperature: Double,
    val condition: String,
    val iconUrl: String, val description: String,
    val mainDescription: String,
    val humidity: String,
    val pressure: String,
    val feelsLike: String
) {

}
