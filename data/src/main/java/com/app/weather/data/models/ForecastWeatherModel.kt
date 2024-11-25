package com.app.weather.data.models

import com.app.weather.data.models.CloudinessModel
import com.google.gson.annotations.SerializedName

data class ForecastWeatherModel(
    @SerializedName("main") val weatherData: MainModel,
    @SerializedName("weather") val weatherStatus: List<WeatherModel>,
    @SerializedName("wind") val wind: WindModel,
    @SerializedName("dt_txt") val date: String,
    @SerializedName("clouds") val cloudiness: CloudinessModel
)
