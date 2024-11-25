package com.app.weather.features.citynamesearch.domain

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
object SaveCityNameUseCaseModule {

    @Provides
    @ViewModelScoped
    fun getCity(
        weatherRepository: SessionService
    ): SaveCityNameUseCase {
        return SaveCityNameUseCase(weatherRepository)
    }
}
