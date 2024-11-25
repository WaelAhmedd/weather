package com.app.weather.features.currentweather.domain

import com.app.weather.core.core.utils.LocationProvider
import com.app.weather.data.domain.SessionService
import com.app.weather.data.domain.repos.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFetchForecastByCoordinatesUseCase(
        weatherRepository: WeatherRepository
    ): FetchForecastByCoordinatesUseCase {
        return FetchForecastByCoordinatesUseCase(weatherRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideFetchForecastByCityNameUseCase(
        weatherRepository: WeatherRepository
    ): FetchForecastByCityNameUseCase {
        return FetchForecastByCityNameUseCase(weatherRepository)
    }

    @Provides
    @ViewModelScoped
    fun getCity(
        weatherRepository: SessionService
    ): GetCityNameUseCase {
        return GetCityNameUseCase(weatherRepository)
    }

    @Provides
    @ViewModelScoped
    fun getLocation(
        weatherRepository: LocationProvider
    ): GetLocationUseCase {
        return GetLocationUseCase(weatherRepository)
    }
}
