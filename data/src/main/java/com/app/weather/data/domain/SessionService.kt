package com.app.weather.data.domain

interface SessionService {
    fun saveCityName(name: String)
    fun getCityName(): String?
}