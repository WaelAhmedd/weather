package com.app.weather.features.currentweather.data

import com.app.weather.data.models.ForecastModel

fun ForecastModel.toWeatherData(): WeatherData {
    return WeatherData(
        cityName = this.cityDtoData?.cityName ?: "",
        temperature = this.weatherList.first().weatherData.temp,
        condition = this.weatherList.first().weatherData.pressure.toString(),
        iconUrl = "",
        feelsLike = this.weatherList.first().weatherData.feelsLike.toString(),
        description = this.weatherList.first().weatherStatus.first().description,
        mainDescription = this.weatherList.first().weatherStatus.first().mainDescription,
        humidity = this.weatherList.first().weatherData.humidity.toString(),
        pressure = this.weatherList.first().weatherData.pressure.toString()
    )
}
