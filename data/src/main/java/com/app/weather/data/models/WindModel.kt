package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class WindModel(
    @SerializedName("speed") val speed: Double,
)
