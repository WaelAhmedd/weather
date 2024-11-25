package com.app.weather.data.models

import com.google.gson.annotations.SerializedName

data class ForecastModel(
    @SerializedName("list") val weatherList: List<ForecastWeatherModel>,
    @SerializedName("city") val cityDtoData: CityModel?
)
