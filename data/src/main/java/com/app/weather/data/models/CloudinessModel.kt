package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class CloudinessModel(
    @SerializedName("all") val cloudiness: Int
)