package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class MainModel(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Int,
)
