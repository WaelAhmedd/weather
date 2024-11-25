package com.app.weather.data.di

import com.app.weather.data.datasources.WeathersApiService
import com.app.weather.data.reposimp.WeatherRepositoryImpl
import com.app.weather.data.domain.repos.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideWeathersRepo(apiService: WeathersApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }
}
