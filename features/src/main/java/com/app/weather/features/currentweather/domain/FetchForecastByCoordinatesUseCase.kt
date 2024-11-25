package com.app.weather.features.currentweather.domain

import com.app.weather.data.models.ForecastModel
import com.app.weather.data.domain.UseCase
import com.app.weather.data.domain.repos.WeatherRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchForecastByCoordinatesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : UseCase<FetchForecastByCoordinatesUseCase.Params, Result<ForecastModel>> {

    data class Params(val latitude: Double, val longitude: Double)

    override fun execute(params: Params): Flow<Result<ForecastModel>> {
        return weatherRepository.fetchForecastByCoordinates(
            latitude = params.latitude,
            longitude = params.longitude
        )
    }
}
