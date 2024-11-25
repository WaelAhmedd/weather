package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class CoordModel(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)
