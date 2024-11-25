package com.app.weather.features.currentweather.domain

import com.app.weather.core.core.utils.LocationProvider
import javax.inject.Inject


class GetLocationUseCase @Inject constructor(private val defaultLocationTracker: LocationProvider) {
    suspend fun getLocation() = defaultLocationTracker.fetchCurrentLocation()
}