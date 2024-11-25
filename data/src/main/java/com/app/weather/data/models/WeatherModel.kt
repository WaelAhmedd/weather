package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("main") val mainDescription: String,
    @SerializedName("description") val description: String
)
