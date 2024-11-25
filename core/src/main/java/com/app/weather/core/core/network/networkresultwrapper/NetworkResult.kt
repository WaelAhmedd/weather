package com.app.weather.core.core.network.networkresultwrapper

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure(val error: ErrorResponse) : NetworkResult<Nothing>()
}

sealed interface AppResult<out T> {
    data object Loading : AppResult<Nothing>
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure(val error: ErrorResponse) : AppResult<Nothing>
}
