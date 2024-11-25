package com.app.weather.data.di

import android.app.Application
import com.app.weather.data.datasources.WeathersApiService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    fun provideWeathersApi(retrofit: Retrofit): WeathersApiService {
        return retrofit.create(WeathersApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }
}