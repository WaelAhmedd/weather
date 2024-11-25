package com.app.weather.core.core.network.networkresultwrapper

data class BaseResponse<T>(
    val data: T?,
    val message: String?
)