package com.app.weather.data.domain

import kotlinx.coroutines.flow.Flow

interface UseCase<in Params, out T> {
    fun execute(params: Params): Flow<T>
}
