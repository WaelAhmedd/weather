package com.app.weather.features.citynamesearch.domain

import com.app.weather.data.domain.SessionService
import javax.inject.Inject

class SaveCityNameUseCase @Inject constructor(
    private val sessionService: SessionService
) {
    fun execute(name: String): Unit {
        return sessionService.saveCityName(name)
    }
}
