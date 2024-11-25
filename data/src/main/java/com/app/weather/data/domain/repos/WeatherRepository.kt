package com.app.weather.data.domain.repos

import com.app.weather.data.models.ForecastModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchForecastByCoordinates(latitude: Double, longitude: Double): Flow<Result<ForecastModel>>
    fun fetchForecastByCityName(cityName: String): Flow<Result<ForecastModel>>
}
