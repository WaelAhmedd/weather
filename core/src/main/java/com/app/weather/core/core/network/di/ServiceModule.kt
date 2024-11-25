package com.app.weather.core.core.network.di

import android.content.Context
import com.app.weather.core.core.base.ISharedPrefManager
import com.app.weather.core.core.base.SharedPrefManager

import com.app.weather.data.SessionServiceImpl
import com.app.weather.data.domain.SessionService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {


    @Binds
    @Singleton
    abstract fun bindSessionService(
        sessionService: SessionServiceImpl
    ): SessionService


    companion object {


        @Provides
        @Singleton
        fun provideSharedPrefManager(
            @ApplicationContext context: Context,
        ): ISharedPrefManager {
            return SharedPrefManager(context)
        }


    }
}