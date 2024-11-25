package com.app.weather.features.currentweather.domain

import com.app.weather.data.domain.SessionService
import com.app.weather.data.models.ForecastModel
import com.app.weather.data.domain.UseCase
import com.app.weather.data.domain.repos.WeatherRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityNameUseCase @Inject constructor(
    private val sessionService: SessionService
) {
    fun execute(): String? {
        return sessionService.getCityName()

    }
}
