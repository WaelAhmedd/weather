package com.app.weather.data.datasources

import com.app.weather.data.models.ForecastModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeathersApiService {
    @GET("/data/2.5/forecast")
    suspend fun getForecastData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,

        @Query("units") units: String = "metric",
    ): ForecastModel

    @GET("/data/2.5/forecast")
    suspend fun getForecastDataWithCityName(
        @Query("q") cityName: String,

        @Query("units") units: String = "metric",
    ): ForecastModel
}