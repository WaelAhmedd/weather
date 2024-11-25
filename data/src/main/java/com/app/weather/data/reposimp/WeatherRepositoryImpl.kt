package com.app.weather.data.reposimp

import com.app.weather.data.datasources.WeathersApiService

import com.app.weather.data.domain.repos.WeatherRepository
import com.app.weather.data.models.ForecastModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeathersApiService
) : WeatherRepository {

    override fun fetchForecastByCoordinates(latitude: Double, longitude: Double): Flow<Result<ForecastModel>> = flow {
        try {
            val forecast = apiService.getForecastData(latitude = latitude, longitude = longitude)
            emit(Result.success(forecast))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun fetchForecastByCityName(cityName: String): Flow<Result<ForecastModel>> = flow {
        try {
            val forecast = apiService.getForecastDataWithCityName(cityName = cityName)
            emit(Result.success(forecast))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}


